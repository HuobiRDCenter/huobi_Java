package com.huobi.service.huobi;

import com.alibaba.fastjson.JSONObject;

import com.huobi.client.AlgoClient;
import com.huobi.client.req.algo.CancelAlgoOrderRequest;
import com.huobi.client.req.algo.CreateAlgoOrderRequest;
import com.huobi.client.req.algo.GetHistoryAlgoOrdersRequest;
import com.huobi.client.req.algo.GetOpenAlgoOrdersRequest;
import com.huobi.constant.Options;
import com.huobi.constant.enums.algo.AlgoOrderTypeEnum;
import com.huobi.model.algo.AlgoOrder;
import com.huobi.model.algo.CancelAlgoOrderResult;
import com.huobi.model.algo.CreateAlgoOrderResult;
import com.huobi.model.algo.GetHistoryAlgoOrdersResult;
import com.huobi.model.algo.GetOpenAlgoOrdersResult;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.parser.algo.AlgoOrderParser;
import com.huobi.service.huobi.parser.algo.CancelAlgoOrderResultParser;
import com.huobi.service.huobi.parser.algo.CreateAlgoOrderResultParser;
import com.huobi.service.huobi.parser.algo.GetHistoryAlgoOrdersResultParser;
import com.huobi.service.huobi.parser.algo.GetOpenAlgoOrdersResultParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;


public class HuobiAlgoService implements AlgoClient {

  private static final String GET_ALGO_ORDERS_SPECIFIC_PATH = "/v2/algo-orders/specific";
  private static final String GET_OPEN_ALGO_ORDERS_PATH = "/v2/algo-orders/opening";
  private static final String GET_HISTORY_ALGO_ORDERS_PATH = "/v2/algo-orders/history";
  private static final String CREATE_ALGO_ORDER_PATH = "/v2/algo-orders";
  private static final String CANCEL_ALGO_ORDER_PATH = "/v2/algo-orders/cancellation";


  private Options options;

  private HuobiRestConnection restConnection;

  public HuobiAlgoService(Options options) {
    this.options = options;
    this.restConnection = new HuobiRestConnection(options);
  }


  @Override
  public CreateAlgoOrderResult createAlgoOrder(CreateAlgoOrderRequest request) {

    InputChecker checker = InputChecker.checker()
        .shouldNotNull(request.getAccountId(), "accountId")
        .shouldNotNull(request.getSymbol(), "symbol")
        .shouldNotNull(request.getOrderSide(), "orderSide")
        .shouldNotNull(request.getOrderType(), "orderType")
        .shouldNotNull(request.getClientOrderId(), "clientOrderId")
        .shouldNotNull(request.getStopPrice(), "stopPrice");

    if (request.getOrderType() == AlgoOrderTypeEnum.LIMIT) {
      checker
          .shouldNotNull(request.getOrderPrice(), "orderPrice")
          .shouldNotNull(request.getOrderSize(), "orderSize");
    } else {
      checker.shouldNotNull(request.getOrderValue(), "orderValue");
    }

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("accountId", request.getAccountId())
        .putToPost("symbol", request.getSymbol())
        .putToPost("orderPrice", request.getOrderPrice())
        .putToPost("orderSide", request.getOrderSide().getSide())
        .putToPost("orderSize", request.getOrderSize())
        .putToPost("orderValue", request.getOrderValue())
        .putToPost("timeInForce", request.getTimeInForce() == null ? null : request.getTimeInForce().getTimeInForce())
        .putToPost("orderType", request.getOrderType().getType())
        .putToPost("clientOrderId", request.getClientOrderId())
        .putToPost("stopPrice", request.getStopPrice());

    JSONObject jsonObject = restConnection.executePostWithSignature(CREATE_ALGO_ORDER_PATH, builder);
    return new CreateAlgoOrderResultParser().parse(jsonObject.getJSONObject("data"));
  }

  @Override
  public CancelAlgoOrderResult cancelAlgoOrder(CancelAlgoOrderRequest request) {

    InputChecker.checker().checkList(request.getClientOrderIds(), 1, 50, "clientOrderIds");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("clientOrderIds", request.getClientOrderIds());

    JSONObject jsonObject = restConnection.executePostWithSignature(CANCEL_ALGO_ORDER_PATH, builder);
    JSONObject data = jsonObject.getJSONObject("data");
    return new CancelAlgoOrderResultParser().parse(data);
  }

  @Override
  public GetOpenAlgoOrdersResult getOpenAlgoOrders(GetOpenAlgoOrdersRequest request) {

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("accountId", request.getAccountId())
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("orderSide", request.getOrderSide() == null ? null : request.getOrderSide().getSide())
        .putToUrl("orderType", request.getOrderType() == null ? null : request.getOrderType().getType())
        .putToUrl("sort", request.getSort() == null ? null : request.getSort().getSort())
        .putToUrl("limit", request.getLimit())
        .putToUrl("fromId", request.getFromId());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_OPEN_ALGO_ORDERS_PATH, builder);
    return new GetOpenAlgoOrdersResultParser().parse(jsonObject);
  }

  @Override
  public GetHistoryAlgoOrdersResult getHistoryAlgoOrders(GetHistoryAlgoOrdersRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getSymbol(), "symbol")
        .shouldNotNull(request.getOrderStatus(), "orderStatus")
    ;

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("accountId", request.getAccountId())
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("orderSide", request.getOrderSide() == null ? null : request.getOrderSide().getSide())
        .putToUrl("orderType", request.getOrderType() == null ? null : request.getOrderType().getType())
        .putToUrl("orderStatus", request.getOrderStatus() == null ? null : request.getOrderStatus().getStatus())
        .putToUrl("startTime", request.getStartTime())
        .putToUrl("endTime", request.getEndTime())
        .putToUrl("sort", request.getSort() == null ? null : request.getSort().getSort())
        .putToUrl("limit", request.getLimit())
        .putToUrl("fromId", request.getFromId());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_HISTORY_ALGO_ORDERS_PATH, builder);
    return new GetHistoryAlgoOrdersResultParser().parse(jsonObject);
  }

  @Override
  public AlgoOrder getAlgoOrdersSpecific(String clientOrderId) {

    InputChecker.checker().shouldNotNull(clientOrderId, "clientOrderId");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("clientOrderId", clientOrderId);

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_ALGO_ORDERS_SPECIFIC_PATH, builder);
    return new AlgoOrderParser().parse(jsonObject.getJSONObject("data"));
  }
}
