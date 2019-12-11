package com.huobi.client;

import com.huobi.client.impl.HuobiApiInternalFactory;
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

/***
 * The subscription client interface, it is used for subscribing any market data update and
 * account change, it is asynchronous, so you must implement the SubscriptionListener interface.
 * The server will push any update to the client. if client get the update, the onReceive method
 * will be called.
 */
public interface SubscriptionClient {

  /**
   * Subscribe candlestick/kline event. If the candlestick/kline is updated, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   */
  void subscribeCandlestickEvent(String symbols, CandlestickInterval interval,
      SubscriptionListener<CandlestickEvent> callback);

  /**
   * Subscribe candlestick/kline event. If the candlestick/kline is updated, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
   */
  void subscribeCandlestickEvent(String symbols, CandlestickInterval interval,
      SubscriptionListener<CandlestickEvent> callback,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request candlestick/kline event. If the candlestick/kline is received, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param from The query start timestamp
   * @param to The query end timestamp
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   */

  void requestCandlestickEvent(
      String symbols, Long from, Long to,
      CandlestickInterval interval,
      SubscriptionListener<CandlestickReqEvent> subscriptionListener);

  /**
   * Request candlestick/kline event. If the candlestick/kline is received, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param from The query start timestamp
   * @param to The query end timestamp
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if subscription failed or error happen * between client and Huobi server.
   */

  void requestCandlestickEvent(
      String symbols, Long from, Long to,
      CandlestickInterval interval,
      SubscriptionListener<CandlestickReqEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request candlestick/kline event. If the candlestick/kline is received, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param from The query start timestamp
   * @param to The query end timestamp
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
   * @param autoClose True or false.
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if subscription failed or error happen * between client and Huobi server.
   */

  void requestCandlestickEvent(
      String symbols, Long from, Long to,
      CandlestickInterval interval,
      boolean autoClose,
      SubscriptionListener<CandlestickReqEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe price depth event. If the price depth is updated, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   */
  void subscribePriceDepthEvent(String symbols, SubscriptionListener<PriceDepthEvent> callback);

  /**
   * Subscribe price depth event. If the price depth is updated, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
   */
  void subscribePriceDepthEvent(String symbols,
      SubscriptionListener<PriceDepthEvent> callback,
      SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe price depth event. If the price depth is updated, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param step The aggregation depth type,step0,step1,etc.
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's update.
   * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
   */

  void subscribePriceDepthEvent(
      String symbols, DepthStep step,
      SubscriptionListener<PriceDepthEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request price depth event. If the price depth is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   */

  void requestPriceDepthEvent(
      String symbols,
      SubscriptionListener<PriceDepthEvent> subscriptionListener);

  /**
   * Request price depth event. If the price depth is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   */

  void requestPriceDepthEvent(
      String symbols, DepthStep step,
      SubscriptionListener<PriceDepthEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request price depth event. If the price depth is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param autoClose True or false.
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   */

  void requestPriceDepthEvent(
      String symbols, DepthStep step, boolean autoClose,
      SubscriptionListener<PriceDepthEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe price depth event. If the price depth is updated server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   */
  void subscribeTradeEvent(String symbols, SubscriptionListener<TradeEvent> callback);

  /**
   * Subscribe trade event. If the price depth is updated, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
   */
  void subscribeTradeEvent(String symbols,
      SubscriptionListener<TradeEvent> callback,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request trade event. If the price depth is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   */

  void requestTradeEvent(
      String symbols,
      SubscriptionListener<TradeEvent> subscriptionListener);

  /**
   * Request trade event. If the price depth is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
   */

  void requestTradeEvent(
      String symbols,
      SubscriptionListener<TradeEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request trade event. If the price depth is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param autoClose True or false.
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
   */

  void requestTradeEvent(
      String symbols, boolean autoClose,
      SubscriptionListener<TradeEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe market bbo event. If the bbo is updated, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's update.
   */

  void subscribeMarketBBOEvent(String symbols, SubscriptionListener<MarketBBOEvent> subscriptionListener);

  /**
   * Subscribe market bbo event. If the bbo is updated, server will send the data to client and onReceive in callback will be called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's update.
   * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
   */

  void subscribeMarketBBOEvent(String symbols, SubscriptionListener<MarketBBOEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe account changing event. If the balance is updated, server will send the data to client and onReceive in callback will be called.
   * default to subscribe the available balance.
   *
   * @param mode The account balance mode, see {@link BalanceMode}
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   */
  void subscribeAccountEvent(BalanceMode mode, SubscriptionListener<AccountEvent> callback);

  /**
   * Subscribe account changing event. If the balance is updated, server will send the data to client and onReceive in callback will be called.
   *
   * @param mode when mode is AVAILABLE, balance refers to available balance; when mode is TOTAL, balance refers to TOTAL balance for trade sub
   * account (available+frozen).
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
   */
  void subscribeAccountEvent(BalanceMode mode, SubscriptionListener<AccountEvent> callback,
      SubscriptionErrorHandler errorHandler);

  void subscribeAccountChangeV2Event(AccountChangeModeEnum mode, SubscriptionListener<AccountChangeV2Event> callback);

  void subscribeAccountChangeV2Event(AccountChangeModeEnum mode, SubscriptionListener<AccountChangeV2Event> callback,SubscriptionErrorHandler errorHandler);


  void subscribeTradeClearing(String symbols,SubscriptionListener<TradeClearingEvent> callback);

  void subscribeTradeClearing(String symbols,SubscriptionListener<TradeClearingEvent> callback, SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe order changing event. If a order is created, canceled etc, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   */
  void subscribeOrderUpdateEvent(String symbols, SubscriptionListener<OrderUpdateEvent> callback);

  /**
   * Subscribe order changing event. If a order is created, canceled etc, server will send the data to client and onReceive in callback will be
   * called.(recommend to use it)
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   */
  void subscribeOrderUpdateNewEvent(String symbols, SubscriptionListener<OrderUpdateNewEvent> callback);

  /**
   * Request order list event. If order list is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param ordersRequest The order list request.
   * @param callback The implementation is required. onReceive will be called if receive server's response.
   */
  void requestOrderListEvent(OrdersRequest ordersRequest, SubscriptionListener<OrderListEvent> callback);

  /**
   * Request  order detail . If order detail is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param callback The implementation is required. onReceive will be called if receive server's response.
   */
  void requestOrderDetailEvent(Long orderId, SubscriptionListener<OrderListEvent> callback);

  /**
   * Subscribe order changing event. If a order is created, canceled etc, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
   */
  void subscribeOrderUpdateEvent(String symbols, SubscriptionListener<OrderUpdateEvent> callback,
      SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe order changing event. If a order is created, canceled etc, server will send the data to client and onReceive in callback will be
   * called.(recommend to use it)
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
   */

  void subscribeOrderUpdateNewEvent(String symbols, SubscriptionListener<OrderUpdateNewEvent> callback,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request order list event. If order list is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param ordersRequest The order list request.
   * @param callback The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if request failed or error happen between client and Huobi server.
   */

  void requestOrderListEvent(OrdersRequest ordersRequest, SubscriptionListener<OrderListEvent> callback,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request  order detail . If order detail is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param callback The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if request failed or error happen between client and Huobi server.
   */

  void requestOrderDetailEvent(Long orderId, SubscriptionListener<OrderListEvent> callback,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request order list event. If order list is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param ordersRequest The order list request.
   * @param callback The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if request failed or error happen between client and Huobi server.
   */

  void requestOrderListEvent(OrdersRequest ordersRequest, boolean autoClose, SubscriptionListener<OrderListEvent> callback,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request  order detail . If order detail is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param callback The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if request failed or error happen between client and Huobi server.
   */

  void requestOrderDetailEvent(Long orderId, boolean autoClose, SubscriptionListener<OrderListEvent> callback,
      SubscriptionErrorHandler errorHandler);

  /**
   * Subscribe 24 hours trade statistics event. If statistics is generated, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   */
  void subscribe24HTradeStatisticsEvent(String symbols,
      SubscriptionListener<TradeStatisticsEvent> callback);

  /**
   * Subscribe 24 hours trade statistics event. If statistics is generated, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param callback The implementation is required. onReceive will be called if receive server's update.
   * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
   */
  void subscribe24HTradeStatisticsEvent(String symbols,
      SubscriptionListener<TradeStatisticsEvent> callback, SubscriptionErrorHandler errorHandler);

  /**
   * Request 24 hours trade statistics event. If statistics is received, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   */

  void request24HTradeStatisticsEvent(String symbols,
      SubscriptionListener<TradeStatisticsEvent> subscriptionListener);

  /**
   * Request 24 hours trade statistics event. If statistics is received, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if request failed or error happen between client and Huobi server.
   */

  void request24HTradeStatisticsEvent(String symbols,
      SubscriptionListener<TradeStatisticsEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request 24 hours trade statistics event. If statistics is received, server will send the data to client and onReceive in callback will be
   * called.
   *
   * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
   * @param autoClose True or false.
   * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if request failed or error happen between client and Huobi server.
   */

  void request24HTradeStatisticsEvent(String symbols, boolean autoClose,
      SubscriptionListener<TradeStatisticsEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler);

  /**
   * Request account list . If account list is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param callback The implementation is required. onReceive will be called if receive server's response.
   */

  void requestAccountListEvent(SubscriptionListener<AccountListEvent> callback);

  /**
   * Request account list . If account list is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param callback The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if request failed or error happen between client and Huobi server.
   */
  void requestAccountListEvent(SubscriptionListener<AccountListEvent> callback, SubscriptionErrorHandler errorHandler);

  /**
   * Request account list . If account list is received, server will send the data to client and onReceive in callback will be called.
   *
   * @param autoClose True or false.
   * @param callback The implementation is required. onReceive will be called if receive server's response.
   * @param errorHandler The error handler will be called if request failed or error happen between client and Huobi server.
   */
  void requestAccountListEvent(boolean autoClose, SubscriptionListener<AccountListEvent> callback,
      SubscriptionErrorHandler errorHandler);

  void subscribeMarketDepthMBP(String symbol, MBPLevelEnums level, SubscriptionListener<MarketDepthMBPEvent> callback);

  void subscribeMarketDepthMBP(String symbol, MBPLevelEnums level, SubscriptionListener<MarketDepthMBPEvent> callback,
      SubscriptionErrorHandler errorHandler);

  void requestMarketDepthMBP(String symbol, MBPLevelEnums level, SubscriptionListener<MarketDepthMBPEvent> callback);

  void requestMarketDepthMBP(String symbol, MBPLevelEnums level, SubscriptionListener<MarketDepthMBPEvent> callback,
      SubscriptionErrorHandler errorHandler);

  /**
   * Unsubscribe all subscription.
   */
  void unsubscribeAll();

  /**
   * Create the subscription client to subscribe the update from server.
   *
   * @return The instance of synchronous client.
   */
  static SubscriptionClient create() {
    return create("", "", new SubscriptionOptions());
  }

  /**
   * Create the subscription client to subscribe the update from server.
   *
   * @param apiKey The public key applied from Huobi.
   * @param secretKey The private key applied from Huobi.
   * @return The instance of synchronous client.
   */
  static SubscriptionClient create(
      String apiKey, String secretKey) {
    return HuobiApiInternalFactory.getInstance().createSubscriptionClient(
        apiKey, secretKey, new SubscriptionOptions());
  }

  /**
   * Create the subscription client to subscribe the update from server.
   *
   * @param apiKey The public key applied from Huobi.
   * @param secretKey The private key applied from Huobi.
   * @param subscriptionOptions The option of subscription connection, see {@link SubscriptionOptions}
   * @return The instance of synchronous client.
   */
  static SubscriptionClient create(
      String apiKey, String secretKey, SubscriptionOptions subscriptionOptions) {
    return HuobiApiInternalFactory.getInstance().createSubscriptionClient(
        apiKey, secretKey, subscriptionOptions);
  }

}
