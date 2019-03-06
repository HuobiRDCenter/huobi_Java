package com.huobi.client.impl;


import com.huobi.client.SubscriptionOptions;

public class StubWebsocketConnection extends WebSocketConnection {

  private WebsocketRequest request;
  private MockOkHttpWebsocket mockWebsocket;
  static private WebSocketWatchDog watchDog = new WebSocketWatchDog(new SubscriptionOptions());

  StubWebsocketConnection(
      String apiKey, String secretKey, WebsocketRequest request, SubscriptionOptions options) {
    super(apiKey, secretKey, options, request, watchDog);
    this.request = request;
    this.mockWebsocket = new MockOkHttpWebsocket();
  }

  StubWebsocketConnection(WebsocketRequest request) {
    super("", "", new SubscriptionOptions(), request, watchDog);
    this.request = request;
    this.mockWebsocket = new MockOkHttpWebsocket();
  }

  MockOkHttpWebsocket getWebsocket() {
    return mockWebsocket;
  }

  @Override
  void send(String str) {
    mockWebsocket.send(str);
  }
}
