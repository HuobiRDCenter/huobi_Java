package com.huobi.constant.enums;

import lombok.Getter;

/**
 * SPOT, MARGIN, OTC, POINT, UNKNOWN.
 */
@Getter
public enum AccountTypeEnum {
  SPOT("spot"),
  MARGIN("margin"),
  SUPER_MARGIN("super-margin"),
  OTC("otc"),
  POINT("point"),
  UNKNOWN("unknown");

  private final String code;

  AccountTypeEnum(String code) {
    this.code = code;
  }

  public static AccountTypeEnum find(String code) {
    for (AccountTypeEnum typeEnum : AccountTypeEnum.values()) {
      if (typeEnum.getCode().equals(code)) {
        return typeEnum;
      }
    }
    return null;
  }
}
