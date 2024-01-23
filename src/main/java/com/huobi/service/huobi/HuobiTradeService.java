package com.huobi.service.huobi;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.TradeClient;
import com.huobi.client.req.trade.*;
import com.huobi.constant.Options;
import com.huobi.constant.WebSocketConstants;
import com.huobi.constant.enums.OrderTypeEnum;
import com.huobi.exception.SDKException;
import com.huobi.model.trade.*;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.connection.HuobiWebSocketConnection;
import com.huobi.service.huobi.parser.trade.*;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;
import com.huobi.utils.ResponseCallback;
import com.huobi.utils.SymbolUtils;

public class HuobiTradeService implements TradeClient {


  public static final String CREATE_ORDER_PATH = "/v1/order/orders/place";//下单
  public static final String CANCEL_ORDER_PATH = "/v1/order/orders/{order-id}/submitcancel";//撤销订单
  public static final String CANCEL_ORDER_BY_CLIENT_ORDER_ID_PATH = "/v1/order/orders/submitCancelClientOrder";//撤销订单（基于clientorderID）
  public static final String BATCH_CANCEL_OPEN_ORDERS_PATH = "/v1/order/orders/batchCancelOpenOrders";//批量撤销所有订单
  public static final String BATCH_CANCEL_ORDERS_PATH = "/v1/order/orders/batchcancel";//批量撤销指定订单
  public static final String GET_OPEN_ORDERS_PATH = "/v1/order/openOrders";//查询当前未成交订单
  public static final String GET_ORDER_PATH = "/v1/order/orders/{order-id}";//查询订单详情
  public static final String GET_ORDERS_PATH = "/v1/order/orders";//搜索历史订单
  public static final String GET_ORDERS_HISTORY_PATH = "/v1/order/history";//搜索最近48小时内历史订单
  public static final String GET_ORDER_BY_CLIENT_ORDER_ID_PATH = "/v1/order/orders/getClientOrder";//查询订单详情（基于clientorderID）
  public static final String GET_SINGLE_ORDER_MATCH_RESULT_PATH = "/v1/order/orders/{order-id}/matchresults";//成交明细
  public static final String GET_MATCH_RESULT_PATH = "/v1/order/matchresults";//当前和历史成交
  public static final String GET_FEE_RATE_PATH = "/v2/reference/transact-fee-rate";//获取用户当前手续费率
  public static final String BATCH_ORDERS_PATH = "/v1/order/batch-orders";//批量下单
  public static final String MARGIN_ORDER_PATH = "/v1/order/auto/place";//杠杆下单




  private Options options;

  private HuobiRestConnection restConnection;

  private HuobiAccountService huobiAccountService;

  public HuobiTradeService(Options options) {
    this.options = options;
    this.restConnection = new HuobiRestConnection(options);
    this.huobiAccountService = new HuobiAccountService(options);
  }

  @Override
  public Long createOrder(CreateOrderRequest request) {

    InputChecker.checker().checkSymbol(request.getSymbol())
        .shouldNotNull(request.getAccountId(), "Account-Id")
        .shouldNotNull(request.getAmount(), "Amount")
        .shouldNotNull(request.getType(), "Type")
        .shouldNotNull(request.getOrderSource(), "order source");

    if (request.getType() == OrderTypeEnum.SELL_LIMIT
        || request.getType() == OrderTypeEnum.BUY_LIMIT
        || request.getType() == OrderTypeEnum.BUY_LIMIT_MAKER
        || request.getType() == OrderTypeEnum.SELL_LIMIT_MAKER) {
      InputChecker.checker()
          .shouldNotNull(request.getPrice(), "Price");
    }
    if (request.getType() == OrderTypeEnum.SELL_MARKET
        || request.getType() == OrderTypeEnum.BUY_MARKET) {
      InputChecker.checker()
          .shouldNull(request.getPrice(), "Price");
    }
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("account-id", request.getAccountId())
        .putToPost("amount", request.getAmount())
        .putToPost("price", request.getPrice())
        .putToPost("symbol", request.getSymbol())
        .putToPost("type", request.getType().getCode())
        .putToPost("client-order-id", request.getClientOrderId())
        .putToPost("source", request.getOrderSource().getCode())
        .putToPost("stop-price", request.getStopPrice())
        .putToPost("operator", request.getOperator() == null ? null : request.getOperator().getOperator())
        .putToPost("self-match-prevent", request.getSelfMatchPrevent());

    JSONObject jsonObject = restConnection.executePostWithSignature(CREATE_ORDER_PATH, builder);
    return jsonObject.getLong("data");
  }

