package com.huobi.client.examples;

import com.alibaba.fastjson.JSON;
import com.huobi.client.SubscriptionClient;
import com.huobi.client.SubscriptionOptions;
import org.apache.commons.lang.StringUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class SubscribeTradeEvent {


  public static void main(String[] args) {

    String symbol = "htusdt";
    String symbol2 = "btcusdt";
    String symbol3 = "ltcusdt";
    String symbol4 = "etcusdt";
    String symbol5 = "ethusdt";
    String symbol6 = "trxusdt";
    String symbol7 = "xrpusdt";
    String symbol8 = "bsvusdt";
    String symbol9 = "bchusdt";
    String symbol10 = "eosusdt";
    String symbol11= "dashusdt";
    String symbol12 = "ckbusdt";
    SubscriptionOptions options=new SubscriptionOptions();
    String proxyHost="proxy.huobidev.com";
    int proxyPort=3129;
    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
    options.setProxy(proxy);
    SubscriptionClient subscriptionClient = SubscriptionClient.create("","",options);
    List<String> symbols=new ArrayList<>();
    symbols.add(symbol);
    symbols.add(symbol2);
    symbols.add(symbol3);
    symbols.add(symbol4);
    symbols.add(symbol5);
    symbols.add(symbol6);
    symbols.add(symbol7);
    symbols.add(symbol8);
    symbols.add(symbol9);
    symbols.add(symbol10);
    symbols.add(symbol11);
    String join = StringUtils.join(symbols, ',');
    subscriptionClient.subscribeTradeEvent(join, tradeEvent -> {

      System.out.println("------------Subscribe Trade Event-------------symbol:"+tradeEvent.getSymbol());
      tradeEvent.getTradeList().forEach(trade -> {
        System.out.println(JSON.toJSONString(trade));
        System.out.println("id:" + trade.getTradeId() + " price:" + trade.getPrice() + " amount:" + trade.getAmount() + " direction:" + trade.getDirection());
      });
    });

    subscriptionClient.subscribeTradeEvent(symbol2, tradeEvent -> {

      System.out.println("------------Subscribe Trade Event-------------symbol:"+tradeEvent.getSymbol());
      tradeEvent.getTradeList().forEach(trade -> {
        System.out.println(JSON.toJSONString(trade));
        System.out.println("id:" + trade.getTradeId() + " price:" + trade.getPrice() + " amount:" + trade.getAmount() + " direction:" + trade.getDirection());
      });
    });

//    subscriptionClient.requestTradeEvent(symbol, tradeEvent -> {
//
//      System.out.println("------------Request Trade Event-------------");
//      tradeEvent.getTradeList().forEach(trade -> {
//        System.out.println(JSON.toJSONString(trade));
//        System.out.println("id:" + trade.getTradeId() + " price:" + trade.getPrice() + " amount:" + trade.getAmount() + " direction:" + trade.getDirection());
//      });
//
//    });


  }

}
