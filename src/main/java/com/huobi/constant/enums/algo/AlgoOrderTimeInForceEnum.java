package com.huobi.constant.enums.algo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlgoOrderTimeInForceEnum {

  GTC("gtc"),
  BOC("boc"),
  IOC("ioc"),
  FOK("fok"),
  ;
  private final String timeInForce;
}
