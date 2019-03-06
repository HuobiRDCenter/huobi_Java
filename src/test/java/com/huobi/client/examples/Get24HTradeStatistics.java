package com.huobi.client.examples;

import com.huobi.client.AsyncRequestClient;
import com.huobi.client.RequestOptions;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.TradeStatistics;

public class Get24HTradeStatistics {
  public static void main(String[] args) {
    RequestOptions options = new RequestOptions();
    options.setUrl("https://www.xxx.yyy/");
    // Synchronization mode
    SyncRequestClient syncRequestClient = SyncRequestClient.create("","",options);
    TradeStatistics statistics = syncRequestClient.get24HTradeStatistics("btcusdt");
    System.out.println("---- Statistics ----");
    System.out.println("Timestamp: " + statistics.getTimestamp());
    System.out.println("High: " + statistics.getHigh());
    System.out.println("Low: " + statistics.getLow());
    System.out.println("Open: " + statistics.getOpen());
    System.out.println("Close: " + statistics.getClose());
    System.out.println("Volume: " + statistics.getVolume());
    System.out.println();
    // Asynchronization mode
    AsyncRequestClient asyncRequestClient = AsyncRequestClient.create("","",options);
    asyncRequestClient.get24HTradeStatistics("btcusdt", (statisticsResult) -> {
      if (statisticsResult.succeeded()) {
        System.out.println("---- Statistics ----");
        System.out.println("Timestamp: " + statisticsResult.getData().getTimestamp());
        System.out.println("High: " + statisticsResult.getData().getHigh());
        System.out.println("Low: " + statisticsResult.getData().getLow());
        System.out.println("Open: " + statisticsResult.getData().getOpen());
        System.out.println("Close: " + statisticsResult.getData().getClose());
        System.out.println("Volume: " + statisticsResult.getData().getVolume());
        System.out.println();
      }
    });
  }
}
