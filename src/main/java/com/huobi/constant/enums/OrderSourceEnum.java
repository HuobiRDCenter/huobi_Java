package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * sys, web, api, app.
 */
@Getter
@AllArgsConstructor
public enum OrderSourceEnum {
  SYS("sys"),
  WEB("web"),
  API("api"),
  APP("app"),
  FLSYS("fl-sys"),
  FLMGT("fl-mgt"),
  SPOTWEB("spot-web"),
  SPOTAPI("spot-api"),
  SPOTAPP("spot-app"),
  MARGINAPI("margin-api"),
  MARGINWEB("margin-web"),
  MARGINAPP("margin-app");

  private final String code;

  public static OrderSourceEnum find(String code) {
    for (OrderSourceEnum sourceEnum : OrderSourceEnum.values()) {
      if (sourceEnum.getCode().equals(code)) {
        return sourceEnum;
      }
    }
    return null;
  }

}
