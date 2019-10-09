package com.huobi.client.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.impl.utils.TimeService;

/**
 * The candlestick/kline data.
 */
public class Candlestick {

  private Long id;
  private long timestamp;
  private BigDecimal amount;
  private long count;
  private BigDecimal open;
  private BigDecimal close;
  private BigDecimal low;
  private BigDecimal high;
  private BigDecimal volume;

  /**
   * Get the UNIX formatted timestamp in UTC.
   *
   * @return The timestamp.
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * Get the aggregated trading volume in USDT.
   *
   * @return The amount.
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Get the number of completed trades.<br>
   *  it returns 0 when get ETF candlestick
   *
   * @return The number of trades.
   */
  public long getCount() {
    return count;
  }

  /**
   * Get the opening price.
   *
   * @return The open price.
   */
  public BigDecimal getOpen() {
    return open;
  }

  /**
   * Get the closing price.
   *
   * @return The close price.
   */
  public BigDecimal getClose() {
    return close;
  }

  /**
   * Get the low price.
   *
   * @return The low price.
   */
  public BigDecimal getLow() {
    return low;
  }

  /**
   * Get the high price.
   *
   * @return The high price.
   */
  public BigDecimal getHigh() {
    return high;
  }

  /**
   * Get the trading volume in base currency.
   *
   * @return The volume.
   */
  public BigDecimal getVolume() {
    return volume;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public void setOpen(BigDecimal open) {
    this.open = open;
  }

  public void setClose(BigDecimal close) {
    this.close = close;
  }

  public void setLow(BigDecimal low) {
    this.low = low;
  }

  public void setHigh(BigDecimal high) {
    this.high = high;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public static RestApiJsonParser<List<Candlestick>> getListParser() {
    return (jsonWrapper -> {
      List<Candlestick> res = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        res.add(parse(item));
      });

      return res;
    });
  }

  public static Candlestick parse(JsonWrapper item){
    Candlestick candlestick = new Candlestick();
    candlestick.setId(item.getLong("id"));
    candlestick.setTimestamp(TimeService.convertCSTInSecondToUTC(item.getLong("id")));
    candlestick.setOpen(item.getBigDecimal("open"));
    candlestick.setClose(item.getBigDecimal("close"));
    candlestick.setLow(item.getBigDecimal("low"));
    candlestick.setHigh(item.getBigDecimal("high"));
    candlestick.setAmount(item.getBigDecimal("amount"));
    candlestick.setCount(item.getLongOrDefault("count",0));
    candlestick.setVolume(item.getBigDecimal("vol"));
    return candlestick;
  }
}
