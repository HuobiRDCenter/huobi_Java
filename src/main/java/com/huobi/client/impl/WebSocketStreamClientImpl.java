package com.huobi.client.impl;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.SubscriptionErrorHandler;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.SubscriptionOptions;
import com.huobi.client.model.enums.AccountChangeModeEnum;
import com.huobi.client.model.enums.BalanceMode;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.enums.DepthStep;
import com.huobi.client.model.enums.MBPLevelEnums;
import com.huobi.client.model.event.AccountChangeV2Event;
import com.huobi.client.model.event.AccountEvent;
import com.huobi.client.model.event.AccountListEvent;
import com.huobi.client.model.event.CandlestickEvent;
import com.huobi.client.model.event.CandlestickReqEvent;
import com.huobi.client.model.event.MarketBBOEvent;
import com.huobi.client.model.event.MarketDepthMBPEvent;
import com.huobi.client.model.event.OrderListEvent;
import com.huobi.client.model.event.OrderUpdateEvent;
import com.huobi.client.model.event.OrderUpdateNewEvent;
import com.huobi.client.model.event.PriceDepthEvent;
import com.huobi.client.model.event.TradeClearingEvent;
import com.huobi.client.model.event.TradeEvent;
import com.huobi.client.model.event.TradeStatisticsEvent;
import com.huobi.client.model.request.OrdersRequest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WebSocketStreamClientImpl implements SubscriptionClient {

  private final SubscriptionOptions options;
  private WebSocketWatchDog watchDog;

  private final WebsocketRequestImpl requestImpl;

  private final List<WebSocketConnection> connections = new LinkedList<>();

  private final String apiKey;

  private final String secretKey;

  WebSocketStreamClientImpl(String apiKey, String secretKey, SubscriptionOptions options) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;
    this.watchDog = null;
    this.options = Objects.requireNonNull(options);

    this.requestImpl = new WebsocketRequestImpl(apiKey);
  }

  private <T> void createConnection(WebsocketRequest<T> request, boolean autoClose) {
    if (watchDog == null) {
      watchDog = new WebSocketWatchDog(options);
    }
    WebSocketConnection connection = new WebSocketConnection(
        apiKey, secretKey, options, request, watchDog, autoClose);
    if (autoClose == false) {
      connections.add(connection);
    }
    connection.connect();
  }

  private <T> void createConnection(WebsocketRequest<T> request) {
    createConnection(request, false);
  }

  private List<String> parseSymbols(String symbol) {
    return Arrays.asList(symbol.split("[,]"));
  }

  @Override
  public void subscribeCandlestickEvent(
      String symbols,
      CandlestickInterval interval,
      SubscriptionListener<CandlestickEvent> callback) {
    subscribeCandlestickEvent(symbols, interval, callback, null);
  }

  @Override
  public void subscribeCandlestickEvent(
      String symbols,
      CandlestickInterval interval,
      SubscriptionListener<CandlestickEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.subscribeCandlestickEvent(
        parseSymbols(symbols), interval, subscriptionListener, errorHandler));
  }

  @Override
  public void requestCandlestickEvent(
      String symbols, Long from, Long to,
      CandlestickInterval interval,
      SubscriptionListener<CandlestickReqEvent> subscriptionListener) {
    requestCandlestickEvent(symbols, from, to, interval, subscriptionListener, null);
  }

  @Override
  public void requestCandlestickEvent(
      String symbols, Long from, Long to,
      CandlestickInterval interval,
      SubscriptionListener<CandlestickReqEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    requestCandlestickEvent(symbols, from, to, interval, true, subscriptionListener, errorHandler);
  }

  public void requestCandlestickEvent(
      String symbols, Long from, Long to,
      CandlestickInterval interval,
      boolean autoClose,
      SubscriptionListener<CandlestickReqEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.requestCandlestickEvent(
        parseSymbols(symbols), from, to, interval, subscriptionListener, errorHandler), autoClose);
  }

  @Override
  public void subscribePriceDepthEvent(
      String symbols,
      SubscriptionListener<PriceDepthEvent> subscriptionListener) {
    subscribePriceDepthEvent(symbols, subscriptionListener, null);
  }

  @Override
  public void subscribePriceDepthEvent(
      String symbols,
      SubscriptionListener<PriceDepthEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    subscribePriceDepthEvent(symbols, DepthStep.STEP0, subscriptionListener, errorHandler);
  }

  @Override
  public void subscribePriceDepthEvent(
      String symbols, DepthStep step,
      SubscriptionListener<PriceDepthEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.subscribePriceDepthEvent(
        parseSymbols(symbols), step, subscriptionListener, errorHandler));
  }

  @Override
  public void requestPriceDepthEvent(
      String symbols,
      SubscriptionListener<PriceDepthEvent> subscriptionListener) {
    requestPriceDepthEvent(symbols, DepthStep.STEP0, subscriptionListener, null);
  }

  @Override
  public void requestPriceDepthEvent(
      String symbols, DepthStep step,
      SubscriptionListener<PriceDepthEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    requestPriceDepthEvent(symbols, step, true, subscriptionListener, errorHandler);
  }

  @Override
  public void requestPriceDepthEvent(
      String symbols, DepthStep step, boolean autoClose,
      SubscriptionListener<PriceDepthEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.requestPriceDepthEvent(
        parseSymbols(symbols), step, subscriptionListener, errorHandler), autoClose);
  }

  @Override
  public void subscribeTradeEvent(
      String symbols,
      SubscriptionListener<TradeEvent> subscriptionListener) {
    subscribeTradeEvent(symbols, subscriptionListener, null);
  }

  @Override
  public void subscribeTradeEvent(
      String symbols,
      SubscriptionListener<TradeEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.subscribeTradeEvent(
        parseSymbols(symbols), subscriptionListener, errorHandler));
  }

  @Override
  public void requestTradeEvent(
      String symbols,
      SubscriptionListener<TradeEvent> subscriptionListener) {
    requestTradeEvent(symbols, subscriptionListener, null);
  }

  @Override
  public void requestTradeEvent(
      String symbols,
      SubscriptionListener<TradeEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    requestTradeEvent(symbols, true, subscriptionListener, errorHandler);
  }

  @Override
  public void requestTradeEvent(
      String symbols, boolean autoClose,
      SubscriptionListener<TradeEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.requestTradeEvent(
        parseSymbols(symbols), subscriptionListener, errorHandler), autoClose);
  }

  public void subscribeMarketBBOEvent(String symbols, SubscriptionListener<MarketBBOEvent> subscriptionListener) {
    subscribeMarketBBOEvent(symbols, subscriptionListener, null);
  }

  public void subscribeMarketBBOEvent(String symbols, SubscriptionListener<MarketBBOEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.subscribeMarketBBOEvent(parseSymbols(symbols), subscriptionListener, errorHandler));
  }

  @Override
  public void subscribeOrderUpdateEvent(
      String symbols,
      SubscriptionListener<OrderUpdateEvent> subscriptionListener) {
    subscribeOrderUpdateEvent(symbols, subscriptionListener, null);
  }

  @Override
  public void subscribeOrderUpdateNewEvent(String symbols, SubscriptionListener<OrderUpdateNewEvent> callback) {
    subscribeOrderUpdateNewEvent(symbols, callback, null);
  }

  @Override
  public void requestOrderListEvent(OrdersRequest ordersRequest, SubscriptionListener<OrderListEvent> callback) {
    requestOrderListEvent(ordersRequest, callback, null);
  }

  public void requestOrderDetailEvent(Long orderId, SubscriptionListener<OrderListEvent> callback) {
    requestOrderDetailEvent(orderId, callback, null);
  }

  @Override
  public void subscribeOrderUpdateEvent(
      String symbols,
      SubscriptionListener<OrderUpdateEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.subscribeOrderUpdateEvent(
        parseSymbols(symbols), subscriptionListener, errorHandler));
  }

  @Override
  public void subscribeOrderUpdateNewEvent(String symbols, SubscriptionListener<OrderUpdateNewEvent> callback,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.subscribeOrderUpdateNewEvent(
        parseSymbols(symbols), callback, errorHandler));
  }

  @Override
  public void requestOrderListEvent(OrdersRequest ordersRequest, SubscriptionListener<OrderListEvent> callback,
      SubscriptionErrorHandler errorHandler) {
    requestOrderListEvent(ordersRequest, true, callback, errorHandler);
  }

  @Override
  public void requestOrderDetailEvent(Long orderId, SubscriptionListener<OrderListEvent> callback,
      SubscriptionErrorHandler errorHandler) {
    requestOrderDetailEvent(orderId, true, callback, errorHandler);
  }

  @Override
  public void requestOrderListEvent(OrdersRequest ordersRequest, boolean autoClose, SubscriptionListener<OrderListEvent> callback,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.requestOrderListEvent(
        ordersRequest, callback, errorHandler), autoClose);
  }

  @Override
  public void requestOrderDetailEvent(Long orderId, boolean autoClose, SubscriptionListener<OrderListEvent> callback,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.requestOrderDetailEvent(orderId, callback, errorHandler), autoClose);
  }


  @Override
  public void unsubscribeAll() {
    for (WebSocketConnection connection : connections) {
      watchDog.onClosedNormally(connection);
      connection.close();
    }
    connections.clear();
  }

  @Override
  public void subscribeAccountEvent(
      BalanceMode mode,
      SubscriptionListener<AccountEvent> subscriptionListener) {
    subscribeAccountEvent(mode, subscriptionListener, null);
  }

  @Override
  public void subscribeAccountEvent(
      BalanceMode mode,
      SubscriptionListener<AccountEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.subscribeAccountEvent(mode, subscriptionListener, errorHandler));
  }


  public void subscribeAccountChangeV2Event(AccountChangeModeEnum mode, SubscriptionListener<AccountChangeV2Event> callback) {
    subscribeAccountChangeV2Event(mode, callback, null);
  }

  public void subscribeAccountChangeV2Event(AccountChangeModeEnum mode, SubscriptionListener<AccountChangeV2Event> callback,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.subscribeAccountChangeV2Event(mode, callback, errorHandler));
  }

  public void subscribeTradeClearing(String symbols,SubscriptionListener<TradeClearingEvent> callback) {
    subscribeTradeClearing(symbols,callback,null);
  }

  public void subscribeTradeClearing(String symbols,SubscriptionListener<TradeClearingEvent> callback, SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.subscribeTradeClearing(parseSymbols(symbols), callback, errorHandler));
  }


  @Override
  public void subscribe24HTradeStatisticsEvent(String symbols,
      SubscriptionListener<TradeStatisticsEvent> subscriptionListener) {
    subscribe24HTradeStatisticsEvent(symbols, subscriptionListener, null);
  }

  @Override
  public void subscribe24HTradeStatisticsEvent(String symbols,
      SubscriptionListener<TradeStatisticsEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.subscribe24HTradeStatisticsEvent(
        parseSymbols(symbols), subscriptionListener, errorHandler));
  }

  @Override
  public void request24HTradeStatisticsEvent(String symbols,
      SubscriptionListener<TradeStatisticsEvent> subscriptionListener) {
    request24HTradeStatisticsEvent(symbols, subscriptionListener, null);
  }

  @Override
  public void request24HTradeStatisticsEvent(String symbols,
      SubscriptionListener<TradeStatisticsEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    request24HTradeStatisticsEvent(symbols, true, subscriptionListener, errorHandler);
  }

  public void request24HTradeStatisticsEvent(String symbols, boolean autoClose,
      SubscriptionListener<TradeStatisticsEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.request24HTradeStatisticsEvent(
        parseSymbols(symbols), subscriptionListener, errorHandler), autoClose);
  }

  @Override
  public void requestAccountListEvent(SubscriptionListener<AccountListEvent> callback) {
    requestAccountListEvent(true, callback, null);
  }

  @Override
  public void requestAccountListEvent(SubscriptionListener<AccountListEvent> callback,
      SubscriptionErrorHandler errorHandler) {
    requestAccountListEvent(true, callback, errorHandler);
  }

  @Override
  public void requestAccountListEvent(boolean autoClose, SubscriptionListener<AccountListEvent> callback,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.requestAccountListEvent(callback, errorHandler), autoClose);
  }

  public void subscribeMarketDepthMBP(String symbol, MBPLevelEnums level, SubscriptionListener<MarketDepthMBPEvent> callback) {
    subscribeMarketDepthMBP(symbol, level, callback, null);
  }

  @Override
  public void subscribeMarketDepthMBP(String symbol, MBPLevelEnums level, SubscriptionListener<MarketDepthMBPEvent> callback,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.subscribeMarketDepthMBPEvent(symbol, level, callback, errorHandler));
  }


  public void requestMarketDepthMBP(String symbol, MBPLevelEnums level, SubscriptionListener<MarketDepthMBPEvent> callback) {
    requestMarketDepthMBP(symbol, level, callback, null);
  }

  @Override
  public void requestMarketDepthMBP(String symbol, MBPLevelEnums level, SubscriptionListener<MarketDepthMBPEvent> callback,
      SubscriptionErrorHandler errorHandler) {
    createConnection(requestImpl.requestMarketDepthMBPEvent(symbol, level, callback, errorHandler), true);
  }
}
