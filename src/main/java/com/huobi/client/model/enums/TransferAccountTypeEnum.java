package com.huobi.client.model.enums;

public enum  TransferAccountTypeEnum {

  SPOT("spot"),
  MARGIN("margin"),
  ;

  private final String accountType;
  TransferAccountTypeEnum(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountType() {
    return accountType;
  }

  public TransferAccountTypeEnum find(String accountType) {
    for (TransferAccountTypeEnum typeEnum : TransferAccountTypeEnum.values()) {
      if (typeEnum.getAccountType().equals(accountType)) {
        return typeEnum;
      }
    }
    return null;
  }
}
