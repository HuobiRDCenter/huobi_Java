package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SUBMITTED, PARTIALFILLED, CANCELLING. PARTIALCANCELED FILLED CANCELED CREATED
 */
@Getter
@AllArgsConstructor
public enum OrderStateEnum {
  SUBMITTED("submitted"),
  CREATED("created"),
  PARTIALFILLED("partial-filled"),
  CANCELLING("cancelling"),
  PARTIALCANCELED("partial-canceled"),
  FILLED("filled"),
  CANCELED("canceled");

  private final String code;

  public static OrderStateEnum find(String code) {
    for (OrderStateEnum stateEnum : OrderStateEnum.values()) {
      if (stateEnum.getCode().equals(code)) {
        return stateEnum;
      }
    }
    return null;
  }
}
