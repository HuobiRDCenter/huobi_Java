package com.huobi.client.examples;

import com.huobi.client.AsyncRequestClient;
import com.huobi.client.RequestOptions;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.ExchangeInfo;
import com.huobi.client.model.Symbol;

public class GetExchangeInfo {
  public static void main(String[] args) {
    // Synchronization mode
    SyncRequestClient syncRequestClient = SyncRequestClient.create();
    ExchangeInfo exchangeInfo = syncRequestClient.getExchangeInfo();

    System.out.println("---- Supported symbols ----");
    for (Symbol symbol : exchangeInfo.getSymbolList()) {
      System.out.println(symbol.getSymbol());
    }
    System.out.println("---- Supported currencies ----");
    for (String currency : exchangeInfo.getCurrencies()) {
      System.out.println(currency);
    }
    System.out.println();
    // Asynchronization mode
    AsyncRequestClient asyncRequestClient = AsyncRequestClient.create();
    asyncRequestClient.getExchangeInfo((exchangeInfoResult) -> {
      if (exchangeInfoResult.succeeded()) {
        System.out.println("---- Supported symbols ----");
        for (Symbol symbol : exchangeInfoResult.getData().getSymbolList()) {
          System.out.println(symbol.getSymbol());
        }
        System.out.println("---- Supported currencies ----");
        for (String currency : exchangeInfoResult.getData().getCurrencies()) {
          System.out.println(currency);
        }
      }
    });
  }
}
