package com.huobi.client.model.enums;

import com.huobi.client.impl.utils.EnumLookup;

/**
 * buy-market, sell-market, buy-limit, buy-ioc, sell-ioc, buy-limit-maker, sell-limit-maker.
 */
public enum OrderType {
  BUY_MARKET("buy-market"),
  SELL_MARKET("sell-market"),
  BUY_LIMIT("buy-limit"),
  SELL_LIMIT("sell-limit"),
  BUY_IOC("buy-ioc"),
  SELL_IOC("sell-ioc"),
  BUY_LIMIT_MAKER("buy-limit-maker"),
  SELL_LIMIT_MAKER("sell-limit-maker"),
  INVALID("invalid");

  private final String code;

  OrderType(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }

  private static final EnumLookup<OrderType> lookup = new EnumLookup<>(OrderType.class);

  public static OrderType lookup(String name) {
    return lookup.lookup(name);
  }

}
