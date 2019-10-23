package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EtfStatusEnum {
  NORMAL("1"),
  REBALANCING_START("2"),
  CREATION_AND_REDEMPTION_SUSPEND("3"),
  CREATION_SUSPEND("4"),
  REDEMPTION_SUSPEND("5");

  private final String code;

  public static EtfStatusEnum find(String code) {
    for (EtfStatusEnum statusEnum : EtfStatusEnum.values()) {
      if (statusEnum.getCode().equals(code)) {
        return statusEnum;
      }
    }
    return null;
  }
}
