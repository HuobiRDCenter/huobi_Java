package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EtfSwapTypeEnum {
  ETF_SWAP_IN("1"),
  ETF_SWAP_OUT("2");

  private final String code;

  public static EtfSwapTypeEnum find(String code) {
    for (EtfSwapTypeEnum typeEnum : EtfSwapTypeEnum.values()) {
      if (typeEnum.getCode().equals(code)) {
        return typeEnum;
      }
    }
    return null;
  }

}
