package com.huobi.constant.enums;

import lombok.Getter;

/**
 * The balance mode used for subscribing the balance notification.
 */
@Getter
public enum BalanceModeEnum {

  /**
   * Subscribe available balance
   */
  AVAILABLE("0"),

  /**
   * Subscribe TOTAL balance, total balance is the sum of available and frozen
   */
  TOTAL("1");

  private final String code;

  BalanceModeEnum(String code) {
    this.code = code;
  }

}
