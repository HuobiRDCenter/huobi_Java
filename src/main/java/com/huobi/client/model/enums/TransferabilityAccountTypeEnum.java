package com.huobi.client.model.enums;

public enum TransferabilityAccountTypeEnum {

  SPOT("spot"),
  ;

  private final String accountType;
  TransferabilityAccountTypeEnum(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountType() {
    return accountType;
  }

  public TransferabilityAccountTypeEnum find(String accountType) {
    for (TransferabilityAccountTypeEnum typeEnum : TransferabilityAccountTypeEnum.values()) {
      if (typeEnum.getAccountType().equals(accountType)) {
        return typeEnum;
      }
    }
    return null;
  }
}
