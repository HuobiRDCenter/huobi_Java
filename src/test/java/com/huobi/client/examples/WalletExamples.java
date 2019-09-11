package com.huobi.client.examples;

import java.util.List;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.model.Withdraw;


public class WalletExamples {

  public static void main(String[] args) {
    String currency = "usdt";

    SyncRequestClient syncRequestClient = SyncRequestClient.create(Constants.API_KEY,Constants.SECRET_KEY);
    List<Withdraw> list = syncRequestClient.getWithdrawHistory(currency,0,10);
    list.forEach(withdraw -> {
      System.out.println("Withdraw History:"+withdraw.toString());
    });
  }

}
