package com.huobi.client.model.event;

import java.util.List;

import com.huobi.client.model.DepthEntry;

public class MarketDepthMBPEvent {

  private Long seqNum;

  private Long prevSeqNum;

  private List<DepthEntry> bids;

  private List<DepthEntry> asks;

  public Long getSeqNum() {
    return seqNum;
  }

  public void setSeqNum(Long seqNum) {
    this.seqNum = seqNum;
  }

  public Long getPrevSeqNum() {
    return prevSeqNum;
  }

  public void setPrevSeqNum(Long prevSeqNum) {
    this.prevSeqNum = prevSeqNum;
  }

  public List<DepthEntry> getBids() {
    return bids;
  }

  public void setBids(List<DepthEntry> bids) {
    this.bids = bids;
  }

  public List<DepthEntry> getAsks() {
    return asks;
  }

  public void setAsks(List<DepthEntry> asks) {
    this.asks = asks;
  }
}
