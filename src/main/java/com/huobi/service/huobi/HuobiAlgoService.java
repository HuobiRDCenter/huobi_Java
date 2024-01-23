package com.huobi.service.huobi;

import com.alibaba.fastjson.JSONObject;

import com.huobi.client.AlgoClient;
import com.huobi.client.req.algo.*;
import com.huobi.constant.Options;
import com.huobi.constant.enums.algo.AlgoOrderTypeEnum;
import com.huobi.model.algo.*;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.parser.algo.*;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;


public class HuobiAlgoService implements AlgoClient {

  private static final String GET_ALGO_ORDERS_SPECIFIC_PATH = "/v2/algo-orders/specific";//查询特定策略委托
  private static final String GET_OPEN_ALGO_ORDERS_PATH = "/v2/algo-orders/opening";//查询未触发OPEN策略委托
  private static final String GET_HISTORY_ALGO_ORDERS_PATH = "/v2/algo-orders/history";//查询策略委托历史
  private static final String CREATE_ALGO_ORDER_PATH = "/v2/algo-orders";//策略委托下单
  private static final String CANCEL_ALGO_ORDER_PATH = "/v2/algo-orders/cancellation";//策略委托（触发前）撤单
  private static final String CANCEL_ALGO_ALL_ORDER_PATH = "/v2/algo-orders/cancel-all-after";//自动撤销订单


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

  @Override
  public CancelAlgoAllOrderResult cancelAlgoAllOrder(CancelAlgoAllOrderRequest request) {
    InputChecker.checker().shouldNotNull(request.getTimeout(), "timeout");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
            .putToPost("timeout", request.getTimeout());

    JSONObject jsonObject = restConnection.executePostWithSignature(CANCEL_ALGO_ALL_ORDER_PATH, builder);
    return new CancelAlgoAllOrderResultParser().parse(jsonObject.getJSONObject("data"));
  }
}
