package com.huobi.client.examples;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.SubscriptionOptions;
import com.huobi.client.model.AccountChange;
import com.huobi.client.model.enums.BalanceMode;

public class SubscribeAccountChange {
  public static void main(String[] args) {
    SubscriptionOptions options = new SubscriptionOptions();
    options.setUri("wss://api.huobi.pro");
    SubscriptionClient subscriptionClient = SubscriptionClient.create(
        "xxxxxx", "xxxxxx", options);
    subscriptionClient.subscribeAccountEvent(BalanceMode.AVAILABLE, (accountEvent) -> {
      System.out.println("---- Account Change: " + accountEvent.getChangeType() + " ----");
      for (AccountChange change : accountEvent.getData()) {
        System.out.println("Account: " + change.getAccountType());
        System.out.println("Currency: " + change.getCurrency());
        System.out.println("Balance: " + change.getBalance());
        System.out.println("Balance type: " + change.getBalanceType());
      }
    });
  }
}
