package com.huobi.client.impl;

import java.io.IOException;
import java.net.URI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huobi.client.SubscriptionOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.InternalUtils;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.UrlParamsBuilder;

import static com.huobi.client.impl.utils.InternalUtils.decode;

public class WebSocketConnection extends WebSocketListener {

  private static final Logger log = LoggerFactory.getLogger(WebSocketConnection.class);

  private static int connectionCounter = 0;

  public enum ConnectionState {
    IDLE,
    DELAY_CONNECT,
    CONNECTED,
    CLOSED_ON_ERROR
  }

  /**
   * 1000 indicates a normal closure, meaning that the purpose for which the connection was established has been fulfilled.
   */
  private static final int CLOSE_CODE_1000 = 1000;
  /**
   * 1001 indicates that an endpoint is "going away", such as a server going down or a browser having navigated away from a page.
   */
  private static final int CLOSE_CODE_1001 = 1001;
  /**
   * 1002 indicates that an endpoint is terminating the connection due to a protocol error.
   */
  private static final int CLOSE_CODE_1002 = 1002;


  private static final String WEBSOCKET_HEADER_EXCHANGE_KEY = "X-HB-Exchange-Code";

  private WebSocket webSocket = null;

  private volatile long lastReceivedTime = 0;

  private volatile ConnectionState state = ConnectionState.IDLE;
  private int delayInSecond = 0;

  private final WebsocketRequest request;
  private final Request okhttpRequest;
  private final String apiKey;
  private final String secretKey;
  private final WebSocketWatchDog watchDog;
  private final int connectionId;
  private final boolean autoClose;

  private String subscriptionMarketUrl = "wss://api.huobi.pro/ws";
  private String subscriptionTradingUrl = "wss://api.huobi.pro/ws/v1";
  private String tradingHost;

  WebSocketConnection(
      String apiKey,
      String secretKey,
      SubscriptionOptions options,
      WebsocketRequest request,
      WebSocketWatchDog watchDog) {
    this(apiKey, secretKey, options, request, watchDog, false);
  }

