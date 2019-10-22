package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CrossMarginAccountStateEnum {

  WORKING("working"),
  FL_SYS("fl-sys"),
  FL_END("fl-end"),
  FL_NEGATIVE("fl-negative")

  ;

  private String code;

  public static CrossMarginAccountStateEnum find(String code) {

    for (CrossMarginAccountStateEnum stateEnum : CrossMarginAccountStateEnum.values()) {
      if (stateEnum.getCode().equals(code)) {
        return stateEnum;
      }
    }
    return null;
  }

}
