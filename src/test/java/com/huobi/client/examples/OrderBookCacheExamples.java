package com.huobi.client.examples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.impl.utils.InternalUtils;
import com.huobi.client.model.DepthEntry;
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.enums.MBPLevelEnums;
import com.huobi.client.model.event.MarketDepthMBPEvent;

public class OrderBookCacheExamples {

  private static final Logger log = LoggerFactory.getLogger(OrderBookCacheExamples.class);

  public static void main(String[] args) {

    String symbol = "htusdt";
    OrderBookCacheExamples examples = new OrderBookCacheExamples(symbol);

    while (true) {
      PriceDepth priceDepth = examples.getDepth();
      if (priceDepth == null) {
        InternalUtils.await(1000);
        continue;
      }

      System.out.println(
          " ask1: price:" + priceDepth.getAsks().get(0).getPrice().toPlainString() + " amount:" + priceDepth.getAsks().get(0).getAmount()
              .toPlainString() + "   size:"+priceDepth.getAsks().size());
      System.out.println(
          " bid1: price:" + priceDepth.getBids().get(0).getPrice().toPlainString() + " amount:" + priceDepth.getBids().get(0).getAmount()
              .toPlainString() + "   size:"+priceDepth.getBids().size());
      System.out.println("------------------------------------------------");

      InternalUtils.await(500);
    }


  }


  private String symbol;

  private SubscriptionClient subscriptionClient = SubscriptionClient.create();

  private volatile Long lastSeqNum = -1L;

  private volatile LinkedBlockingQueue<MarketDepthMBPEvent> UPDATE_QUEUE = new LinkedBlockingQueue<>();

  private volatile Map<BigDecimal, BigDecimal> BIDS_MAP = new TreeMap<>(Comparator.reverseOrder());

  private volatile Map<BigDecimal, BigDecimal> ASKS_MAP = new TreeMap<>();

  private ExecutorService executorService = Executors.newFixedThreadPool(1);

  private PriceDepth depth;

  public OrderBookCacheExamples(String symbol) {
    this.symbol = symbol;

    initialize();
  }


  private void initialize() {

    // 1. 订阅增量深度mbp主题
    startSubscribeDepthMBPEvent();
    // 2. 请求全量数据
    initializeDepthCache();

  }


  public void startSubscribeDepthMBPEvent() {
    subscriptionClient.subscribeMarketDepthMBP(symbol, MBPLevelEnums.LEVEL150, marketDepthMBPEvent -> {
      // 如果上一个lastSeqNum小于0，则认为是未初始化，先放到队列里
      if (lastSeqNum < 0) {
        UPDATE_QUEUE.add(marketDepthMBPEvent);
      } else {
        incrementUpdateTask(marketDepthMBPEvent);
      }
    });
  }

  public void initializeDepthCache() {


    executorService.execute(new Runnable() {
      @Override
      public void run() {
        subscriptionClient.requestMarketDepthMBP(symbol, MBPLevelEnums.LEVEL150, marketDepthMBPEvent -> {

          ASKS_MAP.clear();
          List<DepthEntry> askList = marketDepthMBPEvent.getAsks();
          if (askList != null && askList.size() > 0) {
            askList.forEach(depthEntry -> {
              ASKS_MAP.put(depthEntry.getPrice(), depthEntry.getAmount());
            });
          }

          BIDS_MAP.clear();
          List<DepthEntry> bidList = marketDepthMBPEvent.getBids();
          if (bidList != null && bidList.size() > 0) {
            bidList.forEach(depthEntry -> {
              BIDS_MAP.put(depthEntry.getPrice(), depthEntry.getAmount());
            });
          }

          lastSeqNum = marketDepthMBPEvent.getSeqNum();
        });
      }
    });


  }

  public void incrementUpdateTask(MarketDepthMBPEvent mdevent) {

    List<MarketDepthMBPEvent> eventList = new ArrayList<>();
    while (true) {
      MarketDepthMBPEvent marketDepthMBPEvent = UPDATE_QUEUE.poll();
      if (marketDepthMBPEvent == null) {
        break;
      }
      eventList.add(marketDepthMBPEvent);
    }


    eventList.add(mdevent);

    if (eventList.size() < 0) {
      return;
    }



    for (MarketDepthMBPEvent event : eventList) {

      if (event.getPrevSeqNum() < lastSeqNum) {
        log.debug(" skip seqNum ! lastSeqNum:"+lastSeqNum+"  snapshot seqNum:"+event.getPrevSeqNum());
        continue;
      }

      if (event.getPrevSeqNum() > lastSeqNum) {
        log.debug(" find error seqNum ! lastSeqNum:"+lastSeqNum+"  snapshot seqNum:"+event.getPrevSeqNum());
        lastSeqNum = -1L;
        initializeDepthCache();
        break;
      }

      lastSeqNum = event.getSeqNum();

      List<DepthEntry> askList = event.getAsks();
      if (askList != null && askList.size() > 0) {

        askList.forEach(depthEntry -> {
          BigDecimal price = depthEntry.getPrice();
          BigDecimal amount = depthEntry.getAmount();
          if (amount.equals(BigDecimal.ZERO)) {
            ASKS_MAP.remove(price);
          } else {
            ASKS_MAP.put(price, amount);
          }
        });

      }

      List<DepthEntry> bidList = event.getBids();
      if (bidList != null && bidList.size() > 0) {

        bidList.forEach(depthEntry -> {
          BigDecimal price = depthEntry.getPrice();
          BigDecimal amount = depthEntry.getAmount();
          if (amount.equals(BigDecimal.ZERO)) {
            BIDS_MAP.remove(price);
          } else {
            BIDS_MAP.put(price, amount);
          }
        });
      }

      depth = buildPriceDepth();
    }


  }


  public PriceDepth buildPriceDepth() {
    if (lastSeqNum < 0) {
      return null;
    }

//    System.out.println("ask ：" + JSON.toJSONString(ASKS_MAP));
//    System.out.println("bid ：" + JSON.toJSONString(BIDS_MAP));
    List<DepthEntry> askList = new ArrayList<>(ASKS_MAP.size());
    for (Entry<BigDecimal, BigDecimal> entry : ASKS_MAP.entrySet()) {
      DepthEntry depthEntry = new DepthEntry();
      depthEntry.setPrice(entry.getKey());
      depthEntry.setAmount(entry.getValue());
      askList.add(depthEntry);
    }

    List<DepthEntry> bidList = new ArrayList<>(BIDS_MAP.size());
    for (Entry<BigDecimal, BigDecimal> entry : BIDS_MAP.entrySet()) {
      DepthEntry depthEntry = new DepthEntry();
      depthEntry.setPrice(entry.getKey());
      depthEntry.setAmount(entry.getValue());
      bidList.add(depthEntry);
    }

    PriceDepth depth = new PriceDepth();
    depth.setAsks(askList);
    depth.setBids(bidList);
    return depth;
  }

  public PriceDepth getDepth() {
    return depth;
  }
}
