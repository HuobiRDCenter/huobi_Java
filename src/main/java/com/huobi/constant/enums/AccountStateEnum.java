package com.huobi.constant.enums;

import lombok.Getter;

/**
 * working, lock.
 */
@Getter
public enum AccountStateEnum {
  WORKING("working"),
  LOCK("lock");

  private final String code;

  AccountStateEnum(String code) {
    this.code = code;
  }

  public static AccountStateEnum find(String code) {
    for (AccountStateEnum stateEnum : AccountStateEnum.values()) {
      if (stateEnum.getCode().equals(code)) {
        return stateEnum;
      }
    }
    return null;
  }

}
