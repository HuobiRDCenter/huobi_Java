package com.huobi.client.examples;

import java.util.List;

import com.alibaba.fastjson.JSON;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.impl.AccountsInfoMap;
import com.huobi.client.model.Account;
import com.huobi.client.model.User;

public class GetAccounts {

  public static void main(String[] args) {

    SyncRequestClient client = SyncRequestClient.create(Constants.API_KEY, Constants.SECRET_KEY);

    User user = AccountsInfoMap.getUser(Constants.API_KEY);
    System.out.println("user : " + JSON.toJSONString(user));

    List<Account> accountList = client.getAccountBalance();
    accountList.forEach(account -> {
      System.out.println("----- account type:" + account.getType() + " -----");
      account.getBalances().forEach(balance -> {
        if (balance.getCurrency().equals("usdt")) {
          System.out.println(" account balance: " + JSON.toJSONString(balance));
        }

      });


    });


  }

}
