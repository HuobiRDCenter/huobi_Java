//package com.huobi.client.model.enums;
//
//import com.huobi.client.impl.utils.EnumLookup;
//
///**
// * trade,frozen
// */
//
//public enum TradeOrFrozen {
//
//
//  TRADE("trade"),
//  FROZEN("frozen");
//  private final String code;
//
//  TradeOrFrozen(String side) {
//    this.code = side;
//  }
//
//  @Override
//  public String toString() {
//    return code;
//  }
//
//  private static final EnumLookup<TradeOrFrozen> lookup = new EnumLookup<>(TradeOrFrozen.class);
//
//  public static TradeOrFrozen lookup(String name) {
//    return lookup.lookup(name);
//  }
//}
