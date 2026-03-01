package com.huobi.constant.enums;

import lombok.Getter;

@Getter
public enum BalanceTypeEnum  {
  TRADE("trade"),
  FROZEN("frozen"),
  LOAN("loan"),
  INTEREST("interest"),
  LOAN_AVAILABLE("loan-available"),
  TRANSFER_OUT_AVAILABLE("transfer-out-available");



  private final String code;

  BalanceTypeEnum(String code) {
    this.code = code;
  }

  public static BalanceTypeEnum find(String code) {
    for (BalanceTypeEnum typeEnum : BalanceTypeEnum.values()) {
      if (typeEnum.getCode().equals(code)) {
        return typeEnum;
      }
    }
    return null;
  }

}
