package com.huobi.service.huobi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;

import com.huobi.client.TradeClient;
import com.huobi.client.req.trade.BatchCancelOpenOrdersRequest;
import com.huobi.client.req.trade.CreateOrderRequest;
import com.huobi.client.req.trade.FeeRateRequest;
import com.huobi.client.req.trade.MatchResultRequest;
import com.huobi.client.req.trade.OrderHistoryRequest;
import com.huobi.client.req.trade.OrdersRequest;
import com.huobi.client.req.trade.ReqOrderListRequest;
import com.huobi.client.req.trade.SubOrderUpdateRequest;
import com.huobi.constant.WebSocketConstants;
import com.huobi.constant.enums.AccountTypeEnum;
import com.huobi.constant.enums.OrderStateEnum;
import com.huobi.constant.enums.OrderTypeEnum;
import com.huobi.constant.enums.QueryDirectionEnum;
import com.huobi.constant.enums.StopOrderOperatorEnum;
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
import com.huobi.model.trade.OrderDetailReq;
import com.huobi.model.trade.OrderListReq;
import com.huobi.model.trade.OrderUpdateEvent;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.connection.HuobiWebSocketConnection;
import com.huobi.service.huobi.parser.trade.BatchCancelOpenOrdersResultParser;
import com.huobi.service.huobi.parser.trade.BatchCancelOrderResultParser;
import com.huobi.service.huobi.parser.trade.FeeRateParser;
import com.huobi.service.huobi.parser.trade.MatchResultParser;
import com.huobi.service.huobi.parser.trade.OrderDetailReqParser;
import com.huobi.service.huobi.parser.trade.OrderListReqParser;
import com.huobi.service.huobi.parser.trade.OrderParser;
import com.huobi.service.huobi.parser.trade.OrderUpdateEventParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;
import com.huobi.utils.ResponseCallback;
import com.huobi.utils.SymbolUtils;

public class HuobiTradeService implements TradeClient {


  public static final String CREATE_ORDER_PATH = "/v1/order/orders/place";
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


  public static final String WEBSOCKET_ORDER_UPDATE_TOPIC = "orders.$symbol.update";
  public static final String WEBSOCKET_ORDER_LIST_TOPIC = "orders.list";
  public static final String WEBSOCKET_ORDER_DETAIL_TOPIC = "orders.detail";


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
        .shouldNotNull(request.getAccountType(), "AccountType")
        .shouldNotNull(request.getAmount(), "Amount")
        .shouldNotNull(request.getType(), "Type");

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

    String source = "api";
    if (request.getAccountType() == AccountTypeEnum.MARGIN) {
      source = "margin-api";
    } else if (request.getAccountType() == AccountTypeEnum.SUPER_MARGIN) {
      source = "super-margin-api";
    }

    Account account = null;
    if (request.getAccountType() == AccountTypeEnum.MARGIN) {
      account = huobiAccountService.getMarginAccount(request.getSymbol());
    } else {
      account = huobiAccountService.getAccount(request.getAccountType());
    }

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("account-id", account.getId())
        .putToPost("amount", request.getAmount())
        .putToPost("price", request.getPrice())
        .putToPost("symbol", request.getSymbol())
        .putToPost("type", request.getType().getCode())
        .putToPost("source", source)
        .putToPost("client-order-id", request.getClientOrderId())
        .putToPost("stop-price", request.getStopPrice())
        .putToPost("operator", request.getOperator() == null ? null : request.getOperator().getOperator());

    JSONObject jsonObject = restConnection.executePostWithSignature(CREATE_ORDER_PATH, builder);
    return jsonObject.getLong("data");
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
        .shouldNotNull(request.getAccountType(), "accountType");
    if (request.getSize() != null) {
      InputChecker.checker()
          .checkRange(request.getSize(), 1, 100, "size");
    }
    Account account = huobiAccountService.getAccount(request.getAccountType());
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

    Account account = huobiAccountService.getAccount(request.getAccountType());
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


  public void subOrderUpdate(SubOrderUpdateRequest request, ResponseCallback<OrderUpdateEvent> callback) {

    // 检查参数
    InputChecker.checker()
        .shouldNotNull(request.getSymbols(), "symbols");

    // 格式化symbol为数组
    List<String> symbolList = SymbolUtils.parseSymbols(request.getSymbols());

    // 检查数组
    InputChecker.checker().checkSymbolList(symbolList);

    List<String> commandList = new ArrayList<>(symbolList.size());
    symbolList.forEach(symbol -> {

      String topic = WEBSOCKET_ORDER_UPDATE_TOPIC
          .replace("$symbol", symbol);

      JSONObject command = new JSONObject();
      command.put("op", WebSocketConstants.OP_SUB);
      command.put("topic", topic);
      command.put("id", System.nanoTime());
      commandList.add(command.toJSONString());
    });

    HuobiWebSocketConnection.createAssetConnection(options, commandList, new OrderUpdateEventParser(), callback, false);
  }

  public void reqOrderList(ReqOrderListRequest request, ResponseCallback<OrderListReq> callback) {

    InputChecker.checker()
        .shouldNotNull(request.getAccountType(), "account-type")
        .shouldNotNull(request.getSymbol(), "symbol")
        .checkList(request.getStates(), 1, 100, "states");

    Account account = null;
    if (request.getAccountType() == AccountTypeEnum.MARGIN) {
      account = huobiAccountService.getMarginAccount(request.getSymbol());
    } else {
      account = huobiAccountService.getAccount(request.getAccountType());
    }

    String startDateString = request.getStartDate() == null
        ? null : DateFormatUtils.format(request.getStartDate(), "yyyy-MM-dd");
    String endDateString = request.getEndDate() == null
        ? null : DateFormatUtils.format(request.getEndDate(), "yyyy-MM-dd");

    JSONObject command = new JSONObject();
    command.put("op", WebSocketConstants.OP_REQ);
    command.put("topic", WEBSOCKET_ORDER_LIST_TOPIC);
    command.put("account-id", account.getId());
    command.put("symbol", request.getSymbol());
    command.put("types", request.getTypesString());
    command.put("states", request.getStatesString());
    command.put("start-date", startDateString);
    command.put("end-date", endDateString);
    command.put("from", request.getFrom() != null ? request.getFrom() + "" : null);
    command.put("direct", request.getDirection() != null ? request.getDirection().toString() : null);
    command.put("size", request.getSize() != null ? request.getSize() + "" : null);
    List<String> commandList = new ArrayList<>(1);
    commandList.add(command.toJSONString());

    HuobiWebSocketConnection.createAssetConnection(options, commandList, new OrderListReqParser(), callback, true);

  }

  public void reqOrderDetail(Long orderId, ResponseCallback<OrderDetailReq> callback) {

    InputChecker.checker()
        .shouldNotNull(orderId, "order-id");

    JSONObject command = new JSONObject();
    command.put("op", WebSocketConstants.OP_REQ);
    command.put("topic", WEBSOCKET_ORDER_DETAIL_TOPIC);
    command.put("order-id", orderId.toString());
    List<String> commandList = new ArrayList<>(1);
    commandList.add(command.toJSONString());

    HuobiWebSocketConnection.createAssetConnection(options, commandList, new OrderDetailReqParser(), callback, true);

  }

}
