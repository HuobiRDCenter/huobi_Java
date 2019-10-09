package com.huobi.client.impl;

import static com.huobi.client.impl.utils.InternalUtils.await;

import com.huobi.client.SubscriptionErrorHandler;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.Channels;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.Account;
import com.huobi.client.model.AccountChange;
import com.huobi.client.model.Balance;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.DepthEntry;
import com.huobi.client.model.Order;
import com.huobi.client.model.OrderUpdate;
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.Trade;
import com.huobi.client.model.TradeStatistics;
import com.huobi.client.model.enums.AccountChangeType;
import com.huobi.client.model.enums.AccountState;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceMode;
import com.huobi.client.model.enums.BalanceType;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.enums.DealRole;
import com.huobi.client.model.enums.DepthStep;
import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.enums.StopOrderOperator;
import com.huobi.client.model.enums.TradeDirection;
import com.huobi.client.model.event.AccountEvent;
import com.huobi.client.model.event.AccountListEvent;
import com.huobi.client.model.event.CandlestickEvent;
import com.huobi.client.model.event.CandlestickReqEvent;
import com.huobi.client.model.event.MarketBBOEvent;
import com.huobi.client.model.event.OrderListEvent;
import com.huobi.client.model.event.OrderUpdateEvent;
import com.huobi.client.model.event.OrderUpdateNewEvent;
import com.huobi.client.model.event.PriceDepthEvent;
import com.huobi.client.model.event.TradeEvent;
import com.huobi.client.model.event.TradeStatisticsEvent;
import com.huobi.client.model.request.OrdersRequest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;

class WebsocketRequestImpl {

  private final String apiKey;

  WebsocketRequestImpl(String apiKey) {
    this.apiKey = apiKey;
  }

  WebsocketRequest<CandlestickEvent> subscribeCandlestickEvent(
      List<String> symbols,
      CandlestickInterval interval,
      SubscriptionListener<CandlestickEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    InputChecker.checker()
        .checkSymbolList(symbols)
        .shouldNotNull(subscriptionListener, "listener")
        .shouldNotNull(interval, "CandlestickInterval");
    WebsocketRequest<CandlestickEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    if (symbols.size() == 1) {
      request.name = "Candlestick for " + symbols;
    } else {
      request.name = "Candlestick for " + symbols + " ...";
    }
    request.connectionHandler = (connection) ->
        symbols.stream()
            .map((symbol) -> Channels.klineChannel(symbol, interval))
            .forEach(req -> {
              connection.send(req);
              await(1);
            });
    request.jsonParser = CandlestickEvent.getParser(interval);
    return request;
  }


