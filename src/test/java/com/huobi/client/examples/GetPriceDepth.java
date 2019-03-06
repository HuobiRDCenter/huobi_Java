package com.huobi.client.examples;

import com.huobi.client.AsyncRequestClient;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.DepthEntry;
import com.huobi.client.model.PriceDepth;

public class GetPriceDepth {
  public static void main(String[] args) {
    // Synchronization mode
    SyncRequestClient syncRequestClient = SyncRequestClient.create();
    PriceDepth depth = syncRequestClient.getPriceDepth("btcusdt", 5);

    int i = 0;
    System.out.println("---- Top 5 bids ----");
    for (DepthEntry entry : depth.getBids()) {
      System.out.println(i++ + ": price: " + entry.getPrice() + ", amount: " + entry.getAmount());
    }
    i = 0;
    System.out.println("---- Top 5 asks ----");
    for (DepthEntry entry : depth.getAsks()) {
      System.out.println(i + ": price: " + entry.getPrice() + ", amount: " + entry.getAmount());
    }
    System.out.println();

    // Asynchronization mode
    AsyncRequestClient asyncRequestClient = AsyncRequestClient.create();
    asyncRequestClient.getPriceDepth("btcusdt", 5, (depthResult) -> {
      int j = 0;
      System.out.println("---- Top 5 bids ----");
      for (DepthEntry entry : depthResult.getData().getBids()) {
        System.out.println(j++ + ": price: " + entry.getPrice() + ", amount: " + entry.getAmount());
      }
      j = 0;
      System.out.println("---- Top 5 asks ----");
      for (DepthEntry entry : depthResult.getData().getAsks()) {
        System.out.println(j + ": price: " + entry.getPrice() + ", amount: " + entry.getAmount());
      }
    });
  }
}