  WebSocketConnection(
      String apiKey,
      String secretKey,
      SubscriptionOptions options,
      WebsocketRequest request,
      WebSocketWatchDog watchDog,
      boolean autoClose) {
    this.connectionId = WebSocketConnection.connectionCounter++;
    this.apiKey = apiKey;
    this.secretKey = secretKey;
    this.request = request;
    this.autoClose = autoClose;
    try {
      String host = new URI(options.getUri()).getHost();
      this.tradingHost = host;
      String tradingPath = ApiSignatureV2.SIGNATURE_VERSION_VALUE.equals(request.signatureVersion) ? "/ws/v2" : "/ws/v1";
      if (host.indexOf("api") == 0) {
        this.subscriptionMarketUrl = "wss://" + host + "/ws";
        this.subscriptionTradingUrl = "wss://" + host + tradingPath;
      } else {
        this.subscriptionMarketUrl = "wss://" + host + "/api/ws";
        this.subscriptionTradingUrl = "wss://" + host + tradingPath;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    this.okhttpRequest = request.authHandler == null
        ? new Request.Builder().url(subscriptionMarketUrl).build()
        : new Request.Builder().url(subscriptionTradingUrl).build();
    this.watchDog = watchDog;
    log.info("[Sub] Connection [id: "
        + this.connectionId
        + "] created for " + request.name);
  }

  int getConnectionId() {
    return this.connectionId;
  }

  void connect() {
    if (state == ConnectionState.CONNECTED) {
      log.info("[Sub][" + this.connectionId + "] Already connected");
      return;
    }
    log.info("[Sub][" + this.connectionId + "] Connecting...");
    webSocket = RestApiInvoker.createWebSocket(okhttpRequest, this);
  }

  void reConnect(int delayInSecond) {
    log.warn("[Sub][" + this.connectionId + "] Reconnecting after "
        + delayInSecond + " seconds later");
    if (webSocket != null) {
      webSocket.cancel();
      webSocket = null;
    }
    this.delayInSecond = delayInSecond;
    state = ConnectionState.DELAY_CONNECT;
  }

  void reConnect() {
    if (delayInSecond != 0) {
      delayInSecond--;
    } else {
      connect();
    }
  }

  long getLastReceivedTime() {
    return this.lastReceivedTime;
  }

  void send(String str) {
    boolean result = false;
    log.debug("[Send]{}", str);
    if (webSocket != null) {
      result = webSocket.send(str);
    }
    if (!result) {
      log.error("[Sub][" + this.connectionId
          + "] Failed to send message");
      closeOnError();
    }
  }

  @Override
  public void onMessage(WebSocket webSocket, String text) {
    super.onMessage(webSocket, text);
    lastReceivedTime = System.currentTimeMillis();

    log.debug("[On Message]:{}", text);
    try {
      JsonWrapper jsonWrapper = JsonWrapper.parseFromString(text);

      if (jsonWrapper.containKey("action")) {
        String action = jsonWrapper.getString("action");
        if ("ping".equals(action)) {
          processPingOnV2TradingLine(jsonWrapper, webSocket);
        } else if ("push".equals(action)) {
          onReceive(jsonWrapper);
        } if ("req".equals(action)) {
          String ch = jsonWrapper.getStringOrDefault("ch",null);
          if ("auth".equals(ch)) {
            this.request.authHandler.handle(this);
          }

        }

      }

    } catch (Exception e) {
      log.error("[On Message][{}]: catch exception:", connectionId, e);
      closeOnError();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onMessage(WebSocket webSocket, ByteString bytes) {
    super.onMessage(webSocket, bytes);
    try {
      if (request == null) {
        log.error("[Sub][" + this.connectionId
            + "] request is null");
        closeOnError();
        return;
      }

      lastReceivedTime = System.currentTimeMillis();

      String data;
      try {
        data = new String(decode(bytes.toByteArray()));
      } catch (IOException e) {
        log.error("[Sub][" + this.connectionId
            + "] Receive message error: " + e.getMessage());
        closeOnError();
        return;
      }
      log.debug("[On Message][{}] {}", connectionId, data);
      JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
      if (jsonWrapper.containKey("status") && !"ok".equals(jsonWrapper.getString("status"))) {
        String errorCode = jsonWrapper.getStringOrDefault("err-code", "");
        String errorMsg = jsonWrapper.getStringOrDefault("err-msg", "");
        onError(errorCode + ": " + errorMsg, null);
        log.error("[Sub][" + this.connectionId
            + "] Got error from server: " + errorCode + "; " + errorMsg);
        close();
      } else if (jsonWrapper.containKey("op")) {
        String op = jsonWrapper.getString("op");
        if (op.equals("notify")) {
          onReceive(jsonWrapper);
        } else if (op.equals("ping")) {
          processPingOnTradingLine(jsonWrapper, webSocket);
        } else if (op.equals("auth")) {
          if (request.authHandler != null) {
            request.authHandler.handle(this);
          }
        } else if (op.equals("req")) {
          onReceiveAndClose(jsonWrapper);
        }
      } else if (jsonWrapper.containKey("ch") || jsonWrapper.containKey("rep")) {
        onReceiveAndClose(jsonWrapper);
      } else if (jsonWrapper.containKey("ping")) {
        processPingOnMarketLine(jsonWrapper, webSocket);
      } else if (jsonWrapper.containKey("subbed")) {
      }
    } catch (Exception e) {
      log.error("[Sub][" + this.connectionId + "] Unexpected error: " + e.getMessage());
      closeOnError();
    }
  }

  private void onError(String errorMessage, Throwable e) {
    if (request.errorHandler != null) {
      HuobiApiException exception = new HuobiApiException(
          HuobiApiException.SUBSCRIPTION_ERROR, errorMessage, e);
      request.errorHandler.onError(exception);
    }
    log.error("[Sub][" + this.connectionId + "] " + errorMessage);
  }

  private void onReceiveAndClose(JsonWrapper jsonWrapper) {
    onReceive(jsonWrapper);
    if (autoClose) {
      close();
    }
  }

  @SuppressWarnings("unchecked")
  private void onReceive(JsonWrapper jsonWrapper) {
    Object obj = null;
    try {
      obj = request.jsonParser.parseJson(jsonWrapper);
    } catch (Exception e) {
      onError("Failed to parse server's response: " + e.getMessage(), e);
    }
    try {
      request.updateCallback.onReceive(obj);
    } catch (Exception e) {
      onError("Process error: " + e.getMessage()
          + " You should capture the exception in your error handler", e);
    }
  }

  private void processPingOnTradingLine(JsonWrapper jsonWrapper, WebSocket webSocket) {
    long ts = jsonWrapper.getLong("ts");
    webSocket.send(String.format("{\"op\":\"pong\",\"ts\":%d}", ts));
  }

  private void processPingOnMarketLine(JsonWrapper jsonWrapper, WebSocket webSocket) {
    long ts = jsonWrapper.getLong("ping");
    webSocket.send(String.format("{\"pong\":%d}", ts));
  }

  private void processPingOnV2TradingLine(JsonWrapper jsonWrapper, WebSocket webSocket) {

    long ts = jsonWrapper.getJsonObject("data").getLong("ts");

    String pong = String.format("{\"action\": \"pong\",\"params\": {\"ts\": %d}}", ts);
    webSocket.send(pong);
  }

  public ConnectionState getState() {
    return state;
  }

  public void close() {
    log.error("[Sub][" + this.connectionId + "] Closing normally");
    webSocket.cancel();
    webSocket = null;
    watchDog.onClosedNormally(this);
  }

  @Override
  public void onClosed(WebSocket webSocket, int code, String reason) {
    super.onClosed(webSocket, code, reason);
    if (state == ConnectionState.CONNECTED) {
      state = ConnectionState.IDLE;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onOpen(WebSocket webSocket, Response response) {
    super.onOpen(webSocket, response);
    this.webSocket = webSocket;
    log.info("[Sub][" + this.connectionId + "] Connected to server");
    watchDog.onConnectionCreated(this);
    if (request.connectionHandler != null) {
      request.connectionHandler.handle(this);
    }
    state = ConnectionState.CONNECTED;
    lastReceivedTime = System.currentTimeMillis();
    if (request.authHandler != null) {

      if (ApiSignature.signatureVersionValue.equals(request.signatureVersion)) {
        // 老版本验签
        sendAuthV2();
      } else if (ApiSignatureV2.SIGNATURE_VERSION_VALUE.equals(request.signatureVersion)) {
        // 新版本2.1验签
        sendAuthV2_1();
      } else {
        onError("Unsupport signature version: " + request.signatureVersion, null);
        close();
        return;
      }

      InternalUtils.await(100);
    }
  }

  private void sendAuthV2_1() {
    ApiSignatureV2 as = new ApiSignatureV2();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    try {
      URI uri = new URI(subscriptionTradingUrl);
      as.createSignature(apiKey, secretKey, "GET", tradingHost, uri.getPath(), builder);
    } catch (Exception e) {
      onError("Unexpected error when create the signature v2: " + e.getMessage(), e);
      close();
      return;
    }

    JSONObject signObj = JSON.parseObject(builder.buildUrlToJsonString());
    signObj.put("authType", "api");

    JSONObject json = new JSONObject();
    json.put("action", "req");
    json.put("ch", "auth");
    json.put("params", signObj);
    send(json.toJSONString());
  }

  private void sendAuthV2() {
    ApiSignature as = new ApiSignature();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    try {
      URI uri = new URI(subscriptionTradingUrl);
      as.createSignature(apiKey, secretKey, "GET", tradingHost, uri.getPath(), builder);
    } catch (Exception e) {
      onError("Unexpected error when create the signature: " + e.getMessage(), e);
      close();
      return;
    }
    builder.putToUrl(ApiSignature.op, ApiSignature.opValue)
        .putToUrl("cid", System.currentTimeMillis());
    send(builder.buildUrlToJsonString());
  }

  @Override
  public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    onError("Unexpected error: " + t.getMessage(), t);
    closeOnError();
  }

  private void closeOnError() {
    if (webSocket != null) {
      this.webSocket.cancel();
      state = ConnectionState.CLOSED_ON_ERROR;
      log.error("[Sub][" + this.connectionId + "] Connection is closing due to error");
    }
  }
}
