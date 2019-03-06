package com.huobi.client.model.request;

import com.huobi.client.model.enums.CandlestickInterval;

/**
 * The request for getting candlestick/kline data.
 */
public class CandlestickRequest {

  /**
   * The request for getting candlestick/kline data.
   *
   * @param symbol The symbol, like "btcusdt". To query hb10, put "hb10" at here. (mandatory)
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc. (mandatory)
   */
  public CandlestickRequest(String symbol, CandlestickInterval interval) {
    this.symbol = symbol;
    this.interval = interval;
  }


  /**
   * The request for getting candlestick/kline data.
   *
   * @param symbol The symbol, like "btcusdt". To query hb10, put "hb10" at here. (mandatory)
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc. (mandatory)
   * @param startTime The start time of of requested candlestick/kline data. (optional, can be
   * null)
   * @param endTime The end time of of requested candlestick/kline data. (optional, can be null)
   * @param size The maximum number of candlestick/kline requested. Range [1 - 2000]. (optional, can
   * be null)
   */
  public CandlestickRequest(
      String symbol,
      CandlestickInterval interval,
      Long startTime,
      Long endTime,
      Integer size) {
    this.symbol = symbol;
    this.interval = interval;
    this.startTime = startTime;
    this.endTime = endTime;
    this.size = size;
  }

  private final String symbol;
  private final CandlestickInterval interval;
  private Long startTime = null;
  private Long endTime = null;
  private Integer size = null;

  public String getSymbol() {
    return symbol;
  }

  public CandlestickInterval getInterval() {
    return interval;
  }

  public Long getStartTime() {
    return startTime;
  }

  public Long getEndTime() {
    return endTime;
  }

  public Integer getSize() {
    return size;
  }
}
