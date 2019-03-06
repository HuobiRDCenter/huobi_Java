package com.huobi.client.examples;

import com.huobi.client.AsyncRequestClient;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.CandlestickInterval;
import java.util.List;

public class GetCandlestickData {
  public static void main(String[] args) {
    // Synchronization mode
    SyncRequestClient syncRequestClient = SyncRequestClient.create();
    List<Candlestick> candlestickList = syncRequestClient.getLatestCandlestick(
        "btcusdt", CandlestickInterval.MIN1, 1);
    System.out.println("---- 1 min candlestick for btcusdt ----");
    for (Candlestick candlestick : candlestickList) {
      System.out.println();
      System.out.println("Timestamp: " + candlestick.getTimestamp());
      System.out.println("High: " + candlestick.getHigh());
      System.out.println("Low: " + candlestick.getLow());
      System.out.println("Open: " + candlestick.getOpen());
      System.out.println("Close: " + candlestick.getClose());
      System.out.println("Volume: " + candlestick.getVolume());
    }
    System.out.println();
    // Asynchronization mode
    AsyncRequestClient asyncRequestClient = AsyncRequestClient.create();
    asyncRequestClient.getLatestCandlestick("btcusdt", CandlestickInterval.MIN1, 1, (candlestickResult) -> {
      if (candlestickResult.succeeded()) {
        System.out.println("---- 1 min candlestick for btcusdt ----");
        for (Candlestick candlestick : candlestickResult.getData()) {
          System.out.println();
          System.out.println("Timestamp: " + candlestick.getTimestamp());
          System.out.println("High: " + candlestick.getHigh());
          System.out.println("Low: " + candlestick.getLow());
          System.out.println("Open: " + candlestick.getOpen());
          System.out.println("Close: " + candlestick.getClose());
          System.out.println("Volume: " + candlestick.getVolume());
        }
      }
    });
  }
}
