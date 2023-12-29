package com.huobi.client;

import java.util.List;

import com.huobi.client.req.trade.*;
import com.huobi.constant.Options;
import com.huobi.constant.enums.ExchangeEnum;
import com.huobi.exception.SDKException;
import com.huobi.model.trade.BatchCancelOpenOrdersResult;
import com.huobi.model.trade.BatchCancelOrderResult;
import com.huobi.model.trade.FeeRate;
import com.huobi.model.trade.MatchResult;
import com.huobi.model.trade.Order;
import com.huobi.model.trade.OrderDetailReq;
import com.huobi.model.trade.OrderListReq;
import com.huobi.model.trade.OrderUpdateEvent;
import com.huobi.model.trade.OrderUpdateV2Event;
import com.huobi.model.trade.TradeClearingEvent;
import com.huobi.service.huobi.HuobiTradeService;
import com.huobi.utils.ResponseCallback;

public interface TradeClient {

  Long createOrder(CreateOrderRequest request);

  Long cancelOrder(CancelOrderRequest request);

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

  void subOrderUpdateV2(SubOrderUpdateV2Request request, ResponseCallback<OrderUpdateV2Event> callback);

  void subTradeClearing(SubTradeClearingRequest request, ResponseCallback<TradeClearingEvent> callback);

  static TradeClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiTradeService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }

}
