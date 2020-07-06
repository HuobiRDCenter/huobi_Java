package com.huobi.client.model.enums;

public enum TradableMarketAccountTypeEnum {

  ISOLATED_MARGIN("isolated-margin"),

  CROSS_MARGIN("cross-margin"),
  ;

  private final String accountType;
  TradableMarketAccountTypeEnum(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountType() {
    return accountType;
  }

  public TradableMarketAccountTypeEnum find(String accountType) {
    for (TradableMarketAccountTypeEnum typeEnum : TradableMarketAccountTypeEnum.values()) {
      if (typeEnum.getAccountType().equals(accountType)) {
        return typeEnum;
      }
    }
    return null;
  }
}
