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

import com.huobi.client.impl.utils.InternalUtils;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.constant.Options;
import com.huobi.constant.enums.ConnectionStateEnum;
import com.huobi.service.huobi.parser.HuobiModelParser;
import com.huobi.service.huobi.signature.ApiSignature;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.ConnectionFactory;
import com.huobi.utils.ResponseCallback;
import com.huobi.utils.WebSocketConnection;
import com.huobi.utils.WebSocketWatchDog;

import static com.huobi.client.impl.utils.InternalUtils.decode;

@Data
@Slf4j
public class HuobiWebSocketConnection extends WebSocketListener implements WebSocketConnection {

  private static AtomicInteger CONNECTION_COUNTER = new AtomicInteger();

  public static final String HUOBI_TRADING_WEBSOCKET_PATH = "/ws/v1";
  public static final String HUOBI_MARKET_WEBSOCKET_PATH = "/ws";

  private long lastReceivedTime;

  private WebSocket webSocket;

  private Request okhttpRequest;

  private ConnectionStateEnum state;

  private Integer Id;

  private List<String> commandList;

  private HuobiModelParser parser;

  private ResponseCallback callback;

  private boolean autoClose;

  private boolean authNeed;

  private Options options;

  private long delayInSecond;

  private String host;

  private HuobiWebSocketConnection() {}

  public static HuobiWebSocketConnection createAssetConnection(Options options,
      List<String> commandList,
      HuobiModelParser parser,
      ResponseCallback callback,
      Boolean autoClose) {

    return createConnection(options, commandList, parser, callback, autoClose, true);
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

    HuobiWebSocketConnection connection = new HuobiWebSocketConnection();
    connection.setOptions(options);
    connection.setCommandList(commandList);
    connection.setParser(parser);
    connection.setCallback(callback);
    connection.setAuthNeed(authNeed);
    connection.setAutoClose(autoClose);
    connection.setId(CONNECTION_COUNTER.addAndGet(1));

    // 创建websocket请求
    String url = options.getOptionWebSocketHost() + HUOBI_MARKET_WEBSOCKET_PATH;
    if (authNeed) {
      url = options.getOptionWebSocketHost() + HUOBI_TRADING_WEBSOCKET_PATH;
    }
    Request request = new Request.Builder().url(url).build();
    connection.setOkhttpRequest(request);

    try {
      connection.setHost(new URL(options.getOptionRestHost()).getHost());
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

  void send(String str) {
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
    lastReceivedTime = TimeService.getCurrentTimeStamp();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onMessage(WebSocket webSocket, ByteString bytes) {
    super.onMessage(webSocket, bytes);
    try {

      lastReceivedTime = TimeService.getCurrentTimeStamp();

      String data;
      try {
        data = new String(decode(bytes.toByteArray()));
      } catch (IOException e) {
        log.error("[Connection On Message][" + this.getId() + "] Receive message error: " + e.getMessage());
        closeOnError();
        return;
      }
      log.info("[Connection On Message][{}] {}", this.getId(), data);
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

  public ConnectionStateEnum getState() {
    return state;
  }

  @Override
  public int getConnectionId() {
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
    WebSocketWatchDog.onConnectionCreated(this);

    state = ConnectionStateEnum.CONNECTED;
    lastReceivedTime = TimeService.getCurrentTimeStamp();

    if (authNeed) {
      // 需要验签的部分
      ApiSignature as = new ApiSignature();
      UrlParamsBuilder builder = UrlParamsBuilder.build();
      try {
        as.createSignature(options.getOptionsApiKey(), options.getOptionsSecretKey(), "GET", this.getHost(), HUOBI_TRADING_WEBSOCKET_PATH, builder);
      } catch (Exception e) {
        onError("Unexpected error when create the signature: " + e.getMessage(), e);
        close();
        return;
      }
      builder.putToUrl(ApiSignature.op, ApiSignature.opValue)
          .putToUrl("cid", TimeService.getCurrentTimeStamp());
      send(builder.buildUrlToJsonString());
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

}
