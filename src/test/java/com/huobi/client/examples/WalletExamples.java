package com.huobi.client.examples;

import java.util.List;

import com.alibaba.fastjson.JSON;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.model.Account;
import com.huobi.client.model.Withdraw;
import com.huobi.client.model.enums.AccountType;


public class WalletExamples {

  public static void main(String[] args) {
    String currency = "usdt";

    SyncRequestClient syncRequestClient = SyncRequestClient.create(Constants.API_KEY,Constants.SECRET_KEY);

    List<Account> accountList = syncRequestClient.getAccountBalance();
    accountList.forEach(account -> {
      System.out.println(JSON.toJSONString(account));
    });


    Account spotAccount = syncRequestClient.getAccountBalance(AccountType.SPOT);
    System.out.println(JSON.toJSONString(spotAccount));

    Account marginAccount = syncRequestClient.getAccountBalance(AccountType.MARGIN,"xrpusdt");
    System.out.println(JSON.toJSONString(marginAccount));

    Account superMarginAccount = syncRequestClient.getAccountBalance(AccountType.SUPER_MARGIN);
    System.out.println(JSON.toJSONString(superMarginAccount));


    /**
     * Get withdraw history
     */
    List<Withdraw> list = syncRequestClient.getWithdrawHistory(currency,0,10);
    list.forEach(withdraw -> {
      System.out.println("Withdraw History:"+withdraw.toString());
    });
  }

}
