package com.huobi.service.huobi.connection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import com.huobi.constant.Options;
import com.huobi.constant.enums.ConnectionStateEnum;
import com.huobi.service.huobi.parser.HuobiModelParser;
import com.huobi.service.huobi.signature.ApiSignature;
import com.huobi.service.huobi.signature.ApiSignatureV2;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.ConnectionFactory;
import com.huobi.utils.IdGenerator;
import com.huobi.utils.InternalUtils;
import com.huobi.utils.ResponseCallback;
import com.huobi.utils.WebSocketConnection;
import com.huobi.utils.WebSocketWatchDog;

@Data
@Slf4j
public class HuobiWebSocketConnection extends WebSocketListener implements WebSocketConnection {

  public static final String HUOBI_TRADING_WEBSOCKET_PATH = "/ws/v1";
  public static final String HUOBI_TRADING_WEBSOCKET_V2_PATH = "/ws/v2";
  public static final String HUOBI_MARKET_WEBSOCKET_PATH = "/ws";

  public static final String AUTH_VERSION_V1 = "v1";
  public static final String AUTH_VERSION_V2 = "v2";

  private long lastReceivedTime;

  private WebSocket webSocket;

  private Request okhttpRequest;

  private ConnectionStateEnum state;

  private Long id;

  private List<String> commandList;

  private HuobiModelParser parser;

  private ResponseCallback callback;

  private boolean autoClose;

  private boolean authNeed;

  private Options options;

  private long delayInSecond;

  private String host;

  private String authVersion = AUTH_VERSION_V1;

  private HuobiWebSocketConnection() {}

  public static HuobiWebSocketConnection createAssetConnection(Options options,
      List<String> commandList,
      HuobiModelParser parser,
      ResponseCallback callback,
      Boolean autoClose) {

    return createConnection(options, commandList, parser, callback, autoClose, true);
  }

  public static HuobiWebSocketConnection createAssetV2Connection(Options options,
      List<String> commandList,
      HuobiModelParser parser,
      ResponseCallback callback,
      Boolean autoClose) {

    return createConnection(options, commandList, parser, callback, autoClose, true, AUTH_VERSION_V2);
  }

  public static HuobiWebSocketConnection createMarketConnection(Options options,
      List<String> commandList,
      HuobiModelParser parser,
      ResponseCallback callback,
      boolean autoClose) {
    return createConnection(options, commandList, parser, callback, autoClose, false);
  }

  public static HuobiWebSocketConnection createConnection(Options options,
      List<String> commandList,
      HuobiModelParser parser,
      ResponseCallback callback,
      Boolean autoClose,
      boolean authNeed) {
    return createConnection(options, commandList, parser, callback, autoClose, authNeed, AUTH_VERSION_V1);
  }

