package com.huobi.constant.enums.algo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlgoOrderStatusEnum {

  CREATED("created"),
  CANCELED("canceled"),
  TRIGGERED("triggered"),
  REJECTED("rejected"),

  ;
  private final String status;
}
