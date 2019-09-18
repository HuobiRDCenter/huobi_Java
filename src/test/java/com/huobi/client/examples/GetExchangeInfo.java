package com.huobi.client.examples;

import java.util.List;

import com.huobi.client.AsyncRequestClient;
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
    List<Symbol> symbolList = syncRequestClient.getSymbols();
    symbolList.forEach(symbol -> {
      System.out.println(symbol.toString());
    });

    List<String> currencyList = syncRequestClient.getCurrencies();
    currencyList.forEach(currency ->{
      System.out.println(currency);
    });

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


    asyncRequestClient.getSymbols((symbolAsyncResult)->{
      List<Symbol> symbols = symbolAsyncResult.getData();
      symbols.forEach(symbol -> {
        System.out.println(symbol.toString());
      });
    });

    asyncRequestClient.getCurrencies((symbolAsyncResult)->{
      List<String> currencies = symbolAsyncResult.getData();
      currencies.forEach(currency -> {
        System.out.println(currency);
      });
    });
  }
}
