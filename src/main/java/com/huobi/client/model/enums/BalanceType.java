package com.huobi.client.model.enums;

import com.huobi.client.impl.utils.EnumLookup;

public enum BalanceType {
  TRADE("trade"),
  FROZEN("frozen"),
  LOAN("loan"),
  INTEREST("interest"),
  LOAN_AVAILABLE("loan-available"),
  TRANSFER_OUT_AVAILABLE("transfer-out-available");



  private final String code;

  BalanceType(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }

  private static final EnumLookup<BalanceType> lookup = new EnumLookup<>(BalanceType.class);

  public static BalanceType lookup(String name) {
    return lookup.lookup(name);
  }

}
