package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * buy-market, sell-market, buy-limit, buy-ioc, sell-ioc,
 * buy-limit-maker, sell-limit-maker, buy-stop-limit, sell-stop-limit.
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum {
  BUY_MARKET("buy-market"),
  SELL_MARKET("sell-market"),
  BUY_LIMIT("buy-limit"),
  SELL_LIMIT("sell-limit"),
  BUY_IOC("buy-ioc"),
  SELL_IOC("sell-ioc"),
  BUY_LIMIT_MAKER("buy-limit-maker"),
  SELL_LIMIT_MAKER("sell-limit-maker"),
  BUY_STOP_LIMIT("buy-stop-limit"),
  SELL_STOP_LIMIT("sell-stop-limit"),

  INVALID("invalid");

  private final String code;


  public static OrderTypeEnum find(String code) {
    for (OrderTypeEnum typeEnum : OrderTypeEnum.values()) {
      if (typeEnum.getCode().equals(code)) {
        return typeEnum;
      }
    }
    return null;
  }

}
