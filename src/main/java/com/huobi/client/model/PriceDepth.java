package com.huobi.client.model;

import java.util.LinkedList;
import java.util.List;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.impl.utils.TimeService;

/**
 * The price depth information.
 */
public class PriceDepth {

  private long timestamp;
  private List<DepthEntry> bids;
  private List<DepthEntry> asks;

  /**
   * Get the UNIX formatted timestamp in UTC.
   *
   * @return The timestamp.
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * Get the list of the bid depth.
   *
   * @return The price depth list, see {@link DepthEntry}
   */
  public List<DepthEntry> getBids() {
    return bids;
  }

  /**
   * Get the list of the ask depth.
   *
   * @return The price depth list, see {@link DepthEntry}
   */
  public List<DepthEntry> getAsks() {
    return asks;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public void setBids(List<DepthEntry> bids) {
    this.bids = bids;
  }

  public void setAsks(List<DepthEntry> asks) {
    this.asks = asks;
  }


  public static RestApiJsonParser<PriceDepth> getParser(int size) {
    return (jsonWrapper -> {
      JsonWrapper tick = jsonWrapper.getJsonObject("tick");
      PriceDepth dp = new PriceDepth();
      long ts = TimeService.convertCSTInMillisecondToUTC(tick.getLong("ts"));
      JsonWrapperArray bids = tick.getJsonArray("bids");
      JsonWrapperArray asks = tick.getJsonArray("asks");
      List<DepthEntry> bidList = new LinkedList<>();
      List<DepthEntry> askList = new LinkedList<>();
      for (int i = 0; i < size; i++) {
        bidList.add(DepthEntry.parse(bids.getArrayAt(i)));
      }
      for (int i = 0; i < size; i++) {
        askList.add(DepthEntry.parse(asks.getArrayAt(i)));
      }
      dp.setBids(bidList);
      dp.setAsks(askList);
      dp.setTimestamp(ts);
      return dp;
    });
  }
}
