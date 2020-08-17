package com.huobi.client.model.enums;

import com.huobi.client.impl.utils.EnumLookup;

/**
 * buy-market, sell-market, buy-limit, buy-ioc, sell-ioc,
 * buy-limit-maker, sell-limit-maker, buy-stop-limit, sell-stop-limit,
 * buy-limit-fok, sell-limit-fok, buy-stop-limit-fok, sell-stop-limit-fok.
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
  BUY_STOP_LIMIT("buy-stop-limit"),
  SELL_STOP_LIMIT("sell-stop-limit"),
  
  BUY_LIMIT_FOK("buy-limit-fok"),
  SELL_LIMIT_FOK("sell-limit-fok"),
  BUY_STOP_LIMIT_FOK("buy-stop-limit-fok"),
  SELL_STOP_LIMIT_FOK("sell-stop-limit-fok"),
  

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
