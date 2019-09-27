package com.huobi.client.examples;

import com.huobi.client.SubscriptionClient;

public class SubscribeMarketBBO {

  public static void main(String[] args) {

    String symbol = "btcusdt";
    SubscriptionClient client = SubscriptionClient.create();

    client.subscribeMarketBBOEvent(symbol,(marketBBOEvent)->{
      System.out.println(marketBBOEvent.toString());
    });


  }

}