  WebsocketRequest<CandlestickReqEvent> requestCandlestickEvent(
      List<String> symbols, Long from, Long to,
      CandlestickInterval interval,
      SubscriptionListener<CandlestickReqEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {

    InputChecker.checker()
        .checkSymbolList(symbols)
        .shouldNotNull(subscriptionListener, "listener")
        .shouldNotNull(interval, "CandlestickInterval");
    WebsocketRequest<CandlestickReqEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    if (symbols.size() == 1) {
      request.name = "Candlestick for " + symbols;
    } else {
      request.name = "Candlestick for " + symbols + " ...";
    }
    request.connectionHandler = (connection) ->
        symbols.stream()
            .map((symbol) -> Channels.klineReqChannel(symbol, interval, from, to))
            .forEach(req -> {
              connection.send(req);
              await(1);
            });
    request.jsonParser = CandlestickReqEvent.getParser(interval);
    return request;


  }

  WebsocketRequest<TradeEvent> subscribeTradeEvent(
      List<String> symbols,
      SubscriptionListener<TradeEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    InputChecker.checker().checkSymbolList(symbols).shouldNotNull(subscriptionListener, "listener");
    WebsocketRequest<TradeEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    if (symbols.size() == 1) {
      request.name = "Trade for " + symbols;
    } else {
      request.name = "Trade for " + symbols + " ...";
    }
    request.connectionHandler = (connection) ->
        symbols.stream()
            .map(Channels::tradeChannel)
            .forEach(req -> {
              connection.send(req);
              await(1);
            });
    request.jsonParser = TradeEvent.getParser();
    return request;
  }

  WebsocketRequest<TradeEvent> requestTradeEvent(
      List<String> symbols,
      SubscriptionListener<TradeEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    InputChecker.checker().checkSymbolList(symbols).shouldNotNull(subscriptionListener, "listener");
    WebsocketRequest<TradeEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    if (symbols.size() == 1) {
      request.name = "Trade Req for " + symbols;
    } else {
      request.name = "Trade Req for " + symbols + " ...";
    }
    request.connectionHandler = (connection) ->
        symbols.forEach(symbol -> {
          String req = Channels.tradeChannel(Channels.OP_REQ, symbol);
          connection.send(req);
          await(1);
        });
    request.jsonParser = TradeEvent.getReqParser();
    return request;
  }


  WebsocketRequest<PriceDepthEvent> requestPriceDepthEvent(
      List<String> symbols, DepthStep step,
      SubscriptionListener<PriceDepthEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    InputChecker.checker().checkSymbolList(symbols).shouldNotNull(subscriptionListener, "listener");

    WebsocketRequest<PriceDepthEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    if (symbols.size() == 1) {
      request.name = "PriceDepth Req for " + symbols;
    } else {
      request.name = "PriceDepth Req  for " + symbols + " ...";
    }
    request.connectionHandler = (connection) ->
        symbols.forEach(symbol -> {
          String req = Channels.priceDepthChannel(Channels.OP_REQ, symbol, step);
          connection.send(req);
          await(1);
        });
    request.jsonParser = PriceDepthEvent.getReqParser();
    return request;
  }

  WebsocketRequest<PriceDepthEvent> subscribePriceDepthEvent(
      List<String> symbols,
      SubscriptionListener<PriceDepthEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    return subscribePriceDepthEvent(symbols, DepthStep.STEP0, subscriptionListener, errorHandler);
  }

  WebsocketRequest<PriceDepthEvent> subscribePriceDepthEvent(
      List<String> symbols, DepthStep step,
      SubscriptionListener<PriceDepthEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {

    InputChecker.checker().checkSymbolList(symbols).shouldNotNull(subscriptionListener, "listener");

    WebsocketRequest<PriceDepthEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    if (symbols.size() == 1) {
      request.name = "PriceDepth for " + symbols;
    } else {
      request.name = "PriceDepth for " + symbols + " ...";
    }
    request.connectionHandler = (connection) ->
        symbols.forEach(symbol -> {
          String req = Channels.priceDepthChannel(symbol, step);
          connection.send(req);
          await(1);
        });
    request.jsonParser = PriceDepthEvent.getParser();
    return request;
  }


  WebsocketRequest<MarketBBOEvent> subscribeMarketBBOEvent(
      List<String> symbols,
      SubscriptionListener<MarketBBOEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {

    InputChecker.checker().checkSymbolList(symbols).shouldNotNull(subscriptionListener, "listener");

    WebsocketRequest<MarketBBOEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    if (symbols.size() == 1) {
      request.name = "MarketBBO for " + symbols;
    } else {
      request.name = "MarketBBO for " + symbols + " ...";
    }

    request.connectionHandler = (connection) ->
        symbols.forEach(symbol -> {
          String req = Channels.marketBBOChannel(symbol);
          connection.send(req);
          await(1);
        });
    request.jsonParser = MarketBBOEvent.getParser();
    return request;
  }



  WebsocketRequest<OrderUpdateEvent> subscribeOrderUpdateEvent(
      List<String> symbols,
      SubscriptionListener<OrderUpdateEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    InputChecker.checker().checkSymbolList(symbols).shouldNotNull(subscriptionListener, "listener");
    WebsocketRequest<OrderUpdateEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    if (symbols.size() == 1) {
      request.name = "OrderUpdate for " + symbols;
    } else {
      request.name = "OrderUpdate for " + symbols + " ...";
    }
    request.authHandler = (connection) ->
        symbols.stream()
            .map(Channels::ordersChannel)
            .forEach(req -> {
              connection.send(req);
              await(1);
            });
    request.jsonParser = OrderUpdateEvent.getParser(apiKey);
    return request;
  }

  WebsocketRequest<OrderUpdateNewEvent> subscribeOrderUpdateNewEvent(
      List<String> symbols,
      SubscriptionListener<OrderUpdateNewEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    InputChecker.checker().checkSymbolList(symbols).shouldNotNull(subscriptionListener, "listener");
    WebsocketRequest<OrderUpdateNewEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    if (symbols.size() == 1) {
      request.name = "OrderUpdateNew for " + symbols;
    } else {
      request.name = "OrderUpdateNew for " + symbols + " ...";
    }
    request.authHandler = (connection) ->
        symbols.stream()
            .map(Channels::ordersChannelNew)
            .forEach(req -> {
              connection.send(req);
              await(1);
            });
    request.jsonParser = OrderUpdateNewEvent.getParser();
    return request;
  }

  WebsocketRequest<AccountEvent> subscribeAccountEvent(
      BalanceMode mode,
      SubscriptionListener<AccountEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    InputChecker.checker().shouldNotNull(subscriptionListener, "listener");
    WebsocketRequest<AccountEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    request.name = "Account";
    request.authHandler = (connection) ->
        connection.send(Channels.accountChannel(mode));
    request.jsonParser = AccountEvent.getParser(apiKey);
    return request;
  }

  WebsocketRequest<TradeStatisticsEvent> subscribe24HTradeStatisticsEvent(
      List<String> symbols,
      SubscriptionListener<TradeStatisticsEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    InputChecker.checker().checkSymbolList(symbols).shouldNotNull(subscriptionListener, "listener");
    WebsocketRequest<TradeStatisticsEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    if (symbols.size() == 1) {
      request.name = "24HTradeStatistics for " + symbols;
    } else {
      request.name = "24HTradeStatistics for " + symbols + " ...";
    }
    request.connectionHandler = (connection) -> {
      for (String symbol : symbols) {
        symbol = Channels.tradeStatisticsChannel(symbol);
        connection.send(symbol);
      }
    };
    request.jsonParser = TradeStatisticsEvent.getParser();
    return request;
  }

  WebsocketRequest<TradeStatisticsEvent> request24HTradeStatisticsEvent(
      List<String> symbols,
      SubscriptionListener<TradeStatisticsEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    InputChecker.checker().checkSymbolList(symbols).shouldNotNull(subscriptionListener, "listener");
    WebsocketRequest<TradeStatisticsEvent> request =
        new WebsocketRequest<>(subscriptionListener, errorHandler);
    if (symbols.size() == 1) {
      request.name = "24HTradeStatistics Req for " + symbols;
    } else {
      request.name = "24HTradeStatistics Req for " + symbols + " ...";
    }
    request.connectionHandler = (connection) -> {
      for (String symbol : symbols) {
        String req = Channels.tradeStatisticsChannel(Channels.OP_REQ, symbol);
        connection.send(req);
      }
    };
    request.jsonParser = TradeStatisticsEvent.getReqParser();
    return request;
  }

  WebsocketRequest<AccountListEvent> requestAccountListEvent(
      SubscriptionListener<AccountListEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {

    WebsocketRequest<AccountListEvent> request = new WebsocketRequest<AccountListEvent>(subscriptionListener, errorHandler);
    request.authHandler = (connection) -> {
      connection.send(Channels.requestAccountListChannel());
    };
    request.jsonParser = AccountListEvent.getParser();

    return request;
  }


  WebsocketRequest<OrderListEvent> requestOrderListEvent(
      OrdersRequest ordersRequest,
      SubscriptionListener<OrderListEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    WebsocketRequest<OrderListEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);
    request.name = "Order List Req for " + ordersRequest.getSymbol();

    request.authHandler = (connection) -> {

      Account account = AccountsInfoMap.getUser(apiKey).getAccount(AccountType.SPOT);
      if (account == null) {
        throw new HuobiApiException(HuobiApiException.INPUT_ERROR, "[Input] No such account");
      }

      String startDateString = ordersRequest.getStartDate() == null
          ? null : DateFormatUtils.format(ordersRequest.getStartDate(), "yyyy-MM-dd");
      String endDateString = ordersRequest.getEndDate() == null
          ? null : DateFormatUtils.format(ordersRequest.getEndDate(), "yyyy-MM-dd");
      JSONObject requestTopic = new JSONObject();
      requestTopic.put("op", Channels.OP_REQ);
      requestTopic.put("topic", "orders.list");
      requestTopic.put("account-id", account.getId());
      requestTopic.put("symbol", ordersRequest.getSymbol());
      requestTopic.put("types", ordersRequest.getTypesString());
      requestTopic.put("states", ordersRequest.getStatesString());
      requestTopic.put("start-date", startDateString);
      requestTopic.put("end-date", endDateString);
      requestTopic.put("from", ordersRequest.getStartId() != null ? ordersRequest.getStartId()+"" : null);
      requestTopic.put("direct", ordersRequest.getDirect() != null ? ordersRequest.getDirect().toString() : null);
      requestTopic.put("size", ordersRequest.getSize() != null ? ordersRequest.getSize()+"" : null);

      connection.send(requestTopic.toJSONString());
    };
    request.jsonParser = OrderListEvent.getParser(apiKey);
    return request;
  }

  WebsocketRequest<OrderListEvent> requestOrderDetailEvent(
      Long orderId,
      SubscriptionListener<OrderListEvent> subscriptionListener,
      SubscriptionErrorHandler errorHandler) {
    WebsocketRequest<OrderListEvent> request = new WebsocketRequest<>(subscriptionListener, errorHandler);

    InputChecker.checker().shouldNotNull(orderId,"orderId");
    request.name = "Order Detail Req for " + orderId;

    request.authHandler = (connection) -> {

      JSONObject requestTopic = new JSONObject();
      requestTopic.put("op", Channels.OP_REQ);
      requestTopic.put("topic", "orders.detail");
      requestTopic.put("order-id", orderId.toString());

      System.out.println("[send]"+ requestTopic.toJSONString());
      connection.send(requestTopic.toJSONString());
    };
    request.jsonParser = OrderListEvent.getSingleParser(apiKey);
    return request;
  }
}
