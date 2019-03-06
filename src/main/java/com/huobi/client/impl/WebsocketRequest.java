package com.huobi.client.impl;

import com.huobi.client.SubscriptionErrorHandler;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.impl.utils.Handler;

class WebsocketRequest<T> {

  WebsocketRequest(SubscriptionListener<T> listener, SubscriptionErrorHandler errorHandler) {
    this.updateCallback = listener;
    this.errorHandler = errorHandler;
  }

  String name;
  Handler<WebSocketConnection> connectionHandler;
  Handler<WebSocketConnection> authHandler = null;
  final SubscriptionListener<T> updateCallback;
  RestApiJsonParser<T> jsonParser;
  final SubscriptionErrorHandler errorHandler;
}
