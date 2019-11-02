package com.huobi.client.examples;

import com.alibaba.fastjson.JSON;

import com.huobi.client.SubscriptionClient;

public class SubscribeTradeEvent {


  public static void main(String[] args) {

    String symbol = "htusdt";
    SubscriptionClient subscriptionClient = SubscriptionClient.create();
    subscriptionClient.subscribeTradeEvent(symbol, tradeEvent -> {

      System.out.println("------------Subscribe Trade Event-------------");
      tradeEvent.getTradeList().forEach(trade -> {
        System.out.println(JSON.toJSONString(trade));
        System.out.println("id:" + trade.getTradeId() + " price:" + trade.getPrice() + " amount:" + trade.getAmount() + " direction:" + trade.getDirection());
      });
    });

    subscriptionClient.requestTradeEvent(symbol, tradeEvent -> {

      System.out.println("------------Request Trade Event-------------");
      tradeEvent.getTradeList().forEach(trade -> {
        System.out.println(JSON.toJSONString(trade));
        System.out.println("id:" + trade.getTradeId() + " price:" + trade.getPrice() + " amount:" + trade.getAmount() + " direction:" + trade.getDirection());
      });

    });


  }

}
