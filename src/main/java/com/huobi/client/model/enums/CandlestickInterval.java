package com.huobi.client.model.enums;

/**
 * 1min, 5min, 15min, 30min, 60min, 1day, 1mon, 1week, 1year
 */
public enum CandlestickInterval {
  MIN1("1min"),
  MIN5("5min"),
  MIN15("15min"),
  MIN30("30min"),
  MIN60("60min"),
  DAY1("1day"),
  MON1("1mon"),
  WEEK1("1week"),
  YEAR1("1year");

  private final String code;

  CandlestickInterval(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }
}
