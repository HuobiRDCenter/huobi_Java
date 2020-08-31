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
  SPOT_WEB("spot-web"),
  SPOT_API("spot-api"),
  SPOT_APP("spot-app"),
  MARGIN_API("margin-api"),
  MARGIN_WEB("margin-web"),
  MARGIN_APP("margin-app"),
  SUPER_MARGIN_WEB( "super-margin-web"),
  SUPER_MARGIN_API("super-margin-api"),
  SUPER_MARGIN_APP( "super-margin-app"),

    ;
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
