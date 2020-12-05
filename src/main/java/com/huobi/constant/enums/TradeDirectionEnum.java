package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * buy, sell.
 */
@Getter
@AllArgsConstructor
public enum TradeDirectionEnum {
  BUY("buy"),
  SELL("sell");

  private final String code;

  public static TradeDirectionEnum find(String code) {
    for (TradeDirectionEnum directionEnum : TradeDirectionEnum.values()) {
      if (directionEnum.getCode().equals(code)) {
        return directionEnum;
      }
    }
    return null;
  }

}
