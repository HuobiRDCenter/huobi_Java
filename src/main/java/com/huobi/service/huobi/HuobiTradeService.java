package com.huobi.service.huobi;

import java.util.ArrayList;
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
import com.huobi.model.trade.OrderDetailEvent;
import com.huobi.model.trade.OrderListEvent;
import com.huobi.model.trade.OrderUpdateEvent;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.connection.HuobiWebSocketConnection;
import com.huobi.service.huobi.parser.trade.BatchCancelOpenOrdersResultParser;
import com.huobi.service.huobi.parser.trade.BatchCancelOrderResultParser;
import com.huobi.service.huobi.parser.trade.FeeRateParser;
import com.huobi.service.huobi.parser.trade.MatchResultParser;
import com.huobi.service.huobi.parser.trade.OrderDetailEventParser;
import com.huobi.service.huobi.parser.trade.OrderListEventParser;
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
        .checkSymbol(request.getSymbol())
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

  public void reqOrderList(ReqOrderListRequest request, ResponseCallback<OrderListEvent> callback) {

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

    HuobiWebSocketConnection.createAssetConnection(options, commandList, new OrderListEventParser(), callback, true);

  }

  public void reqOrderDetail(Long orderId, ResponseCallback<OrderDetailEvent> callback) {

    InputChecker.checker()
        .shouldNotNull(orderId, "order-id");

    JSONObject command = new JSONObject();
    command.put("op", WebSocketConstants.OP_REQ);
    command.put("topic", WEBSOCKET_ORDER_DETAIL_TOPIC);
    command.put("order-id", orderId.toString());
    List<String> commandList = new ArrayList<>(1);
    commandList.add(command.toJSONString());

    HuobiWebSocketConnection.createAssetConnection(options, commandList, new OrderDetailEventParser(), callback, true);

  }

  public static void main(String[] args) {

    String symbol = "htusdt";

    HuobiTradeService huobiTradeService = new HuobiTradeService(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY)
        .build());

//    CreateOrderRequest buyLimitRequest = CreateOrderRequest.spotBuyLimit(symbol, new BigDecimal("3.37"), new BigDecimal("1"));
//    Long buyLimitId = tradeService.createOrder(buyLimitRequest);
//    System.out.println("create buy-limit order:" + buyLimitId);

//    CreateOrderRequest sellLimitRequest = CreateOrderRequest.spotSellLimit(symbol, new BigDecimal("4"), new BigDecimal("1"));
//    Long sellLimitId = tradeService.createOrder(sellLimitRequest);
//    System.out.println("create sell-limit order:" + sellLimitId);

//    CreateOrderRequest buyMarketRequest = CreateOrderRequest.spotBuyMarket(symbol, new BigDecimal("5"));
//    Long buyMarketId = tradeService.createOrder(buyMarketRequest);
//    System.out.println("create buy-market order:" + buyMarketId);
//
//
//    CreateOrderRequest sellMarketRequest = CreateOrderRequest.spotSellMarket(symbol, new BigDecimal("1"));
//    Long sellMarketId = tradeService.createOrder(sellMarketRequest);
//    System.out.println("create sell-market order:" + sellMarketId);

//    String superMarginSymbol = "xrpusdt";
//    CreateOrderRequest superMarginBuyLimitRequest = CreateOrderRequest
//        .superMarginBuyLimit(superMarginSymbol, new BigDecimal("0.27"), new BigDecimal("4"));
//    Long superMarginBuyLimitId = tradeService.createOrder(superMarginBuyLimitRequest);
//    System.out.println("create super-margin-buy-limit order:" + superMarginBuyLimitId);
//
//    CreateOrderRequest superMarginBuyMarketRequest = CreateOrderRequest.superMarginBuyMarket(superMarginSymbol, new BigDecimal("1"));
//    Long superMarginBuyMarketId = tradeService.createOrder(superMarginBuyMarketRequest);
//    System.out.println("create super-margin-buy-limit order:" + superMarginBuyMarketId);
//
//    CreateOrderRequest superMarginSellLimitRequest = CreateOrderRequest
//        .superMarginSellLimit(superMarginSymbol, new BigDecimal("0.29"), new BigDecimal("3.45"));
//    Long superMarginSellLimitId = tradeService.createOrder(superMarginSellLimitRequest);
//    System.out.println("create super-margin-sell-limit order:" + superMarginSellLimitId);
//
//    CreateOrderRequest superMarginSellMarketRequest = CreateOrderRequest.superMarginSellMarket(superMarginSymbol, new BigDecimal("3.45"));
//    Long superMarginSellMarketId = tradeService.createOrder(superMarginSellMarketRequest);
//    System.out.println("create super-margin-sell-limit order:" + superMarginSellMarketId);

