package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  DepositWithdrawTypeEnum {

  DEPOSIT("deposit"),
  WITHDRAW("withdraw");

  private final String type;
}
