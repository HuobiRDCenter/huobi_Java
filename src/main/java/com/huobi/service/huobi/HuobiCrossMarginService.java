package com.huobi.service.huobi;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import com.huobi.client.CrossMarginClient;
import com.huobi.client.req.crossmargin.CrossMarginApplyLoanRequest;
import com.huobi.client.req.crossmargin.CrossMarginLoanOrdersRequest;
import com.huobi.client.req.crossmargin.CrossMarginRepayLoanRequest;
import com.huobi.client.req.crossmargin.CrossMarginTransferRequest;
import com.huobi.client.req.crossmargin.GeneralLoanOrdersRequest;
import com.huobi.client.req.crossmargin.GeneralRepayLoanRequest;
import com.huobi.constant.Options;
import com.huobi.constant.enums.MarginTransferDirectionEnum;
import com.huobi.model.crossmargin.CrossMarginAccount;
import com.huobi.model.crossmargin.CrossMarginCurrencyInfo;
import com.huobi.model.crossmargin.CrossMarginLoadOrder;
import com.huobi.model.crossmargin.GeneralRepayLoanRecord;
import com.huobi.model.crossmargin.GeneralRepayLoanResult;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.parser.account.GeneralRepayLoanRecordParser;
import com.huobi.service.huobi.parser.account.GeneralRepayLoanResultParser;
import com.huobi.service.huobi.parser.crossmargin.CrossMarginAccountParser;
import com.huobi.service.huobi.parser.crossmargin.CrossMarginCurrencyInfoParser;
import com.huobi.service.huobi.parser.crossmargin.CrossMarginLoadOrderParser;
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
    public static final String GENERAL_REPAY_LOAN_PATH = "/v2/account/repayment";
    public static final String GENERAL_GET_REPAYMENT_LOAN_RECORDS_PATH = "/v2/account/repayment";


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
                .putToPost("currency", request.getCurrency())
                .putToPost("amount", request.getAmount());
        JSONObject jsonObject = restConnection.executePostWithSignature(path, builder);
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

    @Override
    public List<GeneralRepayLoanResult> repayLoan(GeneralRepayLoanRequest request) {
        InputChecker.checker()
                .shouldNotNull(request.getAccountId(), "accountId")
                .shouldNotNull(request.getCurrency(), "currency")
                .shouldNotNull(request.getAmount(), "amount");

        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToPost("accountId", request.getAccountId())
                .putToPost("currency", request.getCurrency())
                .putToPost("amount", request.getAmount())
                .putToPost("transactId", request.getTransactId());
        JSONObject jsonObject = restConnection.executePostWithSignature(GENERAL_REPAY_LOAN_PATH, builder);
        JSONArray data = jsonObject.getJSONArray("data");
        return new GeneralRepayLoanResultParser().parseArray(data);
    }

    @Override
    public List<GeneralRepayLoanRecord> getRepaymentLoanRecords(GeneralLoanOrdersRequest request) {
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        if (StringUtils.isNotEmpty(request.getRepayId()))
            builder.putToUrl("repayId", request.getRepayId());
        if (StringUtils.isNotEmpty(request.getAccountId()))
            builder.putToUrl("accountId", request.getAccountId());
        if (StringUtils.isNotEmpty(request.getCurrency()))
            builder.putToUrl("currency", request.getCurrency());
        if (request.getStartTime() != 0)
            builder.putToUrl("startTime", request.getStartTime());
        if (request.getEndTime() != 0)
            builder.putToUrl("endTime", request.getEndTime());
        if (request.getSort() != null)
            builder.putToUrl("sort", request.getSort().getSort());
        if (request.getLimit() != 0)
            builder.putToUrl("limit", request.getLimit());
        if (request.getFromId() != 0)
            builder.putToUrl("fromId", request.getFromId());
        JSONObject jsonObject = restConnection.executeGetWithSignature(GENERAL_GET_REPAYMENT_LOAN_RECORDS_PATH, builder);
        JSONArray data = jsonObject.getJSONArray("data");
        return new GeneralRepayLoanRecordParser().parseArray(data);
    }


}