//    String marginSymbol = "xrpusdt";
//    CreateOrderRequest marginBuyLimitRequest = CreateOrderRequest.marginBuyLimit(marginSymbol,new BigDecimal("0.27"),new BigDecimal("4"));
//    Long marginBuyLimitId = tradeService.createOrder(marginBuyLimitRequest);
//    System.out.println("create margin-buy-limit order:" + marginBuyLimitId);

//    CreateOrderRequest marginSellLimitRequest = CreateOrderRequest.marginSellLimit(marginSymbol,new BigDecimal("0.295"),new BigDecimal("4"));
//    Long marginSellLimitId = tradeService.createOrder(marginSellLimitRequest);
//    System.out.println("create margin-sell-limit order:" + marginSellLimitId);

//    CreateOrderRequest marginBuyMarketRequest = CreateOrderRequest.marginBuyMarket(marginSymbol, new BigDecimal("1"));
//    Long marginBuyMarketId = tradeService.createOrder(marginBuyMarketRequest);
//    System.out.println("create margin-buy-limit order:" + marginBuyMarketId);

//    CreateOrderRequest marginSellMarketRequest = CreateOrderRequest.marginSellMarket(marginSymbol, new BigDecimal("3.44"));
//    Long marginSellMarketId = tradeService.createOrder(marginSellMarketRequest);
//    System.out.println("create margin-sell-limit order:" + marginSellMarketId);

//    CreateOrderRequest buyStopLossRequest = CreateOrderRequest.buyStopLoss(
//        AccountTypeEnum.SPOT,
//        symbol,
//        new BigDecimal("3.4"),
//        new BigDecimal("1"),
//        new BigDecimal("3.41"),
//        StopOrderOperatorEnum.GTE);
//
//    Long buyStopLossId = tradeService.createOrder(buyStopLossRequest);
//    System.out.println("create buy-stop-limit order:" + buyStopLossId);

//    CreateOrderRequest sellStopLossRequest = CreateOrderRequest.sellStopLoss(
//        AccountTypeEnum.SPOT,
//        symbol,
//        new BigDecimal("3.32"),
//        new BigDecimal("1"),
//        new BigDecimal("3.33"),
//        StopOrderOperatorEnum.LTE);
//
//    Long sellStopLossId = tradeService.createOrder(sellStopLossRequest);
//    System.out.println("create sell-stop-limit order:" + sellStopLossId);

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

//    tradeService.subOrderUpdate(SubOrderUpdateRequest.builder().symbols(symbol).build(), (orderUpdateEvent)->{
//      System.out.println(orderUpdateEvent.toString());
//    });
//
//    tradeService.reqOrderList(ReqOrderListRequest.builder()
//        .accountType(AccountTypeEnum.SPOT)
//        .symbol(symbol)
//        .states(stateList)
//        .types(typeList)
//        .startDate(new Date(1571362445775L))
//        .endDate(new Date(1571362445775L))
//        .size(5)
//        .build(), (orderListEvent) -> {
//
//      System.out.println(orderListEvent.toString());
//      orderListEvent.getOrderList().forEach(order -> {
//        System.out.println(new Date(order.getCreatedAt())+"==>"+order.toString());
//      });
//    });


    huobiTradeService.reqOrderDetail(52286981706L,(orderDetailEvent)->{
      System.out.println(orderDetailEvent.toString());
    });
  }
}
