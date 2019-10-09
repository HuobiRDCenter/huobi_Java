package com.huobi.client.model;

import java.math.BigDecimal;

import lombok.ToString;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.impl.utils.TimeService;

/**
 * The best bid/ask consisting of price and amount.
 */
@ToString
public class BestQuote {

  private long timestamp;
  private BigDecimal askPrice;
  private BigDecimal askAmount;
  private BigDecimal bidPrice;
  private BigDecimal bidAmount;

  /**
   * Get the Unix formatted timestamp in UTC.
   *
   * @return The timestamp.
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * Get the best ask price.
   *
   * @return The price.
   */
  public BigDecimal getAskPrice() {
    return askPrice;
  }

  /**
   * Get the best ask amount.
   *
   * @return The amount.
   */
  public BigDecimal getAskAmount() {
    return askAmount;
  }

  /**
   * Get the best bid price.
   *
   * @return The price.
   */
  public BigDecimal getBidPrice() {
    return bidPrice;
  }

  /**
   * Get the best bid amount.
   *
   * @return The amount.
   */
  public BigDecimal getBidAmount() {
    return bidAmount;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public void setAskPrice(BigDecimal askPrice) {
    this.askPrice = askPrice;
  }

  public void setAskAmount(BigDecimal askAmount) {
    this.askAmount = askAmount;
  }

  public void setBidPrice(BigDecimal bidPrice) {
    this.bidPrice = bidPrice;
  }

  public void setBidAmount(BigDecimal bidAmount) {
    this.bidAmount = bidAmount;
  }

  public static RestApiJsonParser<BestQuote> getParser(){
    return (jsonWrapper -> {
      BestQuote bestQuote = new BestQuote();
      bestQuote.setTimestamp(TimeService.convertCSTInMillisecondToUTC(jsonWrapper.getLong("ts")));
      JsonWrapper jsonObject = jsonWrapper.getJsonObject("tick");
      JsonWrapperArray askArray = jsonObject.getJsonArray("ask");
      bestQuote.setAskPrice(askArray.getBigDecimalAt(0));
      bestQuote.setAskAmount(askArray.getBigDecimalAt(1));
      JsonWrapperArray bidArray = jsonObject.getJsonArray("bid");
      bestQuote.setBidPrice(bidArray.getBigDecimalAt(0));
      bestQuote.setBidAmount(bidArray.getBigDecimalAt(1));
      return bestQuote;
    });
  }
}
