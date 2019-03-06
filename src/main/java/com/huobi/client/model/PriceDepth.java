package com.huobi.client.model;

import java.util.List;

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
}