  public static HuobiWebSocketConnection createConnection(Options options,
      List<String> commandList,
      HuobiModelParser parser,
      ResponseCallback callback,
      Boolean autoClose,
      boolean authNeed,
      String authVersion) {

    HuobiWebSocketConnection connection = new HuobiWebSocketConnection();
    connection.setOptions(options);
    connection.setCommandList(commandList);
    connection.setParser(parser);
    connection.setCallback(callback);
    connection.setAuthNeed(authNeed);
    connection.setAutoClose(autoClose);
    connection.setId(IdGenerator.getNextId());
    connection.setAuthVersion(authVersion);

    // 创建websocket请求
    String url = options.getWebSocketHost() + HUOBI_MARKET_WEBSOCKET_PATH;
    if (authNeed) {
      if (AUTH_VERSION_V1.equals(authVersion)) {
        url = options.getWebSocketHost() + HUOBI_TRADING_WEBSOCKET_PATH;
      } else if (AUTH_VERSION_V2.equals(authVersion)) {
        url = options.getWebSocketHost() + HUOBI_TRADING_WEBSOCKET_V2_PATH;
      }
    }
    Request request = new Request.Builder().url(url).build();
    connection.setOkhttpRequest(request);

    try {
      connection.setHost(new URL(options.getRestHost()).getHost());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    // 开启链接
    connection.connect();
    return connection;
  }


  void connect() {
    if (state == ConnectionStateEnum.CONNECTED) {
      log.info("[Connection][" + this.getId() + "] Already connected");
      return;
    }
    log.info("[Connection][" + this.getId() + "] Connecting...");
    webSocket = ConnectionFactory.createWebSocket(okhttpRequest, this);
  }

  public void reConnect(int delayInSecond) {
    log.warn("[Sub][" + this.getId() + "] Reconnecting after "
        + delayInSecond + " seconds later");
    if (webSocket != null) {
      webSocket.cancel();
      webSocket = null;
    }
    this.delayInSecond = delayInSecond;
    state = ConnectionStateEnum.DELAY_CONNECT;
  }

  public void reConnect() {
    if (delayInSecond != 0) {
      delayInSecond--;
    } else {
      connect();
    }
  }


  public long getLastReceivedTime() {
    return this.lastReceivedTime;
  }

  void send(List<String> commandList) {
    if (commandList == null || commandList.size() <= 0) {
      return;
    }
    commandList.forEach(command -> {
      send(command);
    });
  }

  public void send(String str) {
    boolean result = false;
    log.info("[Connection Send]{}", str);
    if (webSocket != null) {
      result = webSocket.send(str);
    }
    if (!result) {
      log.error("[Connection Send][" + this.getId() + "] Failed to send message");
      closeOnError();
    }
  }

  @Override
  public void onMessage(WebSocket webSocket, String text) {
    super.onMessage(webSocket, text);
    lastReceivedTime = System.currentTimeMillis();

    log.debug("[On Message Text]:{}", text);
    try {
      JSONObject json = JSON.parseObject(text);

      if (json.containsKey("action")) {
        String action = json.getString("action");
        if ("ping".equals(action)) {
          processPingOnV2TradingLine(json, webSocket);
        } else if ("push".equals(action)) {
          onReceive(json);
        }
        if ("req".equals(action)) {
          String ch = json.getString("ch");
          if ("auth".equals(ch)) {
            send(commandList);
          }

        }

      }

    } catch (Exception e) {
      log.error("[On Message][{}]: catch exception:", this.getId(), e);
      closeOnError();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onMessage(WebSocket webSocket, ByteString bytes) {
    super.onMessage(webSocket, bytes);
    try {

      lastReceivedTime = System.currentTimeMillis();

      String data;
      try {
        data = new String(InternalUtils.decode(bytes.toByteArray()));
      } catch (IOException e) {
        log.error("[Connection On Message][" + this.getId() + "] Receive message error: " + e.getMessage());
        closeOnError();
        return;
      }
      log.debug("[Connection On Message][{}] {}", this.getId(), data);
      JSONObject jsonObject = JSON.parseObject(data);

      if (jsonObject.containsKey("status") && !"ok".equals(jsonObject.getString("status"))) {
        String errorCode = jsonObject.getString("err-code");
        String errorMsg = jsonObject.getString("err-msg");
        onError(errorCode + ": " + errorMsg, null);
        log.error("[Connection On Message][" + this.getId() + "] Got error from server: " + errorCode + "; " + errorMsg);
        close();
      } else if (jsonObject.containsKey("op")) {
        String op = jsonObject.getString("op");
        if (op.equals("notify")) {
          onReceive(jsonObject);
        } else if (op.equals("ping")) {
          processPingOnTradingLine(jsonObject, webSocket);
        } else if (op.equals("auth")) {
          send(commandList);
        } else if (op.equals("req")) {
          onReceiveAndClose(jsonObject);
        }
      } else if (jsonObject.containsKey("ch") || jsonObject.containsKey("rep")) {
        onReceiveAndClose(jsonObject);
      } else if (jsonObject.containsKey("ping")) {
        processPingOnMarketLine(jsonObject, webSocket);
      } else if (jsonObject.containsKey("subbed")) {
      }
    } catch (Exception e) {
      log.error("[Connection On Message][" + this.getId() + "] Unexpected error: " + e.getMessage());
      closeOnError();
    }
  }

  private void onError(String errorMessage, Throwable e) {
    log.error("[Connection error][" + this.getId() + "] " + errorMessage);
    closeOnError();
  }

  private void onReceiveAndClose(JSONObject jsonObject) {
    onReceive(jsonObject);
    if (autoClose) {
      close();
    }
  }

  @SuppressWarnings("unchecked")
  private void onReceive(JSONObject jsonObject) {
    Object obj = null;
    try {
      obj = parser.parse(jsonObject);
    } catch (Exception e) {
      onError("Process error: " + e.getMessage() + " You should capture the exception in your error handler", e);
      return;
    }

    callback.onResponse(obj);
  }

  private void processPingOnTradingLine(JSONObject jsonObject, WebSocket webSocket) {
    long ts = jsonObject.getLong("ts");
    webSocket.send(String.format("{\"op\":\"pong\",\"ts\":%d}", ts));
  }

  private void processPingOnMarketLine(JSONObject jsonObject, WebSocket webSocket) {
    long ts = jsonObject.getLong("ping");
    webSocket.send(String.format("{\"pong\":%d}", ts));
  }

  private void processPingOnV2TradingLine(JSONObject jsonWrapper, WebSocket webSocket) {
    long ts = jsonWrapper.getJSONObject("data").getLong("ts");
    String pong = String.format("{\"action\": \"pong\",\"params\": {\"ts\": %d}}", ts);
    webSocket.send(pong);
  }

  public ConnectionStateEnum getState() {
    return state;
  }

  @Override
  public Long getConnectionId() {
    return this.getId();
  }

  public void close() {
    log.error("[Connection close][" + this.getId() + "] Closing normally");
    webSocket.cancel();
    webSocket = null;
    WebSocketWatchDog.onClosedNormally(this);
  }

  @Override
  public void onClosed(WebSocket webSocket, int code, String reason) {
    super.onClosed(webSocket, code, reason);
    if (state == ConnectionStateEnum.CONNECTED) {
      state = ConnectionStateEnum.IDLE;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onOpen(WebSocket webSocket, Response response) {
    super.onOpen(webSocket, response);
    this.webSocket = webSocket;
    log.info("[Connection][" + this.getId() + "] Connected to server");
    if (options.isWebSocketAutoConnect()) {
      WebSocketWatchDog.onConnectionCreated(this);
    }

    state = ConnectionStateEnum.CONNECTED;
    lastReceivedTime = System.currentTimeMillis();

    if (authNeed) {
      if (AUTH_VERSION_V1.equals(this.getAuthVersion())) {
        // 老版本验签
        sendAuthV2();
      } else if (AUTH_VERSION_V2.equals(this.getAuthVersion())) {
        // 新版本2.1验签
        sendAuthV2_1();
      } else {
        onError("Unsupport signature version: " + this.getAuthVersion(), null);
        close();
        return;
      }
      InternalUtils.await(100);
    } else {

      // 不需要验签的话，直接把命令发出去就好
      commandList.forEach(command -> {
        send(command);
      });
    }
  }

  @Override
  public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    onError("Unexpected error: " + t.getMessage(), t);
    closeOnError();
  }

  private void closeOnError() {
    if (webSocket != null) {
      this.webSocket.cancel();
      state = ConnectionStateEnum.CLOSED_ON_ERROR;
      log.error("[Connection error][" + this.getId() + "] Connection is closing due to error");
    }
  }

  private void sendAuthV2_1() {
    ApiSignatureV2 as = new ApiSignatureV2();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    try {
      as.createSignature(options.getApiKey(), options.getSecretKey(), "GET", this.getHost(), HUOBI_TRADING_WEBSOCKET_V2_PATH, builder);
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
    // 需要验签的部分
    ApiSignature as = new ApiSignature();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    try {
      as.createSignature(options.getApiKey(), options.getSecretKey(), "GET", this.getHost(), HUOBI_TRADING_WEBSOCKET_PATH, builder);
    } catch (Exception e) {
      onError("Unexpected error when create the signature: " + e.getMessage(), e);
      close();
      return;
    }
    builder.putToUrl(ApiSignature.op, ApiSignature.opValue)
        .putToUrl("cid", System.currentTimeMillis());
    send(builder.buildUrlToJsonString());
  }
}
