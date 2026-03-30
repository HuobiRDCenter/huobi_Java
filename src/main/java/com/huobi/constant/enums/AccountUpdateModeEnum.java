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
  ACCOUNT_CHANGE("1"),

    /**
     * 在账户余额发生变动或可用余额发生变动时均推送且一起推送。
     */
    AVAILABLE_OR_CHANGE("2"),
    ;

  private final String code;

  AccountUpdateModeEnum(String code) {
    this.code = code;
  }

}
