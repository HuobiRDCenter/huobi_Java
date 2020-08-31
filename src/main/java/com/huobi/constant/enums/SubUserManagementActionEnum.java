package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  SubUserManagementActionEnum {

  LOCK("lock"),
  UNLOCK("unlock"),

  ;
  private String action;
}
