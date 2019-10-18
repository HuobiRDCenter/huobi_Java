package com.huobi.service.huobi;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.TradeClient;
import com.huobi.client.req.trade.BatchCancelOpenOrdersRequest;
import com.huobi.client.req.trade.FeeRateRequest;
import com.huobi.client.req.trade.MatchResultRequest;
import com.huobi.client.req.trade.OrderHistoryRequest;
import com.huobi.client.req.trade.OrdersRequest;
import com.huobi.constant.enums.AccountTypeEnum;
import com.huobi.constant.enums.OrderSideEnum;
import com.huobi.constant.enums.OrderStateEnum;
import com.huobi.constant.enums.OrderTypeEnum;
import com.huobi.constant.enums.QueryDirectionEnum;
import com.huobi.exception.SDKException;
import com.huobi.model.account.Account;
import com.huobi.client.req.trade.OpenOrdersRequest;
import com.huobi.constant.Constants;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.Options;
import com.huobi.model.trade.BatchCancelOpenOrdersResult;
import com.huobi.model.trade.BatchCancelOrderResult;
import com.huobi.model.trade.FeeRate;
import com.huobi.model.trade.MatchResult;
import com.huobi.model.trade.Order;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.parser.trade.BatchCancelOpenOrdersResultParser;
import com.huobi.service.huobi.parser.trade.BatchCancelOrderResultParser;
import com.huobi.service.huobi.parser.trade.FeeRateParser;
import com.huobi.service.huobi.parser.trade.MatchResultParser;
import com.huobi.service.huobi.parser.trade.OrderParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;

public class TradeService implements TradeClient {


  public static final String CANCEL_ORDER_PATH = "/v1/order/orders/{order-id}/submitcancel";
  public static final String CANCEL_ORDER_BY_CLIENT_ORDER_ID_PATH = "/v1/order/orders/submitCancelClientOrder";
  public static final String BATCH_CANCEL_OPEN_ORDERS_PATH = "/v1/order/orders/batchCancelOpenOrders";
  public static final String BATCH_CANCEL_ORDERS_PATH = "/v1/order/orders/batchcancel";
  public static final String GET_OPEN_ORDERS_PATH = "/v1/order/openOrders";
  public static final String GET_ORDER_PATH = "/v1/order/orders/{order-id}";
  public static final String GET_ORDERS_PATH = "/v1/order/orders";
  public static final String GET_ORDERS_HISTORY_PATH = "/v1/order/history";
  public static final String GET_ORDER_BY_CLIENT_ORDER_ID_PATH = "/v1/order/orders/getClientOrder";
  public static final String GET_SINGLE_ORDER_MATCH_RESULT_PATH = "/v1/order/orders/{order-id}/matchresults";
  public static final String GET_MATCH_RESULT_PATH = "/v1/order/matchresults";
  public static final String GET_FEE_RATE_PATH = "/v1/fee/fee-rate/get";

  private Options options;

  private HuobiRestConnection restConnection;

  private AccountService accountService;

  public TradeService(Options options) {
    this.options = options;
    this.restConnection = new HuobiRestConnection(options);
    this.accountService = new AccountService(options);
  }

  @Override
  public Long createOrder() {
    return null;
  }

  @Override
  public Long cancelOrder(Long orderId) {

    InputChecker.checker()
        .shouldNotNull(orderId, "order-id");

    String path = CANCEL_ORDER_PATH.replace("{order-id}", orderId + "");

    JSONObject jsonObject = restConnection.executePostWithSignature(path, UrlParamsBuilder.build());
    return jsonObject.getLong("data");
  }

  @Override
  public Integer cancelOrder(String clientOrderId) {

    InputChecker.checker()
        .shouldNotNull(clientOrderId, "client-order-id");

    UrlParamsBuilder builder = UrlParamsBuilder.build();
    builder.putToPost("client-order-id", clientOrderId);

    JSONObject jsonObject = restConnection.executePostWithSignature(CANCEL_ORDER_BY_CLIENT_ORDER_ID_PATH, builder);
    return jsonObject.getInteger("data");
  }

