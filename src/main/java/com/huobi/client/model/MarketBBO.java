package com.huobi.client.model;

import java.math.BigDecimal;

import com.huobi.client.impl.utils.JsonWrapper;

/**
 * The market bbo.
 */

public class MarketBBO {

  private String symbol;

  private Long quoteTime;

  private BigDecimal bid;

  private BigDecimal bidSize;

  private BigDecimal ask;

  private BigDecimal askSize;


  public static MarketBBO parse(JsonWrapper jsonWrapper) {
    MarketBBO bbo = new MarketBBO();
    bbo.setSymbol(jsonWrapper.getStringOrDefault("symbol",null));
    bbo.setQuoteTime(jsonWrapper.getLong("quoteTime"));
    bbo.setBid(jsonWrapper.getBigDecimalOrDefault("bid",null));
    bbo.setBidSize(jsonWrapper.getBigDecimalOrDefault("bidSize",null));
    bbo.setAsk(jsonWrapper.getBigDecimalOrDefault("ask",null));
    bbo.setAskSize(jsonWrapper.getBigDecimalOrDefault("askSize",null));
    return bbo;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Long getQuoteTime() {
    return quoteTime;
  }

  public void setQuoteTime(Long quoteTime) {
    this.quoteTime = quoteTime;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public void setBid(BigDecimal bid) {
    this.bid = bid;
  }

  public BigDecimal getBidSize() {
    return bidSize;
  }

  public void setBidSize(BigDecimal bidSize) {
    this.bidSize = bidSize;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public void setAsk(BigDecimal ask) {
    this.ask = ask;
  }

  public BigDecimal getAskSize() {
    return askSize;
  }

  public void setAskSize(BigDecimal askSize) {
    this.askSize = askSize;
  }
}
