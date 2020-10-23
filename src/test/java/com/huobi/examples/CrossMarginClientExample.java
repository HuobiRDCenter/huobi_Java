package com.huobi.examples;

import java.math.BigDecimal;
import java.util.List;

import com.huobi.Constants;
import com.huobi.client.CrossMarginClient;
import com.huobi.client.req.crossmargin.CrossMarginApplyLoanRequest;
import com.huobi.client.req.crossmargin.CrossMarginLoanOrdersRequest;
import com.huobi.client.req.crossmargin.CrossMarginRepayLoanRequest;
import com.huobi.client.req.crossmargin.CrossMarginTransferRequest;
import com.huobi.client.req.crossmargin.GeneralLoanOrdersRequest;
import com.huobi.client.req.crossmargin.GeneralRepayLoanRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.enums.MarginTransferDirectionEnum;
import com.huobi.model.crossmargin.CrossMarginAccount;
import com.huobi.model.crossmargin.CrossMarginCurrencyInfo;
import com.huobi.model.crossmargin.CrossMarginLoadOrder;
import com.huobi.model.crossmargin.GeneralRepayLoanRecord;
import com.huobi.model.crossmargin.GeneralRepayLoanResult;
import com.huobi.service.huobi.utils.DataUtils;

public class CrossMarginClientExample {

    public static void main(String[] args) {
        CrossMarginClient marginService = CrossMarginClient.create(HuobiOptions.builder()
                .apiKey(Constants.API_KEY)
                .secretKey(Constants.SECRET_KEY)
                .build());

        String currency = "usdt";

        // 转入全仓
        Long transferInId = marginService.transfer(CrossMarginTransferRequest.builder()
                .direction(MarginTransferDirectionEnum.SPOT_TO_MARGIN)
                .currency(currency)
                .amount(new BigDecimal("50"))
                .build());
        System.out.println(" transfer to margin: " + transferInId);

        // 查询余额
        CrossMarginAccount crossMarginAccount = marginService.getLoanBalance();

        System.out.println("account:" + crossMarginAccount.toString());
        crossMarginAccount.getBalanceList().forEach(balance -> {
            System.out.println("======>" + balance.toString());
        });

        // 等待100ms
        DataUtils.timeWait(1000L);

        // 借款
        BigDecimal loanAmount = new BigDecimal("100");
        Long applyId = marginService.applyLoan(CrossMarginApplyLoanRequest.builder()
                .currency(currency)
                .amount(loanAmount)
                .build());

        System.out.println(" apply id:" + applyId);

        // 查询余额
        CrossMarginAccount crossMarginAccount1 = marginService.getLoanBalance();
        System.out.println("account:" + crossMarginAccount1.toString());
        crossMarginAccount1.getBalanceList().forEach(balance -> {
            System.out.println("======>" + balance.toString());
        });

        // 等待100ms
        DataUtils.timeWait(5000L);

        // 还款
        marginService.repayLoan(CrossMarginRepayLoanRequest.builder()
                .orderId(applyId)
                .amount(loanAmount)
                .build());

        System.out.println(" repay finish:");

        // 查询余额
        CrossMarginAccount crossMarginAccount2 = marginService.getLoanBalance();
        System.out.println("account:" + crossMarginAccount2.toString());
        crossMarginAccount2.getBalanceList().forEach(balance -> {
            System.out.println("======>" + balance.toString());
        });

        // 等待100ms
        DataUtils.timeWait(5000L);

        // 转出至现货
        Long transferOutId = marginService.transfer(CrossMarginTransferRequest.builder()
                .direction(MarginTransferDirectionEnum.MARGIN_TO_SPOT)
                .currency(currency)
                .amount(new BigDecimal("50"))
                .build());
        System.out.println(" transfer to spot: " + transferOutId);


        List<CrossMarginLoadOrder> orderList = marginService.getLoanOrders(CrossMarginLoanOrdersRequest.builder().build());
        orderList.forEach(order -> {
            System.out.println(order.toString());
        });

        List<CrossMarginCurrencyInfo> currencyInfoList = marginService.getLoanInfo();
        currencyInfoList.forEach(info -> {
            System.out.println(info);
        });

        GeneralRepayLoanRequest generalRepayLoanRequest = GeneralRepayLoanRequest.builder().currency("usdt").build();
        List<GeneralRepayLoanResult> generalRepayLoanResults = marginService.repayLoan(generalRepayLoanRequest);
        System.out.println("======>" + generalRepayLoanResults);
        GeneralLoanOrdersRequest generalLoanOrdersRequest = GeneralLoanOrdersRequest.builder().limit(1).build();
        List<GeneralRepayLoanRecord> generalRepayLoanRecords = marginService.getRepaymentLoanRecords(generalLoanOrdersRequest);
        System.out.println("======>" + generalRepayLoanRecords);
    }

}