  @Override
  public BatchCancelOpenOrdersResult batchCancelOpenOrders(BatchCancelOpenOrdersRequest request) {

    InputChecker.checker()
        .checkSymbol(request.getSymbol())
        .shouldNotNull(request.getAccountType(), "accountType");
    if (request.getSize() != null) {
      InputChecker.checker()
          .checkRange(request.getSize(), 1, 100, "size");
    }
    Account account = accountService.getAccount(request.getAccountType());
    if (account == null) {
      throw new SDKException(SDKException.EXEC_ERROR, "[Executing] Could not find account:" + request.getAccountType());
    }

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("account-id", account.getId())
        .putToPost("symbol", request.getSymbol())
        .putToPost("side", request.getSide() == null ? null : request.getSide().getCode())
        .putToPost("size", request.getSize());

    JSONObject jsonObject = restConnection.executePostWithSignature(BATCH_CANCEL_OPEN_ORDERS_PATH, builder);
    JSONObject data = jsonObject.getJSONObject("data");
    return new BatchCancelOpenOrdersResultParser().parse(data);
  }

  @Override
  public BatchCancelOrderResult batchCancelOrder(List<Long> ids) {

    InputChecker.checker()
        .shouldNotNull(ids, "order-ids")
        .checkList(ids, 1, 50, "orderIds");

    List<String> stringList = new LinkedList<>();
    for (Object obj : ids) {
      stringList.add(obj.toString());
    }
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("order-ids", stringList);

    JSONObject jsonObject = restConnection.executePostWithSignature(BATCH_CANCEL_ORDERS_PATH, builder);
    JSONObject data = jsonObject.getJSONObject("data");
    return new BatchCancelOrderResultParser().parse(data);
  }

  @Override
  public List<Order> getOpenOrders(OpenOrdersRequest request) {

    InputChecker.checker()
        .checkSymbol(request.getSymbol())
        .shouldNotNull(request.getAccountType(), "accountType")
        .checkRange(request.getSize(), 1, 2000, "size");

    Account account = accountService.getAccount(request.getAccountType());
    if (account == null) {
      throw new SDKException(SDKException.EXEC_ERROR, "[Executing] Could not find account:" + request.getAccountType());
    }

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("account-id", account.getId())
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("side", request.getSide() == null ? null : request.getSide().getCode())
        .putToUrl("size", request.getSize())
        .putToUrl("direct", request.getDirect() == null ? null : request.getDirect().getCode())
        .putToUrl("from", request.getFrom());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_OPEN_ORDERS_PATH, builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new OrderParser().parseArray(data);
  }

  @Override
  public Order getOrder(Long orderId) {

    InputChecker.checker()
        .shouldNotNull(orderId, "order-id");

    String path = GET_ORDER_PATH.replace("{order-id}", orderId + "");

    JSONObject jsonObject = restConnection.executeGetWithSignature(path, UrlParamsBuilder.build());
    JSONObject data = jsonObject.getJSONObject("data");
    return new OrderParser().parse(data);
  }

  @Override
  public Order getOrder(String clientOrderId) {

    InputChecker.checker()
        .shouldNotNull(clientOrderId, "client-order-id");

    UrlParamsBuilder builder = UrlParamsBuilder.build();
    builder.putToUrl("clientOrderId", clientOrderId);

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_ORDER_BY_CLIENT_ORDER_ID_PATH, builder);
    JSONObject data = jsonObject.getJSONObject("data");
    return new OrderParser().parse(data);
  }

  @Override
  public List<Order> getOrders(OrdersRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getSymbol(), "symbol")
        .checkList(request.getStates(), 1, 100, "states");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("types", request.getTypesString())
        .putToUrl("start-date", request.getStartDate(), "yyyy-MM-dd")
        .putToUrl("end-date", request.getEndDate(), "yyyy-MM-dd")
        .putToUrl("from", request.getStartId())
        .putToUrl("states", request.getStatesString())
        .putToUrl("size", request.getSize())
        .putToUrl("direct", request.getDirect() == null ? null : request.getDirect().getCode());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_ORDERS_PATH, builder);
    JSONArray array = jsonObject.getJSONArray("data");
    return new OrderParser().parseArray(array);
  }

  @Override
  public List<Order> getOrdersHistory(OrderHistoryRequest request) {

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("start-time", request.getStartTime())
        .putToUrl("end-time", request.getEndTime())
        .putToUrl("size", request.getSize())
        .putToUrl("direct", request.getDirection() == null ? null : request.getDirection().getCode());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_ORDERS_HISTORY_PATH, builder);
    JSONArray array = jsonObject.getJSONArray("data");
    return new OrderParser().parseArray(array);
  }

  @Override
  public List<MatchResult> getMatchResult(Long orderId) {

    InputChecker.checker()
        .shouldNotNull(orderId, "order-id");

    String path = GET_SINGLE_ORDER_MATCH_RESULT_PATH.replace("{order-id}", orderId + "");

    JSONObject jsonObject = restConnection.executeGetWithSignature(path, UrlParamsBuilder.build());
    JSONArray array = jsonObject.getJSONArray("data");
    return new MatchResultParser().parseArray(array);
  }

  @Override
  public List<MatchResult> getMatchResults(MatchResultRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getSymbol(), "symbol");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("types", request.getTypeString())
        .putToUrl("start-date", request.getStartDate(), "yyyy-MM-dd")
        .putToUrl("end-date", request.getEndDate(), "yyyy-MM-dd")
        .putToUrl("from", request.getFrom())
        .putToUrl("direct", request.getDirection() == null ? null : request.getDirection().getCode())
        .putToUrl("size", request.getSize());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_MATCH_RESULT_PATH, builder);
    JSONArray array = jsonObject.getJSONArray("data");
    return new MatchResultParser().parseArray(array);
  }

  @Override
  public List<FeeRate> getFeeRate(FeeRateRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getSymbols(), "symbol");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbols", request.getSymbols());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_FEE_RATE_PATH, builder);
    JSONArray array = jsonObject.getJSONArray("data");
    return new FeeRateParser().parseArray(array);
  }


  public static void main(String[] args) {

    String symbol = "htusdt";

    TradeService tradeService = new TradeService(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY)
        .build());

