package com.huobi.Hmac256examples;

import java.math.BigDecimal;
import java.util.List;

import com.huobi.constant.Constants;
import com.huobi.client.IsolatedMarginClient;
import com.huobi.client.req.margin.*;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.enums.MarginTransferDirectionEnum;
import com.huobi.model.isolatedmargin.IsolatedMarginAccount;
import com.huobi.model.isolatedmargin.IsolatedMarginLoadOrder;
import com.huobi.model.isolatedmargin.IsolatedMarginSymbolInfo;
import com.huobi.model.isolatedmargin.LeveragePositionLimitResult;
import com.huobi.service.huobi.utils.DataUtils;

public class IsolatedMarginClientExample {

  public static void main(String[] args) {

    IsolatedMarginClient marginService = IsolatedMarginClient.create(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY).sign(Constants.SIGN)
        .build());

    String symbol = "xrpusdt";
    String currency = "usdt";


    // 划转至margin
    Long transferInId = marginService.transfer(IsolatedMarginTransferRequest.builder()
        .direction(MarginTransferDirectionEnum.SPOT_TO_MARGIN)
        .symbol(symbol)
        .currency(currency)
        .amount(new BigDecimal(50))
        .build());
    System.out.println(" transfer to margin :" + transferInId);

    // 查询余额
    List<IsolatedMarginAccount> accountList = marginService.getLoanBalance(IsolatedMarginAccountRequest.builder().build());
    accountList.forEach(isolatedMarginAccount -> {
      System.out.println("account:" + isolatedMarginAccount.toString());
      isolatedMarginAccount.getBalanceList().forEach(balance -> {
        System.out.println("===>Balance:" + balance.toString());
      });
    });

    // 停100ms
    DataUtils.timeWait(100L);

    BigDecimal loanAmount = new BigDecimal("100");

    // 申请贷款
    Long applyId = marginService.applyLoan(IsolatedMarginApplyLoanRequest.builder()
        .symbol(symbol)
        .currency(currency)
        .amount(loanAmount)
        .build());

    System.out.println(" margin apply loan :" + applyId);

    // 查询余额
    List<IsolatedMarginAccount> accountList1 = marginService.getLoanBalance(IsolatedMarginAccountRequest.builder().build());
    accountList1.forEach(isolatedMarginAccount -> {
      System.out.println("account:" + isolatedMarginAccount.toString());
      isolatedMarginAccount.getBalanceList().forEach(balance -> {
        System.out.println("===>Balance:" + balance.toString());
      });
    });

    // 停1000ms
    DataUtils.timeWait(5000L);

    // 还款
    Long repayId = marginService.repayLoan(IsolatedMarginRepayLoanRequest.builder()
        .orderId(applyId)
        .amount(loanAmount)
        .build());
    System.out.println(" margin repay loan :" + repayId);

    //转出
    Long transferOutId = marginService.transfer(IsolatedMarginTransferRequest.builder()
        .direction(MarginTransferDirectionEnum.MARGIN_TO_SPOT)
        .symbol(symbol)
        .currency(currency)
        .amount(new BigDecimal(50))
        .build());
    System.out.println(" transfer to spot :" + transferOutId);

    List<IsolatedMarginAccount> accountList2 = marginService.getLoanBalance(IsolatedMarginAccountRequest.builder().build());
    accountList2.forEach(isolatedMarginAccount -> {
      System.out.println("account:" + isolatedMarginAccount.toString());
      isolatedMarginAccount.getBalanceList().forEach(balance -> {
        System.out.println("===>Balance:" + balance.toString());
      });
    });

    List<IsolatedMarginLoadOrder> loanOrderList = marginService.getLoanOrders(IsolatedMarginLoanOrdersRequest.builder()
        .symbol(symbol)
        .size(1)
        .build());

    loanOrderList.forEach(order -> {
      System.out.println(order.toString());
    });

    List<IsolatedMarginSymbolInfo> list = marginService.getLoanInfo(IsolatedMarginLoanInfoRequest.builder().symbols("xrpusdt").build());
    list.forEach(info -> {
      System.out.println(info);
    });

    LeveragePositionLimitRequest leveragePositionLimitRequest = LeveragePositionLimitRequest.builder().currency("").build();
    List<LeveragePositionLimitResult> leveragePositionLimit = marginService.getLeveragePositionLimit(leveragePositionLimitRequest);
    System.out.println(leveragePositionLimit);

  }

}
