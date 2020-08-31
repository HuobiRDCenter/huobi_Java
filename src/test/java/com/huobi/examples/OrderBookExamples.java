package com.huobi.examples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.huobi.client.MarketClient;
import com.huobi.client.req.market.SubMbpIncrementalUpdateRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.WebSocketConstants;
import com.huobi.model.market.MbpIncrementalUpdateEvent;
import com.huobi.model.market.PriceLevel;
import com.huobi.utils.WebSocketConnection;

public class OrderBookExamples {

  private volatile LinkedBlockingQueue<MbpIncrementalUpdateEvent> UPDATE_QUEUE = new LinkedBlockingQueue<>();

  private volatile Map<BigDecimal, BigDecimal> BIDS_MAP = new TreeMap<>(Comparator.reverseOrder());

  private volatile Map<BigDecimal, BigDecimal> ASKS_MAP = new TreeMap<>();

  private String symbol;

  private MarketClient marketClient = MarketClient.create(new HuobiOptions());

  private Long lastSeqNum = -1L;

  private WebSocketConnection connection;

  private boolean isFirst = true;

  public OrderBookExamples(String symbol) {
    this.symbol = symbol;
    initialize();
  }


  private void initialize() {
    startSubscribeMbpEvent();
  }

  private void startSubscribeMbpEvent() {
    SubMbpIncrementalUpdateRequest request = SubMbpIncrementalUpdateRequest.builder().symbol(symbol).build();
    connection = marketClient.subMbpIncrementalUpdate(request, event -> {

      if (isFirst) {
        sendReqMessage();
        isFirst = false;
      }

      incrementUpdateTask(event);
    });
  }

  private void incrementUpdateTask(MbpIncrementalUpdateEvent event) {

    if (WebSocketConstants.ACTION_REP.equals(event.getAction())) {
      // 全量请求对齐
      Long snapshotSeqNum = event.getSeqNum();

      List<MbpIncrementalUpdateEvent> preUpdateList = new ArrayList<>(UPDATE_QUEUE.size());
      // 把队列里的数据拿出来
      UPDATE_QUEUE.drainTo(preUpdateList);

      boolean isFinish = false;
      int index = 0;
      for (MbpIncrementalUpdateEvent eve : preUpdateList) {
        index++;

        Long preSeqNum = eve.getPrevSeqNum();

        System.out.println("event:::: preSeqNum:" + preSeqNum + "  seqNum:" + eve.getSeqNum() + "  snapshot:" + snapshotSeqNum);

        // 匹配成功
        if (preSeqNum.compareTo(snapshotSeqNum) == 0) {
          event.getBids().forEach(priceLevel -> {
            BIDS_MAP.put(priceLevel.getPrice(), priceLevel.getAmount());
          });

          event.getAsks().forEach(priceLevel -> {
            ASKS_MAP.put(priceLevel.getPrice(), priceLevel.getAmount());
          });

          isFinish = true;
          lastSeqNum = snapshotSeqNum;

          incrementUpdate(eve);
          System.out.println("....compare finish....");
          break;
        }

        // pre < seq 则忽略
        if (preSeqNum.compareTo(snapshotSeqNum) < 0) {
          System.out.println(" ignore message: preSeqNum:" + preSeqNum + "  seqNum:" + eve.getSeqNum() + "  snapshot:" + snapshotSeqNum);
          continue;
        }

        // 如果出现pre > seq 则认为是漏消息了
        if (preSeqNum.compareTo(snapshotSeqNum) > 0) {
          System.out.println("find incr message preSeqNum > snapshot seqNum....    message:" + preSeqNum + "   snapshot:" + snapshotSeqNum);
          break;
        }

      }

      // 没有结束匹配，则出去
      if (!isFinish) {
        sendReqMessage();
        return;
      }

      for (int i = index; i < preUpdateList.size(); i++) {
        MbpIncrementalUpdateEvent eve = preUpdateList.get(i);
        incrementUpdate(eve);
      }


    } else {
      // 如果上一个lastSeqNum小于0，则认为是未初始化，先放到队列里
      if (lastSeqNum < 0) {
        UPDATE_QUEUE.add(event);
        return;
      }

      // 增量更新
      incrementUpdate(event);
    }

  }

  private void incrementUpdate(MbpIncrementalUpdateEvent event) {

    // 当前消息的pre 大于最后一次的seq 说明漏消息了。
    if (event.getPrevSeqNum() > lastSeqNum) {
      sendReqMessage();
      System.out.println(" miss message ::: message:" + event.getPrevSeqNum() + "   snapshot:" + lastSeqNum);
      lastSeqNum = -1L;
      return;
    }

    if (event.getPrevSeqNum() < lastSeqNum) {
      return;
    }

    lastSeqNum = event.getSeqNum();

    if (event.getAsks() != null && event.getAsks().size() > 0) {
      for (PriceLevel level : event.getAsks()) {
        if (level.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
          ASKS_MAP.remove(level.getPrice());
        } else {
          ASKS_MAP.put(level.getPrice(), level.getAmount());
        }
      }
    }

    if (event.getBids() != null && event.getBids().size() > 0) {
      for (PriceLevel level : event.getBids()) {
        if (level.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
          BIDS_MAP.remove(level.getPrice());
        } else {
          BIDS_MAP.put(level.getPrice(), level.getAmount());
        }
      }
    }

  }

  private void sendReqMessage() {
    marketClient.reqMbpIncrementalUpdate(SubMbpIncrementalUpdateRequest.builder().symbol(symbol).build(), connection);
  }

  public MbpIncrementalUpdateEvent getDepth(){

    Iterator<Entry<BigDecimal,BigDecimal>> askIterator = ASKS_MAP.entrySet().iterator();
    List<PriceLevel> askLevelList = new ArrayList<>();
    while (askIterator.hasNext()) {
      Entry<BigDecimal,BigDecimal> entry = askIterator.next();
      BigDecimal price = entry.getKey();
      BigDecimal amount = entry.getValue();
      askLevelList.add(PriceLevel.builder().amount(amount).price(price).build());
    }

    Iterator<Entry<BigDecimal,BigDecimal>> bidIterator = BIDS_MAP.entrySet().iterator();
    List<PriceLevel> bidLevelList = new ArrayList<>();
    while (bidIterator.hasNext()) {
      Entry<BigDecimal,BigDecimal> entry = bidIterator.next();
      BigDecimal price = entry.getKey();
      BigDecimal amount = entry.getValue();
      bidLevelList.add(PriceLevel.builder().amount(amount).price(price).build());
    }

    return MbpIncrementalUpdateEvent.builder()
        .asks(askLevelList)
        .bids(bidLevelList)
        .build();
  }


  public static void main(String[] args) {

    String symbol = "btcusdt";
    OrderBookExamples examples = new OrderBookExamples(symbol);
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    scheduledExecutorService.scheduleAtFixedRate(() -> {
      try {
        MbpIncrementalUpdateEvent event = examples.getDepth();
        PriceLevel askLevel = event.getAsks().get(0);
        PriceLevel bidLevel = event.getBids().get(0);

        System.out.println("----------------------------");
        System.out.println("ask1:  "+askLevel.getPrice().toPlainString()+" ------ "+ askLevel.getAmount().toPlainString());
        System.out.println("bid1:  "+bidLevel.getPrice().toPlainString()+" ------ "+ bidLevel.getAmount().toPlainString());
        System.out.println("----------------------------");
      } catch (Exception e) {
        e.printStackTrace();
      }

    }, 1000L, 1000L, TimeUnit.MILLISECONDS);
  }


}
