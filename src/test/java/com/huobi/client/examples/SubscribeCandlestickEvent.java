package com.huobi.client.examples;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.SubscriptionOptions;
import com.huobi.client.model.enums.CandlestickInterval;

public class SubscribeCandlestickEvent {
  public static void main(String[] args) {
    SubscriptionClient subscriptionClient = SubscriptionClient.create();
    subscriptionClient.subscribeCandlestickEvent("btcusdt", CandlestickInterval.MIN15, (candlestickEvent) -> {
      System.out.println();
      System.out.println("Timestamp: " + candlestickEvent.getData().getTimestamp());
      System.out.println("High: " + candlestickEvent.getData().getHigh());
      System.out.println("Low: " + candlestickEvent.getData().getLow());
      System.out.println("Open: " + candlestickEvent.getData().getOpen());
      System.out.println("Close: " + candlestickEvent.getData().getClose());
      System.out.println("Volume: " + candlestickEvent.getData().getVolume());
    });
  }
}
