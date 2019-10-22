package com.huobi.constant.enums;

import lombok.Getter;

/**
 * SPOT, MARGIN, OTC, POINT, UNKNOWN.
 */
@Getter
public enum AccountTypeEnum {
  SPOT("spot"),
  MARGIN("margin"),
  SUPER_MARGIN("super-margin","cross-margin"),
  OTC("otc"),
  POINT("point"),
  UNKNOWN("unknown");

  private final String code;

  private final String alias;

  AccountTypeEnum(String code) {
    this.alias = code;
    this.code = code;
  }

  AccountTypeEnum(String code,String alias) {
    this.alias = alias;
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

  public static AccountTypeEnum findAll(String code) {
    for (AccountTypeEnum typeEnum : AccountTypeEnum.values()) {
      if (typeEnum.getCode().equals(code) || typeEnum.getAlias().equals(code)) {
        return typeEnum;
      }
    }
    return null;
  }
}