//    Order clientOrder = tradeService.getOrder("T2170137561018829");
//    System.out.println(clientOrder.toString());

//    int cancelResult = tradeService.cancelOrder("T2170137561018829");
//    System.out.println(cancelResult);

//    Order order = tradeService.getOrder(51210074624L);
//    System.out.println(order);

//    List<Order> orderList = tradeService.getOpenOrders(OpenOrdersRequest.builder()
//        .accountType(AccountTypeEnum.SPOT)
//        .symbol(symbol)
//        .build());
//
//    List<Long> openOrderList = new ArrayList<>();
//    orderList.forEach(order -> {
//      System.out.println(order.toString());
//      openOrderList.add(order.getId());
//      Long res = tradeService.cancelOrder(order.getId());
//      System.out.println("--------cancel order res:"+res+"-----------");
//    });
//
//    BatchCancelOrderResult batchCancelOrderResult = tradeService.batchCancelOrder(openOrderList);
//    System.out.println(batchCancelOrderResult);

//    BatchCancelOpenOrdersResult result = tradeService.batchCancelOpenOrders(BatchCancelOpenOrdersRequest.builder()
//        .accountType(AccountTypeEnum.SPOT)
//        .symbol(symbol)
//        .build());

//    System.out.println(result.toString());

    List<OrderStateEnum> stateList = new ArrayList<>();
    stateList.add(OrderStateEnum.CANCELED);
    stateList.add(OrderStateEnum.FILLED);
//
    List<OrderTypeEnum> typeList = new ArrayList<>();
    typeList.add(OrderTypeEnum.BUY_LIMIT);
    typeList.add(OrderTypeEnum.SELL_LIMIT);
    typeList.add(OrderTypeEnum.BUY_MARKET);
    typeList.add(OrderTypeEnum.SELL_MARKET);

//    List<Order> ordersList = tradeService.getOrders(OrdersRequest.builder()
//        .symbol(symbol)
//        .states(stateList)
//        .build());
//
//    ordersList.forEach(order -> {
//      System.out.println(new Date(order.getCreatedAt()) + ":" + order.toString());
//    });

//    52035072556
//    52034866781
//    List<Order> historyOrderList = tradeService.getOrdersHistory(OrderHistoryRequest.builder()
//        .symbol(symbol)
//        .startTime(1565107200000L)
//        .endTime()
//
//        .direction(QueryDirectionEnum.PREV)
//        .build());
//    historyOrderList.forEach(order -> {
//      System.out.println("History Order : "+new Date(order.getCreatedAt())+":"+order.toString());
//    });

//    List<MatchResult> matchResultList = tradeService.getMatchResult(52035072556L);
//    matchResultList.forEach(matchResult -> {
//      System.out.println(matchResult.toString());
//    });

//    List<MatchResult> matchResultList1 = tradeService.getMatchResults(MatchResultRequest.builder()
//        .symbol(symbol)
//        .build());
//
//    matchResultList1.forEach(matchResult -> {
//      System.out.println(new Date(matchResult.getCreatedAt())+ " All : "+matchResult.toString());
//    });

//    String symbols = symbol+",eosusdt";
//    List<FeeRate> feeRateList = tradeService.getFeeRate(FeeRateRequest.builder().symbols(symbols).build());
//    feeRateList.forEach(feeRate -> {
//      System.out.println(feeRate.toString());
//    });
  }
}
