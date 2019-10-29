package com.huobi.client.examples;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.model.CrossMarginAccount;
import com.huobi.client.model.CrossMarginLoanOrder;
import com.huobi.client.model.enums.CrossMarginTransferType;
import com.huobi.client.model.enums.QueryDirection;
import com.huobi.client.model.request.CrossMarginApplyLoanRequest;
import com.huobi.client.model.request.CrossMarginLoanOrderRequest;
import com.huobi.client.model.request.CrossMarginRepayLoanRequest;
import com.huobi.client.model.request.CrossMarginTransferRequest;

public class CrossMarginExamples {

  public static void main(String[] args) {
    String currency = "usdt";
    BigDecimal transferAmount = new BigDecimal("50");
    BigDecimal loanAmount = new BigDecimal("100");

    SyncRequestClient syncRequestClient = SyncRequestClient.create(Constants.API_KEY, Constants.SECRET_KEY);

    CrossMarginAccount crossMarginAccount = syncRequestClient.getCrossMarginAccount();

    System.out.println("Cross Margin Account:"+JSON.toJSONString(crossMarginAccount));
    crossMarginAccount.getList().forEach(balance -> {
      System.out.println("Balance:"+JSON.toJSONString(balance));
    });
    System.out.println();



    long transferToCrossMargin = syncRequestClient.transferCrossMargin(CrossMarginTransferRequest.builder()
        .type(CrossMarginTransferType.SPOT_TO_SUPER_MARGIN)
        .currency(currency)
        .amount(transferAmount)
        .build());

    System.out.println(" transfer to cross margin :" + transferToCrossMargin);

    long loanOrderId = syncRequestClient.applyCrossMarginLoan(CrossMarginApplyLoanRequest.builder()
        .currency(currency)
        .amount(loanAmount)
        .build());

    System.out.println(" apply loan order id:" + loanOrderId);


    timeWait(3000);

    syncRequestClient.repayCrossMarginLoan(CrossMarginRepayLoanRequest.builder()
        .orderId(loanOrderId)
        .amount(loanAmount)
        .build());

    System.out.println(" repay loan finish . ");


    timeWait(10000);

    long transferToSpot = syncRequestClient.transferCrossMargin(CrossMarginTransferRequest.builder()
        .type(CrossMarginTransferType.SUPER_MARGIN_TO_SPOT)
        .currency(currency)
        .amount(transferAmount)
        .build());

    System.out.println(" transfer to spot :" + transferToSpot);


    List<CrossMarginLoanOrder> orderList = syncRequestClient.getCrossMarginLoanHistory(CrossMarginLoanOrderRequest.builder()
        .build());

    orderList.forEach(order->{
      System.out.println("Cross Loan Order:"+new Date(order.getCreatedAt()) +":"+ JSON.toJSONString(order));
    });


  }


  public static void timeWait(long millis) {
    try {
      System.out.println(" time wait " + millis + " ms");
      Thread.sleep(millis);
    } catch (InterruptedException e) {
    }
  }

}
