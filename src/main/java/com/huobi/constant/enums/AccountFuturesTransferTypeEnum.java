package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  AccountFuturesTransferTypeEnum {

  PRO_TO_FUTURES("pro-to-futures"),
  FUTURE_TO_PRO("futures-to-pro")

  ;
  private String type;
}