  @Override
  public Long cancelOrder(CancelOrderRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getOrderId(), "order-id");

    String path = CANCEL_ORDER_PATH.replace("{order-id}", request.getOrderId() + "");
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    builder.putToPost("symbol", request.getSymbol());
    JSONObject jsonObject = restConnection.executePostWithSignature(path, builder);
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
        .shouldNotNull(request.getAccountId(), "account-id");
    if (request.getSize() != null) {
      InputChecker.checker()
          .checkRange(request.getSize(), 1, 100, "size");
    }

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("account-id", request.getAccountId())
        .putToPost("symbol", request.getSymbol())
        .putToPost("side", request.getSide() == null ? null : request.getSide().getCode())
        .putToPost("size", request.getSize())
        .putToPost("types", request.getTypes());

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
        .shouldNotNull(request.getAccountId(), "account-id")
        .checkRange(request.getSize(), 1, 500, "size");


    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("account-id", request.getAccountId())
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("side", request.getSide() == null ? null : request.getSide().getCode())
        .putToUrl("size", request.getSize())
        .putToUrl("direct", request.getDirect() == null ? null : request.getDirect().getCode())
        .putToUrl("from", request.getFrom())
        .putToUrl("types", request.getTypes());

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

  @Override
  public List<BatchOrdersResult> batchOrders(List<BatchOrdersRequest> list) {
    for (BatchOrdersRequest request : list) {
      InputChecker.checker()
              .shouldNotNull(request.getAccountId(), "account-id")
              .shouldNotNull(request.getSymbol(), "symbol")
              .shouldNotNull(request.getType(), "type")
              .shouldNotNull(request.getAmount(), "amount");
    }
    List<Map<String, Object>> postList = new ArrayList<>();
    for (BatchOrdersRequest request : list) {
      HashMap<String, Object> postDataMap = new HashMap();
      postDataMap.put("account-id", request.getAccountId());
      postDataMap.put("symbol", request.getSymbol());
      postDataMap.put("type", request.getType());
      postDataMap.put("amount", request.getAmount());
      postDataMap.put("price", request.getPrice());
      postDataMap.put("source", request.getSource());
      postDataMap.put("client-order-id", request.getClientOrderId());
      postDataMap.put("self-match-prevent", request.getSelfMatchPrevent());
      postDataMap.put("stop-price", request.getStopPrice());
      postDataMap.put("operator", request.getOperator());
      postList.add(postDataMap);
    }
    UrlParamsBuilder builder = UrlParamsBuilder.build()
            .putToPost("data", list);
    JSONObject jsonObject = restConnection.executePostWithSignature(BATCH_ORDERS_PATH, builder);
    JSONArray array = jsonObject.getJSONArray("data");
    return new BatchOrdersResultParser().parseArray(array);
  }

  @Override
  public OrderResp marginOrder(MarginOrderRequest request) {
    InputChecker.checker()
            .shouldNotNull(request.getSymbol(), "symbol")
            .shouldNotNull(request.getAccountId(), "account-id")
            .shouldNotNull(request.getType(), "type")
            .shouldNotNull(request.getTradePurpose(), "trade-purpose")
            .shouldNotNull(request.getSource(), "source");
    UrlParamsBuilder builder = UrlParamsBuilder.build()
            .putToPost("symbol", request.getSymbol())
            .putToPost("account-id", request.getAccountId())
            .putToPost("amount", request.getAmount())
            .putToPost("market-amount", request.getMarketAmount())
            .putToPost("borrow-amount", request.getBorrowAmount())
            .putToPost("type", request.getType())
            .putToPost("trade-purpose", request.getTradePurpose())
            .putToPost("price", request.getPrice())
            .putToPost("stop-price", request.getStopPrice())
            .putToPost("operator", request.getOperator())
            .putToPost("source", request.getSource());
    JSONObject jsonObject = restConnection.executePostWithSignature(MARGIN_ORDER_PATH, builder);
    JSONObject data = jsonObject.getJSONObject("data");
    return new OrderRespParser().parse(data);
  }

}
