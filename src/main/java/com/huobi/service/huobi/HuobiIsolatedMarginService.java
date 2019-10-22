package com.huobi.service.huobi;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.IsolatedMarginClient;
import com.huobi.client.req.margin.IsolatedMarginAccountRequest;
import com.huobi.client.req.margin.IsolatedMarginApplyLoanRequest;
import com.huobi.client.req.margin.IsolatedMarginLoanOrdersRequest;
import com.huobi.client.req.margin.IsolatedMarginRepayLoanRequest;
import com.huobi.client.req.margin.IsolatedMarginTransferRequest;
import com.huobi.constant.Constants;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.Options;
import com.huobi.constant.enums.MarginTransferDirectionEnum;
import com.huobi.model.isolatedmargin.IsolatedMarginAccount;
import com.huobi.model.isolatedmargin.IsolatedMarginLoadOrder;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.parser.isolatedmargin.IsolatedMarginAccountParser;
import com.huobi.service.huobi.parser.isolatedmargin.IsolatedMarginLoadOrderParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;

public class HuobiIsolatedMarginService implements IsolatedMarginClient {

  public static final String TRANSFER_TO_MARGIN_PATH = "/v1/dw/transfer-in/margin";
  public static final String TRANSFER_TO_SPOT_PATH = "/v1/dw/transfer-out/margin";
  public static final String GET_BALANCE_PATH = "/v1/margin/accounts/balance";
  public static final String GET_LOAN_ORDER_PATH = "/v1/margin/loan-orders";

  public static final String APPLY_LOAN_PATH = "/v1/margin/orders";
  public static final String REPAY_LOAN_PATH = "/v1/margin/orders/{order-id}/repay";


  private Options options;

  private HuobiRestConnection restConnection;

  public HuobiIsolatedMarginService(Options options) {
    this.options = options;
    this.restConnection = new HuobiRestConnection(options);
  }


  @Override
  public Long transfer(IsolatedMarginTransferRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getDirection(), "direction")
        .checkSymbol(request.getSymbol())
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
        .putToPost("symbol", request.getSymbol())
        .putToPost("amount", request.getAmount());

    JSONObject jsonObject = restConnection.executePostWithSignature(path, builder);
    return jsonObject.getLong("data");
  }

  @Override
  public Long applyLoan(IsolatedMarginApplyLoanRequest request) {

    InputChecker.checker()
        .checkSymbol(request.getSymbol())
        .checkCurrency(request.getCurrency())
        .shouldNotNull(request.getAmount(), "amount");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("currency", request.getCurrency())
        .putToPost("symbol", request.getSymbol())
        .putToPost("amount", request.getAmount());

    JSONObject jsonObject = restConnection.executePostWithSignature(APPLY_LOAN_PATH, builder);
    return jsonObject.getLong("data");
  }

  @Override
  public Long repayLoan(IsolatedMarginRepayLoanRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getOrderId(), "order-id")
        .shouldNotNull(request.getAmount(), "amount");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("amount", request.getAmount());

    String path = REPAY_LOAN_PATH.replace("{order-id}", request.getOrderId().toString());
    JSONObject jsonObject = restConnection.executePostWithSignature(path, builder);
    return jsonObject.getLong("data");
  }

  @Override
  public List<IsolatedMarginLoadOrder> getLoanOrders(IsolatedMarginLoanOrdersRequest request) {

    InputChecker.checker()
        .checkSymbol(request.getSymbol());

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("start-date", request.getStartDate(), "yyyy-MM-dd")
        .putToUrl("end-date", request.getEndDate(), "yyyy-MM-dd")
        .putToUrl("states", request.getStatesString())
        .putToUrl("from", request.getFrom())
        .putToUrl("size", request.getSize())
        .putToUrl("direct", request.getDirection() == null ? null : request.getDirection().getCode());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_LOAN_ORDER_PATH, builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new IsolatedMarginLoadOrderParser().parseArray(data);
  }

  @Override
  public List<IsolatedMarginAccount> getLoanBalance(IsolatedMarginAccountRequest request) {

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("sub-uid", request.getSubUid());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_BALANCE_PATH, builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new IsolatedMarginAccountParser().parseArray(data);
  }


  public static void main(String[] args) {

    HuobiIsolatedMarginService marginService = new HuobiIsolatedMarginService(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY)
        .build());

    String symbol = "xrpusdt";
    String currency = "usdt";

//    // 划转至margin
//    Long transferInId = marginService.transfer(IsolatedMarginTransferRequest.builder()
//        .direction(MarginTransferDirectionEnum.SPOT_TO_MARGIN)
//        .symbol(symbol)
//        .currency(currency)
//        .amount(new BigDecimal(50))
//        .build());
//    System.out.println(" transfer to margin :" + transferInId);
//
//    // 查询余额
//    List<IsolatedMarginAccount> accountList = marginService.getLoanBalance(IsolatedMarginAccountRequest.builder().build());
//    accountList.forEach(isolatedMarginAccount -> {
//      System.out.println("account:" + isolatedMarginAccount.toString());
//      isolatedMarginAccount.getBalanceList().forEach(balance -> {
//        System.out.println("===>Balance:" + balance.toString());
//      });
//    });
//
//    // 停100ms
//    timeWait(100);
//
//    BigDecimal loanAmount = new BigDecimal("100");
//
//    // 申请贷款
//    Long applyId = marginService.applyLoan(IsolatedMarginApplyLoanRequest.builder()
//        .symbol(symbol)
//        .currency(currency)
//        .amount(loanAmount)
//        .build());
//
//    System.out.println(" margin apply loan :" + applyId);
//
//    // 查询余额
//    List<IsolatedMarginAccount> accountList1 = marginService.getLoanBalance(IsolatedMarginAccountRequest.builder().build());
//    accountList1.forEach(isolatedMarginAccount -> {
//      System.out.println("account:" + isolatedMarginAccount.toString());
//      isolatedMarginAccount.getBalanceList().forEach(balance -> {
//        System.out.println("===>Balance:" + balance.toString());
//      });
//    });
//
//    // 还款
//    Long repayId = marginService.repayLoan(IsolatedMarginRepayLoanRequest.builder()
//        .orderId(applyId)
//        .amount(loanAmount)
//        .build());
//    System.out.println(" margin repay loan :" + repayId);
//
//    // 停1000ms
//    timeWait(1000);
//
//    //转出
//    Long transferOutId = marginService.transfer(IsolatedMarginTransferRequest.builder()
//        .direction(MarginTransferDirectionEnum.MARGIN_TO_SPOT)
//        .symbol(symbol)
//        .currency(currency)
//        .amount(new BigDecimal(50))
//        .build());
//    System.out.println(" transfer to spot :" + transferOutId);
//
//    List<IsolatedMarginAccount> accountList2 = marginService.getLoanBalance(IsolatedMarginAccountRequest.builder().build());
//    accountList2.forEach(isolatedMarginAccount -> {
//      System.out.println("account:" + isolatedMarginAccount.toString());
//      isolatedMarginAccount.getBalanceList().forEach(balance -> {
//        System.out.println("===>Balance:" + balance.toString());
//      });
//    });


//    List<IsolatedMarginLoadOrder> loanOrderList = marginService.getLoanOrders(IsolatedMarginLoanOrdersRequest.builder()
//        .symbol(symbol)
//        .build());
//
//    loanOrderList.forEach(order->{
//      System.out.println(order.toString());
//    });
  }


}
