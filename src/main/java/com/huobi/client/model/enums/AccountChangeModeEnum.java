package com.huobi.client.model.enums;

/**
 * The balance mode used for subscribing the balance notification.
 */
public enum AccountChangeModeEnum {

  /**
   * Subscribe balance change
   */
  BALANCE("0"),

  /**
   * Subscribe TOTAL balance, total balance is the sum of available and frozen
   */
  TOTAL("1");

  private final String code;

  AccountChangeModeEnum(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
