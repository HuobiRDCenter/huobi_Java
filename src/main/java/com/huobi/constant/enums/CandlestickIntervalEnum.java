package com.huobi.constant.enums;

import lombok.Getter;

@Getter
public enum CandlestickIntervalEnum {

  MIN1("1min"),
  MIN5("5min"),
  MIN15("15min"),
  MIN30("30min"),
  MIN60("60min"),
  HOUR4("4hour"),
  DAY1("1day"),
  MON1("1mon"),
  WEEK1("1week"),
  YEAR1("1year");

  private final String code;

  CandlestickIntervalEnum(String code) {
    this.code = code;
  }



}
