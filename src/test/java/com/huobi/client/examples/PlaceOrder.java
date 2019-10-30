package com.huobi.client.examples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.model.FeeRate;
import com.huobi.client.model.MatchResult;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.enums.StopOrderOperator;
import com.huobi.client.model.request.HistoricalOrdersRequest;
import com.huobi.client.model.request.MatchResultRequest;
import com.huobi.client.model.request.NewOrderRequest;
import com.huobi.client.model.request.OpenOrderRequest;
import com.huobi.client.model.request.OrdersHistoryRequest;
import com.huobi.client.model.request.OrdersRequest;

public class PlaceOrder {

  public static void main(String[] args) {

    String symbol = "htusdt";

    SyncRequestClient syncRequestClient = SyncRequestClient.create(
        Constants.API_KEY, Constants.SECRET_KEY);

    // General Place Order
    String clientOrderId = "T" + System.nanoTime();
    NewOrderRequest newOrderRequest = new NewOrderRequest(
        symbol,
        AccountType.SPOT,
        OrderType.SELL_LIMIT,
        BigDecimal.valueOf(1.0),
        BigDecimal.valueOf(4.04),
        clientOrderId,
        null,
        null
    );

    long o1 = syncRequestClient.createOrder(newOrderRequest);
    System.out.println("--- The new order created ---");
    System.out.println("--- " + o1 + " ---");
    System.out.println("--- " + clientOrderId + " ---");

    // Create Buy Limit Order
    NewOrderRequest requestLimitBuy = NewOrderRequest.spotBuyLimit(symbol, new BigDecimal("3.98"), new BigDecimal("1"));
    long orderBuyId = syncRequestClient.createOrder(requestLimitBuy);
    System.out.println("--- The new order created ---");
    System.out.println("--- " + orderBuyId + " ---");

    // Create Sell Limit Order
    NewOrderRequest requestLimitSell = NewOrderRequest.spotSellLimit(symbol, new BigDecimal("4.04"), new BigDecimal("1"));
    long orderSellId = syncRequestClient.createOrder(requestLimitSell);
    System.out.println("--- The new order created ---");
    System.out.println("--- " + orderSellId + " ---");

    // Create Buy Market Order
    NewOrderRequest requestMarketBuy = NewOrderRequest.spotBuyMarket(symbol, new BigDecimal("5"));
    long marketBuyId = syncRequestClient.createOrder(requestMarketBuy);
    System.out.println("--- The new order created ---");
    System.out.println("--- " + marketBuyId + " ---");

    // Create Sell Market Order
    NewOrderRequest requestMarketSell = NewOrderRequest.spotSellMarket(symbol, new BigDecimal("1"));
    long marketSellId = syncRequestClient.createOrder(requestMarketSell);
    System.out.println("--- The new order created ---");
    System.out.println("--- " + marketSellId + " ---");

    NewOrderRequest buyStopLimit = NewOrderRequest.spotBuyStopOrder(symbol,
        new BigDecimal("4.6"),
        new BigDecimal("4.6"),
        new BigDecimal("1"),
        StopOrderOperator.GTE);

    long buyStopLimitId = syncRequestClient.createOrder(buyStopLimit);
    System.out.println("--- The new stop limit order created ---");
    System.out.println("--- " + buyStopLimitId + " ---");

    NewOrderRequest sellStopLimit = NewOrderRequest.spotSellStopOrder(symbol,
        new BigDecimal("3.5"),
        new BigDecimal("3.4"),
        new BigDecimal("1"),
        StopOrderOperator.LTE);
    long sellStopLimitId = syncRequestClient.createOrder(sellStopLimit);
    System.out.println("--- The new stop limit order created ---");
    System.out.println("--- " + sellStopLimitId + " ---");

    String marginSymbol = "xrpusdt";
    NewOrderRequest marginOrderRequest = new NewOrderRequest(
        marginSymbol,
        AccountType.MARGIN,
        OrderType.BUY_LIMIT,
        BigDecimal.valueOf(4),
        BigDecimal.valueOf(0.28),
        null,
        null,
        null
    );

    Long marginOrderId = syncRequestClient.createOrder(marginOrderRequest);
    System.out.println("----------Margin order id:" + marginOrderId + "---------------");

    syncRequestClient.cancelOrder(marginSymbol,marginOrderId);
    System.out.println("--------------Margin order cancel finish----------------");

    String superMarginSymbol = "xrpusdt";
    NewOrderRequest superMarginOrderRequest = new NewOrderRequest(
        superMarginSymbol,
        AccountType.SUPER_MARGIN,
        OrderType.BUY_LIMIT,
        BigDecimal.valueOf(4),
        BigDecimal.valueOf(0.28),
        null,
        null,
        null
    );

    Long superMarginOrderId = syncRequestClient.createOrder(superMarginOrderRequest);
    System.out.println("----------Super Margin order id:" + superMarginOrderId + "---------------");

    syncRequestClient.cancelOrder(superMarginSymbol,superMarginOrderId);
    System.out.println("--------------Super Margin order cancel finish----------------");

    // Get the order detail use order id
    Order orderInfo = syncRequestClient.getOrder(symbol, o1);
    System.out.println("GetById Id: " + orderInfo.getOrderId());
    System.out.println("GetById Type: " + orderInfo.getType());
    System.out.println("GetById Status: " + orderInfo.getState());
    System.out.println("GetById Amount: " + orderInfo.getAmount());
    System.out.println("GetById Price: " + orderInfo.getPrice());

    // Get the order detail use client order id
    Order orderInfo1 = syncRequestClient.getOrderByClientOrderId(symbol, clientOrderId);
    System.out.println("GetByClientOrderId Id: " + orderInfo1.getOrderId());
    System.out.println("GetByClientOrderId Type: " + orderInfo1.getType());
    System.out.println("GetByClientOrderId Status: " + orderInfo1.getState());
    System.out.println("GetByClientOrderId Amount: " + orderInfo1.getAmount());
    System.out.println("GetByClientOrderId Price: " + orderInfo1.getPrice());

    // Get the order match result use order id
    List<MatchResult> matchResults = syncRequestClient.getMatchResults(symbol, marketBuyId);
    matchResults.forEach(matchResult -> {
      System.out.println("Order Match Result : " + matchResult.toString());
    });

    matchResults = syncRequestClient.getMatchResults(new MatchResultRequest(symbol));
    matchResults.forEach(matchResult -> {
      System.out.println("Match Result list : " + matchResult.toString());
    });

    // Cancel order use order id
    syncRequestClient.cancelOrder(symbol, o1);

    // Cancel order use client order id
    syncRequestClient.cancelOrderByClientOrderId(symbol, clientOrderId);

    List<Long> cancelOrderList = new ArrayList<>();
    // Get current open orders
    List<Order> openOrderList = syncRequestClient.getOpenOrders(new OpenOrderRequest(symbol, AccountType.SPOT));

    openOrderList.forEach(order -> {
      System.out.println("Open Order :: " + order.toString());
      cancelOrderList.add(order.getOrderId());
    });

    // Batch cancel orders
    if (cancelOrderList.size() > 0) {
      syncRequestClient.cancelOrders(symbol, cancelOrderList);
    }

    // Get all the orders
    List<Order> orderList = syncRequestClient.getHistoricalOrders(new HistoricalOrdersRequest(symbol, OrderState.FILLED));
    orderList.forEach(order -> {
      System.out.println("Orders :: " + order.toString());
    });

    System.out.println("=================");

    List<OrderState> stateList = new ArrayList<>();
    stateList.add(OrderState.CANCELED);
    stateList.add(OrderState.FILLED);

    List<OrderType> typeList = new ArrayList<>();
    typeList.add(OrderType.BUY_LIMIT);
    typeList.add(OrderType.SELL_LIMIT);
    typeList.add(OrderType.BUY_MARKET);
    typeList.add(OrderType.SELL_MARKET);

    // Get all the orders
    List<Order> orderList1 = syncRequestClient.getOrders(new OrdersRequest(symbol, stateList, typeList, null, null, null, null, null));
    orderList1.forEach(order -> {
      System.out.println("Orders :: " + order.toString());
    });

    // Get order history
    List<Order> orderHistoryList = syncRequestClient.getOrderHistory(new OrdersHistoryRequest(symbol));
    orderHistoryList.forEach(order -> {
      System.out.println("History Order :: " + order.toString());
    });

    // Get fee rate about symbol
    List<FeeRate> feeRateList = syncRequestClient.getFeeRate("htusdt,ethusdt");
    feeRateList.forEach(feeRate -> {
      System.out.println("FeeRate : " + feeRate.toString());
    });
  }
}
