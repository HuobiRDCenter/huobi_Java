package com.huobi.client.examples;

import com.huobi.client.AsyncRequestClient;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.Trade;
import java.util.List;

public class GetHistoricalTrade {
  public static void main(String[] args) {
    // Synchronization mode
    SyncRequestClient syncRequestClient = SyncRequestClient.create();
    List<Trade> tradeList = syncRequestClient.getHistoricalTrade(
        "btcusdt", 5);
    System.out.println("---- Historical trade for btcusdt ----");
    for (Trade trade : tradeList) {
      System.out.println();
      System.out.println("Trade at: " + trade.getTimestamp());
      System.out.println("Id: " + trade.getTradeId());
      System.out.println("Price: " + trade.getPrice());
      System.out.println("Amount: " + trade.getAmount());
      System.out.println("Direction: " + trade.getDirection().toString());
    }
    System.out.println();
    // Asynchronization mode
    AsyncRequestClient asyncRequestClient = AsyncRequestClient.create();
    asyncRequestClient.getHistoricalTrade("btcusdt", 5, (tradeResult) -> {
      if (tradeResult.succeeded()) {
        System.out.println("---- Historical trade for btcusdt ----");
        for (Trade trade : tradeResult.getData()) {
          System.out.println();
          System.out.println("Trade at: " + trade.getTimestamp());
          System.out.println("Id: " + trade.getTradeId());
          System.out.println("Price: " + trade.getPrice());
          System.out.println("Amount: " + trade.getAmount());
          System.out.println("Direction: " + trade.getDirection().toString());
        }
      }
    });
  }
}
