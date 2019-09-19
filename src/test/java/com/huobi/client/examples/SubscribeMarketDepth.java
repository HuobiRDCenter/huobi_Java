package com.huobi.client.examples;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.SubscriptionOptions;
import com.huobi.client.model.enums.CandlestickInterval;

public class SubscribeMarketDepth {

  public static void main(String[] args) {
    String symbol = "htusdt";
    SubscriptionClient subscriptionClient = SubscriptionClient.create();
    subscriptionClient.subscribePriceDepthEvent(symbol, (priceDepthEvent) -> {
      System.out.println("------------Subscribe Price Depth-----------------");
      System.out.println("bids 0 price: " + priceDepthEvent.getData().getBids().get(0).getPrice());
      System.out.println("bids 0 volume: " + priceDepthEvent.getData().getBids().get(0).getAmount());

    });


    subscriptionClient.requestPriceDepthEvent(symbol,(priceDepthEvent) -> {
      System.out.println("------------Request Price Depth-----------------");
      System.out.println("bids 0 price: " + priceDepthEvent.getData().getBids().get(0).getPrice() +"  size:"+priceDepthEvent.getData().getBids().size());
      System.out.println("bids 0 volume: " + priceDepthEvent.getData().getBids().get(0).getAmount()+ " size:"+priceDepthEvent.getData().getAsks().size());
    });

  }
}
