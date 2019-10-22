package com.huobi.client;

import java.util.List;

import com.huobi.client.req.trade.BatchCancelOpenOrdersRequest;
import com.huobi.client.req.trade.CreateOrderRequest;
import com.huobi.client.req.trade.FeeRateRequest;
import com.huobi.client.req.trade.MatchResultRequest;
import com.huobi.client.req.trade.OpenOrdersRequest;
import com.huobi.client.req.trade.OrderHistoryRequest;
import com.huobi.client.req.trade.OrdersRequest;
import com.huobi.client.req.trade.ReqOrderListRequest;
import com.huobi.client.req.trade.SubOrderUpdateRequest;
import com.huobi.model.trade.BatchCancelOpenOrdersResult;
import com.huobi.model.trade.BatchCancelOrderResult;
import com.huobi.model.trade.FeeRate;
import com.huobi.model.trade.MatchResult;
import com.huobi.model.trade.Order;
import com.huobi.model.trade.OrderDetailReq;
import com.huobi.model.trade.OrderListReq;
import com.huobi.model.trade.OrderUpdateEvent;
import com.huobi.utils.ResponseCallback;

public interface TradeClient {

  Long createOrder(CreateOrderRequest request);

  Long cancelOrder(Long orderId);

  Integer cancelOrder(String clientOrderId);

  BatchCancelOpenOrdersResult batchCancelOpenOrders(BatchCancelOpenOrdersRequest request);

  BatchCancelOrderResult batchCancelOrder(List<Long> ids);

  List<Order> getOpenOrders(OpenOrdersRequest request);

  Order getOrder(Long orderId);

  Order getOrder(String clientOrderId);

  List<Order> getOrders(OrdersRequest request);

  List<Order> getOrdersHistory(OrderHistoryRequest request);

  List<MatchResult> getMatchResult(Long orderId);

  List<MatchResult> getMatchResults(MatchResultRequest request);

  List<FeeRate> getFeeRate(FeeRateRequest request);


  void subOrderUpdate(SubOrderUpdateRequest request, ResponseCallback<OrderUpdateEvent> callback);

  void reqOrderList(ReqOrderListRequest request, ResponseCallback<OrderListReq> callback);

  void reqOrderDetail(Long orderId, ResponseCallback<OrderDetailReq> callback);

}
