package com.huobi.service.huobi;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.CrossMarginClient;
import com.huobi.client.req.crossmargin.CrossMarginApplyLoanRequest;
import com.huobi.client.req.crossmargin.CrossMarginLoanOrdersRequest;
import com.huobi.client.req.crossmargin.CrossMarginRepayLoanRequest;
import com.huobi.client.req.crossmargin.CrossMarginTransferRequest;
import com.huobi.constant.Constants;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.Options;
import com.huobi.constant.enums.MarginTransferDirectionEnum;
import com.huobi.model.crossmargin.CrossMarginAccount;
import com.huobi.model.crossmargin.CrossMarginCurrencyInfo;
import com.huobi.model.crossmargin.CrossMarginLoadOrder;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.parser.crossmargin.CrossMarginAccountParser;
import com.huobi.service.huobi.parser.crossmargin.CrossMarginCurrencyInfoParser;
import com.huobi.service.huobi.parser.crossmargin.CrossMarginLoadOrderParser;
import com.huobi.service.huobi.parser.isolatedmargin.IsolatedMarginLoadOrderParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;

public class HuobiCrossMarginService implements CrossMarginClient {

  public static final String TRANSFER_TO_MARGIN_PATH = "/v1/cross-margin/transfer-in";
  public static final String TRANSFER_TO_SPOT_PATH = "/v1/cross-margin/transfer-out";
  public static final String APPLY_LOAN_PATH = "/v1/cross-margin/orders";
  public static final String REPAY_LOAN_PATH = "/v1/cross-margin/orders/{order-id}/repay";

  public static final String GET_BALANCE_PATH = "/v1/cross-margin/accounts/balance";
  public static final String GET_LOAN_INFO_PATH = "/v1/cross-margin/loan-info";
  public static final String GET_LOAN_ORDER_PATH = "/v1/cross-margin/loan-orders";

  private Options options;

  private HuobiRestConnection restConnection;

  public HuobiCrossMarginService(Options options) {
    this.options = options;
    this.restConnection = new HuobiRestConnection(options);
  }

  @Override
  public Long transfer(CrossMarginTransferRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getDirection(), "direction")
        .checkCurrency(request.getCurrency())
        .shouldNotNull(request.getAmount(), "amount");

    String path = null;
    if (request.getDirection() == MarginTransferDirectionEnum.SPOT_TO_MARGIN) {
      path = TRANSFER_TO_MARGIN_PATH;
    } else {
      path = TRANSFER_TO_SPOT_PATH;
    }

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("currency",request.getCurrency())
        .putToPost("amount",request.getAmount());
    JSONObject jsonObject = restConnection.executePostWithSignature(path,builder);
    return jsonObject.getLong("data");
  }

  @Override
  public Long applyLoan(CrossMarginApplyLoanRequest request) {

    InputChecker.checker()
        .checkCurrency(request.getCurrency())
        .shouldNotNull(request.getAmount(), "amount");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("currency", request.getCurrency())
        .putToPost("amount", request.getAmount());

    JSONObject jsonObject = restConnection.executePostWithSignature(APPLY_LOAN_PATH, builder);
    return jsonObject.getLong("data");
  }

  @Override
  public void repayLoan(CrossMarginRepayLoanRequest request) {
    InputChecker.checker()
        .shouldNotNull(request.getOrderId(), "order-id")
        .shouldNotNull(request.getAmount(), "amount");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("amount", request.getAmount());

    String path = REPAY_LOAN_PATH.replace("{order-id}", request.getOrderId().toString());
    restConnection.executePostWithSignature(path, builder);
  }

  @Override
  public List<CrossMarginLoadOrder> getLoanOrders(CrossMarginLoanOrdersRequest request) {

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("currency", request.getCurrency())
        .putToUrl("start-date", request.getStartDate(), "yyyy-MM-dd")
        .putToUrl("end-date", request.getEndDate(), "yyyy-MM-dd")
        .putToUrl("states", request.getStatesString())
        .putToUrl("from", request.getFrom())
        .putToUrl("size", request.getSize())
        .putToUrl("direct", request.getDirection() == null ? null : request.getDirection().getCode());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_LOAN_ORDER_PATH, builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new CrossMarginLoadOrderParser().parseArray(data);
  }

  @Override
  public CrossMarginAccount getLoanBalance() {

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_BALANCE_PATH, UrlParamsBuilder.build());
    JSONObject data = jsonObject.getJSONObject("data");
    return new CrossMarginAccountParser().parse(data);
  }

  public List<CrossMarginCurrencyInfo> getLoanInfo() {
    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_LOAN_INFO_PATH, UrlParamsBuilder.build());
    JSONArray data = jsonObject.getJSONArray("data");
    return new CrossMarginCurrencyInfoParser().parseArray(data);
  }


}
