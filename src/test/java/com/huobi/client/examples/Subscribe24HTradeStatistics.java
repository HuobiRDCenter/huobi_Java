package com.huobi.client.examples;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.SubscriptionOptions;

public class Subscribe24HTradeStatistics {

  public static void main(String[] args) {
    // Subscribe 24HTradeStatistics with custom server
    SubscriptionOptions options = new SubscriptionOptions();
    options.setUri("wss://api.huobi.pro");
    SubscriptionClient subscriptionClient = SubscriptionClient.create("", "", options);
    subscriptionClient.subscribe24HTradeStatisticsEvent("btcusdt", (statisticsEvent) -> {
      System.out.println();
      System.out.println("Timestamp: " + statisticsEvent.getData().getTimestamp());
      System.out.println("High: " + statisticsEvent.getData().getHigh());
      System.out.println("Low: " + statisticsEvent.getData().getLow());
      System.out.println("Open: " + statisticsEvent.getData().getOpen());
      System.out.println("Close: " + statisticsEvent.getData().getClose());
      System.out.println("Volume: " + statisticsEvent.getData().getVolume());
    });
  }
}
