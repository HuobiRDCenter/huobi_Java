package com.huobi.client.examples;

import com.alibaba.fastjson.JSON;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.model.enums.MBPLevelEnums;

public class SubscribeMarketDepthMBP {


  public static void main(String[] args) {

    String symbol = "htusdt";
    SubscriptionClient subscriptionClient = SubscriptionClient.create();

    subscriptionClient.subscribeMarketDepthMBP(symbol, MBPLevelEnums.LEVEL150, (marketDepthMBPEvent) -> {

      System.out.println("callback:" + JSON.toJSONString(marketDepthMBPEvent));
    });

    subscriptionClient.requestMarketDepthMBP(symbol, MBPLevelEnums.LEVEL150, (marketDepthMBPEvent) -> {
      System.out.println("callback:" + JSON.toJSONString(marketDepthMBPEvent));
    });


  }

}
