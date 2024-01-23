package com.huobi.examples;

import java.util.List;

import com.huobi.client.GenericClient;
import com.huobi.client.req.generic.ChainRequest;
import com.huobi.client.req.generic.CurrencyChainsRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.model.generic.*;

public class GenericClientExample {

  public static void main(String[] args) {
    GenericClient genericService = GenericClient.create(HuobiOptions.builder().build());


    String systemStatusJSONString = genericService.getSystemStatus();
    System.out.println(systemStatusJSONString);

    System.out.println("----------------------------------------------------");

    MarketStatus marketStatus = genericService.getMarketStatus();
    System.out.println("market status :: "+marketStatus.getMarketStatus());


    System.out.println("----------------------------------------------------");

    Long serverTime = genericService.getTimestamp();
    System.out.println("server time:" + serverTime);

    List<CurrencyChain> currencyChainList = genericService.getCurrencyChains(CurrencyChainsRequest.builder()
        .currency("usdt")
        .build());

    currencyChainList.forEach(currencyChain -> {
      System.out.println("***************************************");
      System.out.println("currrency chain:"+currencyChain);
      currencyChain.getChains().forEach(chain -> {
        System.out.println("chain:::"+chain.toString());
      });
    });

    System.out.println("----------------------------------------------------");

    Long timeStamp = genericService.getTimestamp();
    System.out.println(timeStamp);

    System.out.println("----------------------------------------------------");

    List<SymbolV2> symbolsV2 = genericService.getSymbolsV2(null);
    System.out.println(symbolsV2);

    System.out.println("----------------------------------------------------");

    List<Currency> currencyV1 = genericService.getCurrencyV1(null);
    System.out.println(currencyV1);

    System.out.println("----------------------------------------------------");

    List<CurrencyV2> currencyV2 = genericService.getCurrencyV2(null);
    System.out.println(currencyV2);

    System.out.println("----------------------------------------------------");

    List<SymbolV1> symbolsV1 = genericService.getSymbolsV1(null);
    System.out.println(symbolsV1);

    System.out.println("----------------------------------------------------");

    List<MarketSymbol> marketSymbol = genericService.getMarketSymbol(null, null);
    System.out.println(marketSymbol);

    System.out.println("----------------------------------------------------");

    ChainRequest request = ChainRequest.builder().build();
    List<ChainV1> chain = genericService.getChain(request);
    System.out.println(chain);

  }

}
