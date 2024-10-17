package com.huobi.Hmac256examples;

import java.math.BigDecimal;
import java.util.List;

import com.huobi.Constants;
import com.huobi.client.AccountClient;
import com.huobi.client.req.account.*;
import com.huobi.client.req.trade.SubOrderUpdateV2Request;
import com.huobi.client.req.trade.SubTradeClearingRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.enums.*;
import com.huobi.model.account.*;

public class AccountClientExample {

  public static void main(String[] args) {

    Long accountId = 31252055L;
//    Long accountId = 31278987L;
    AccountClient accountService
            = AccountClient.create(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY).sign(Constants.SIGN)
        .build());

//    List<Account> accountList = accountService.getAccounts();
//    accountList.forEach(account -> {
//      System.out.println(account.toString());
//    });


    AccountBalance accountBalance = accountService.getAccountBalance(AccountBalanceRequest.builder()
        .accountId(accountId)
        .build());

    System.out.println(accountBalance.getId());
    System.out.println(accountBalance.getType());
    System.out.println(accountBalance.getState());
    accountBalance.getList().forEach(balance -> {
      System.out.println(balance.toString());
    });

    List<AccountHistory> historyList = accountService.getAccountHistory(AccountHistoryRequest.builder().accountId(accountId).build());
    historyList.forEach(history->{
      System.out.println(history);
    });

    AccountLedgerResult accountLedgerResult = accountService.getAccountLedger(AccountLedgerRequest.builder()
        .accountId(accountId)
        .limit(2)
        .build());
    System.out.println("leger nextId: " + accountLedgerResult.getNextId());
    accountLedgerResult.getLedgerList().forEach(ledger -> {
      System.out.println(ledger);
    });


    accountService.subAccountsUpdate(SubAccountUpdateRequest.builder()
        .accountUpdateMode(AccountUpdateModeEnum.ACCOUNT_CHANGE).build(), event -> {
      System.out.println(event.toString());
    });


    AccountTransferResult accountTransferResult = accountService.accountTransfer(AccountTransferRequest.builder()
        .fromUser(123L)
        .fromAccount(456L)
        .fromAccountType(AccountTransferAccountTypeEnum.SPOT)
        .toUser(678L)
        .toAccount(789L)
        .toAccountType(AccountTransferAccountTypeEnum.MARGIN)
        .currency("usdt")
        .amount(new BigDecimal("10"))
        .build());

    System.out.println("account transfer result:"+accountTransferResult.toString());

    AccountFuturesTransferResult accountFuturesTransferResult = accountService.accountFuturesTransfer(AccountFuturesTransferRequest.builder()
        .currency("xrp")
        .amount(new BigDecimal("5"))
        .type(AccountFuturesTransferTypeEnum.PRO_TO_FUTURES)
        .build());

    System.out.println("account futures result:"+accountFuturesTransferResult.toString());

    Point point = accountService.getPoint(PointRequest.builder().build());
    System.out.println("get point: " + point);


    PointTransferResult pointTransferResult = accountService.pointTransfer(PointTransferRequest.builder()
      .fromUid(123L)
      .toUid(123L)
      .groupId(123L)
      .amount(BigDecimal.ONE)
      .build());
    System.out.println(pointTransferResult);

    AccountAssetValuationResult accountAssetValuationResult = accountService.accountAssetValuation(AccountAssetValuationRequest.builder().accountType(AccountTypeEnum.SPOT).build());
    System.out.println(accountAssetValuationResult);

    accountService.subOrderUpdateV2(SubOrderUpdateV2Request.builder().symbols("*").build(), orderUpdateV2Event -> {

      System.out.println(orderUpdateV2Event.toString());

    });

    accountService.subTradeClearing(SubTradeClearingRequest.builder().symbols("*").build(), event -> {
      System.out.println(event.toString());
    });

    AccountValuationRequest accountValuationRequest = AccountValuationRequest.builder().build();
    AccountValuationResult accountValuationResult = accountService.accountValuation(accountValuationRequest);
    System.out.println(accountValuationResult);

    AccountTransferV2Request accountTransferV2Request = AccountTransferV2Request.builder().from(BusinessLineAccountTypeEnum.SPOT).to(BusinessLineAccountTypeEnum.LINEARSWAP).currency("usdt").amount(new BigDecimal(100)).marginAccount("USDT").build();
    AccountTransferV2Result accountTransferV2Result = accountService.accountTransferV2(accountTransferV2Request);
    System.out.println(accountTransferV2Result);

    UserInfo accountUserInfo = accountService.getAccountUserInfo();
    System.out.println(accountUserInfo);

    OverviewInfo overviewInfo = accountService.getOverviewInfo();
    System.out.println(overviewInfo);

    accountService.feeSwitch(FeeSwitchRequest.builder().switchType(1).deductionCurrency("TRX").build());

  }

}
