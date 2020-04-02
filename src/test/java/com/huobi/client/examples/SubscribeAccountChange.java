package com.huobi.client.examples;

import com.alibaba.fastjson.JSON;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.SubscriptionOptions;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.model.AccountChange;
import com.huobi.client.model.enums.AccountChangeModeEnum;
import com.huobi.client.model.enums.BalanceMode;

public class SubscribeAccountChange {

  public static void main(String[] args) {
    SubscriptionOptions options = new SubscriptionOptions();
    options.setUri("wss://api.huobi.pro");
    SubscriptionClient subscriptionClient = SubscriptionClient.create(
        Constants.API_KEY, Constants.SECRET_KEY, options);
    subscriptionClient.subscribeAccountEvent(BalanceMode.AVAILABLE, (accountEvent) -> {
      System.out.println("---- Account Change: " + accountEvent.getChangeType() + " ----");
      for (AccountChange change : accountEvent.getData()) {
        System.out.println("Account: " + change.getAccountType());
        System.out.println("Currency: " + change.getCurrency());
        System.out.println("Balance: " + change.getBalance());
        System.out.println("Balance type: " + change.getBalanceType());
      }
    });

    subscriptionClient.requestAccountListEvent(accountListEvent -> {
      System.out.println("-------------Request Account List---------------");
      accountListEvent.getAccountList().forEach(account -> {

        System.out.println(" type:" + account.getType() + " state" + account.getState());
        account.getBalances().forEach(balance -> {
          System.out.println("    currency:" + balance.getCurrency() + " type:" + balance.getType() + " balance:" + balance.getBalance().toPlainString());
        });

      });
    });


    subscriptionClient.subscribeAccountChangeV2Event(AccountChangeModeEnum.TOTAL,(accountChangeV2Event) ->{
      System.out.println("===>"+ JSON.toJSONString(accountChangeV2Event));
    });



    subscriptionClient.subscribeTradeClearing("xrpusdt",(tradeClearingEvent)->{

      System.out.println("---------------------");
      System.out.println(tradeClearingEvent.getCh());
      System.out.println(JSON.toJSONString(tradeClearingEvent.getData()));
      System.out.println("---------------------");

    });
  }
}
