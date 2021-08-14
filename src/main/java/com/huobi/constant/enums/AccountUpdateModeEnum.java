package com.huobi.constant.enums;

import lombok.Getter;

/**
 * The balance mode used for subscribing the balance notification.
 */
@Getter
public enum AccountUpdateModeEnum {

  /**
   * Subscribe available balance
   */
  AVAILABLE_CHANGE("0"),

  /**
   * Subscribe TOTAL balance, total balance is the sum of available and frozen
   */
  ACCOUNT_CHANGE("1");

  private final String code;

  AccountUpdateModeEnum(String code) {
    this.code = code;
  }

}
