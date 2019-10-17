package com.huobi.client;

public interface TradeClient {

  Long createOrder();

  Long cancelOrder(Long orderId);

  Integer cancelOrder(String clientOrderId);

  Integer batchCancelOpenOrder();

  void batchCancelOrder();

  void getOpenOrders();

  void getOrder(Long orderId);

  void getOrder(String clientOrderId);

  void getOrders();

  void getOrdersHistory();

  void getMatchResult(Long orderId);

  void getMatchResults();

  void getFeeRate();


}
