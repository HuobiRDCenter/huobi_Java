package com.huobi.examples;

import java.math.BigDecimal;
import java.util.List;

import com.huobi.Constants;
import com.huobi.client.AccountClient;
import com.huobi.client.req.account.AccountBalanceRequest;
import com.huobi.client.req.account.SubAccountChangeRequest;
import com.huobi.client.req.account.TransferSubuserRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.enums.BalanceModeEnum;
import com.huobi.constant.enums.TransferMasterTypeEnum;
import com.huobi.model.account.Account;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.SubuserAggregateBalance;

public class AccountClientExample {

  public static void main(String[] args) {

    AccountClient accountService = AccountClient.create(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY)
        .build());

    List<Account> accountList = accountService.getAccounts();
    accountList.forEach(account -> {
      System.out.println(account.toString());
    });


    AccountBalance accountBalance = accountService.getAccountBalance(AccountBalanceRequest.builder()
        .accountId(290082L)
        .build());

    System.out.println(accountBalance.getId());
    System.out.println(accountBalance.getType());
    System.out.println(accountBalance.getState());
    accountBalance.getList().forEach(balance -> {
      System.out.println(balance.toString());
    });

    System.out.println("===========transfer to subuser ===============");
    long outTransferId = accountService.transferSubuser(TransferSubuserRequest.builder()
        .subUid(120491258L)
        .currency("usdt")
        .amount(new BigDecimal("10"))
        .type(TransferMasterTypeEnum.MASTER_TRANSFER_OUT)
        .build());
    System.out.println("===========transfer to subuser  result:"+outTransferId+"===============");

    List<AccountBalance> subAccountBalanceList = accountService.getSubuserAccountBalance(120491258L);
    System.out.println(subAccountBalanceList);

    List<SubuserAggregateBalance> aggBalanceList = accountService.getSubuserAggregateBalance();
    System.out.println("agg balance list:"+aggBalanceList.toString());

    System.out.println("===========transfer to master ===============");
    long inTransferId = accountService.transferSubuser(TransferSubuserRequest.builder()
        .subUid(120491258L)
        .currency("usdt")
        .amount(new BigDecimal("10"))
        .type(TransferMasterTypeEnum.MASTER_TRANSFER_IN)
        .build());
    System.out.println("===========transfer to subuser  result:"+inTransferId+"===============");

    List<AccountBalance> subAccountBalanceList1 = accountService.getSubuserAccountBalance(120491258L);
    System.out.println(subAccountBalanceList1);
//
    accountService.subAccounts(SubAccountChangeRequest.builder()
        .balanceMode(BalanceModeEnum.TOTAL)
        .build(),(accountChangeEvent)->{

      System.out.println("event:"+accountChangeEvent.getEvent());
      accountChangeEvent.getList().forEach(accountChange -> {
        System.out.println(accountChange.toString());
      });
    });

    accountService.reqAccounts((accountReq) -> {

      System.out.println("topic:"+accountReq.getTopic());
      System.out.println("cid:"+accountReq.getCid());
      accountReq.getBalanceList().forEach(rAccountBalance -> {
        System.out.println(rAccountBalance.toString());
      });

    });

  }

}
