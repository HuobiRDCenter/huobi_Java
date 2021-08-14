package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  IsolatedMarginAccountStateEnum {

  WORKING("working"),
  FL_SYS("fl-sys"),
  FL_MGT("fl-mgt"),
  FL_END("fl-end")

  ;

  private String code;

  public static IsolatedMarginAccountStateEnum find(String code) {

    return null;
  }

}
