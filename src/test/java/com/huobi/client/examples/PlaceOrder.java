package com.huobi.client.examples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.model.BatchCancelResultV1;
import com.huobi.client.model.CancelResult;
import com.huobi.client.model.CreateOrderResult;
import com.huobi.client.model.FeeRate;
import com.huobi.client.model.MatchResult;
import com.huobi.client.model.Order;
import com.huobi.client.model.TransactFeeRate;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.enums.StopOrderOperator;
import com.huobi.client.model.request.BatchCancelRequest;
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
        BigDecimal.valueOf(0.5),
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
    NewOrderRequest requestLimitBuy = NewOrderRequest.spotBuyLimit(symbol, new BigDecimal("1"), new BigDecimal("1"));
    long orderBuyId = syncRequestClient.createOrder(requestLimitBuy);
    System.out.println("--- The new order created ---");
    System.out.println("--- " + orderBuyId + " ---");

    // Create Sell Limit Order
    NewOrderRequest requestLimitSell = NewOrderRequest.spotSellLimit(symbol, new BigDecimal("4.04"), new BigDecimal("0.5"));
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

    // Create Buy Stop Limit Order
    NewOrderRequest buyStopLimit = NewOrderRequest.spotBuyStopOrder(symbol,
        new BigDecimal("4.6"),
        new BigDecimal("4.6"),
        new BigDecimal("1"),
        StopOrderOperator.GTE);

    long buyStopLimitId = syncRequestClient.createOrder(buyStopLimit);
    System.out.println("--- The new stop limit order created ---");
    System.out.println("--- " + buyStopLimitId + " ---");

    // Create Sell Stop Limit Order
    NewOrderRequest sellStopLimit = NewOrderRequest.spotSellStopOrder(symbol,
        new BigDecimal("2"),
        new BigDecimal("2"),
        new BigDecimal("1"),
        StopOrderOperator.LTE);
    long sellStopLimitId = syncRequestClient.createOrder(sellStopLimit);
    System.out.println("--- The new stop limit order created ---");
    System.out.println("--- " + sellStopLimitId + " ---");

    // Batch Create Order
    List<NewOrderRequest> batchOrderRequestList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      String cid = "T_" + System.currentTimeMillis() + "_" + i;
      batchOrderRequestList.add(NewOrderRequest.spotBuyLimit(cid, symbol, new BigDecimal("1"), new BigDecimal("1")));
    }

    List<CreateOrderResult> batchOrderResultList = syncRequestClient.batchCreateOrder(batchOrderRequestList);

    // Print Batch Create Order Result
    List<Long> orderIdList = new ArrayList<>(10);
    List<String> clientOrderIdList = new ArrayList<>(10);
    int i = 0;
    for (CreateOrderResult orderResult : batchOrderResultList) {
      System.out.println(
          " order-id:" + orderResult.getOrderId()
              + "  client-order-id:" + orderResult.getClientOrderId()
              + "  err-code:" + orderResult.getErrCode()
              + "  err-msg:" + orderResult.getErrMsg());
      if (i % 2 == 0) {
        orderIdList.add(orderResult.getOrderId());
      } else {
        clientOrderIdList.add(orderResult.getClientOrderId());
        System.out.println("oid:"+orderResult.getOrderId());
      }
      i++;
    }

    // Batch cancel order use order-ids
    BatchCancelRequest orderIdBatchRequest = new BatchCancelRequest();
    orderIdBatchRequest.setSymbol(symbol);
    orderIdBatchRequest.setOrderIds(orderIdList);
    BatchCancelResultV1 batchCancelResultV1 = syncRequestClient.cancelOrders(orderIdBatchRequest);

    printBatchResult(batchCancelResultV1);

    // Batch cancel order use client-order-ids
    BatchCancelRequest clientOrderIdBatchRequest = new BatchCancelRequest();
    clientOrderIdBatchRequest.setSymbol(symbol);
    clientOrderIdBatchRequest.setClientOrderIds(clientOrderIdList);
    BatchCancelResultV1 clientBatchCancelResultV1 = syncRequestClient.cancelOrders(clientOrderIdBatchRequest);

    printBatchResult(clientBatchCancelResultV1);

    // Get the order detail use order id
    Order orderInfo = syncRequestClient.getOrder(symbol, o1);
    System.out.println("GetById Id: " + orderInfo.getOrderId());
    System.out.println("GetById ClientOrderId: " + orderInfo.getClientOrderId());
    System.out.println("GetById Type: " + orderInfo.getType());
    System.out.println("GetById Status: " + orderInfo.getState());
    System.out.println("GetById Amount: " + orderInfo.getAmount());
    System.out.println("GetById Price: " + orderInfo.getPrice());

    // Get the order detail use client order id
    Order orderInfo1 = syncRequestClient.getOrderByClientOrderId(symbol, clientOrderId);
    System.out.println("GetByClientOrderId Id: " + orderInfo1.getOrderId());
    System.out.println("GetByClientOrderId ClientOrderId: " + orderInfo.getClientOrderId());
    System.out.println("GetByClientOrderId Type: " + orderInfo1.getType());
    System.out.println("GetByClientOrderId Status: " + orderInfo1.getState());
    System.out.println("GetByClientOrderId Amount: " + orderInfo1.getAmount());
    System.out.println("GetByClientOrderId Price: " + orderInfo1.getPrice());

    // Get the order match result use order id
    List<MatchResult> matchResults = syncRequestClient.getMatchResults(symbol, marketBuyId);
    matchResults.forEach(matchResult -> {
      System.out.println("Order Match Result : " + JSON.toJSONString(matchResult));
    });

    matchResults = syncRequestClient.getMatchResults(new MatchResultRequest(symbol));
    matchResults.forEach(matchResult -> {
      System.out.println("Match Result list : " + JSON.toJSONString(matchResult));
    });

    // Cancel order use order id
    syncRequestClient.cancelOrder(symbol, o1);

    // Cancel order use client order id
    syncRequestClient.cancelOrderByClientOrderId(symbol, clientOrderId);

    List<Long> cancelOrderList = new ArrayList<>();
    // Get current open orders
    List<Order> openOrderList = syncRequestClient.getOpenOrders(new OpenOrderRequest(symbol, AccountType.SPOT));

    openOrderList.forEach(order -> {
      System.out.println("Open Order :: " + JSON.toJSONString(order));
      cancelOrderList.add(order.getOrderId());
    });

    // Batch cancel orders
    if (cancelOrderList.size() > 0) {
      syncRequestClient.cancelOrders(symbol, cancelOrderList);
    }

    // Get all the orders
    List<Order> orderList = syncRequestClient.getHistoricalOrders(new HistoricalOrdersRequest(symbol, OrderState.FILLED));
    orderList.forEach(order -> {
      System.out.println("Orders :: " + JSON.toJSONString(order));
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
    List<Order> orderList1 = syncRequestClient.getOrders(new OrdersRequest("adausdt", stateList, typeList, null, null, null, null, null));
    orderList1.forEach(order -> {
      System.out.println("Orders :: " + JSON.toJSONString(order));
    });

    // Get order history
    List<Order> orderHistoryList = syncRequestClient.getOrderHistory(new OrdersHistoryRequest(symbol));
    orderHistoryList.forEach(order -> {
      System.out.println("History Order :: " + JSON.toJSONString(order));
    });

    // Get fee rate about symbol
    List<FeeRate> feeRateList = syncRequestClient.getFeeRate("htusdt,ethusdt");
    feeRateList.forEach(feeRate -> {
      System.out.println("FeeRate : " + JSON.toJSONString(feeRate));
    });

    List<TransactFeeRate> transactFeeRateList = syncRequestClient.getTransactFeeRate("btcusdt,adausdt");
    transactFeeRateList.forEach(rate -> {
      System.out.println("transact fee rate:" + JSON.toJSONString(rate));
    });
  }

  public static void printBatchResult(BatchCancelResultV1 batchCancelResultV1) {
    System.out.println("-----------------------------------");
    for (String id : batchCancelResultV1.getSuccessList()) {
      System.out.println("success order:" + id);
    }

    for (CancelResult cancelResult : batchCancelResultV1.getFailedList()) {
      System.out.println(
          " order-id:" + cancelResult.getOrderId()
              + "  client-order-id:" + cancelResult.getClientOrderId()
              + "  err-code:" + cancelResult.getErrCode()
              + "  err-msg:" + cancelResult.getErrMsg()
              + "  order-state:" + cancelResult.getOrderState());
    }
  }
}
