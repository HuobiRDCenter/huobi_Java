package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * created, accrual, cleared, invalid.
 */
@Getter
@AllArgsConstructor
public enum LoanOrderStateEnum {

  CREATED("created"),
  ACCRUAL("accrual"),
  CLEARED("cleared"),
  INVALID("invalid");

  private final String code;

  public static LoanOrderStateEnum find(String code) {

    for (LoanOrderStateEnum stateEnum : LoanOrderStateEnum.values()) {
      if (stateEnum.getCode().equals(code)) {
        return stateEnum;
      }
    }
    return null;
  }
}
