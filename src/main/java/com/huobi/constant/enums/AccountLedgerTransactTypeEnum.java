package com.huobi.constant.enums;

public enum AccountLedgerTransactTypeEnum {

  TRANSFER("transfer"),
  ;
  private final String code;

  AccountLedgerTransactTypeEnum(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public static AccountLedgerTransactTypeEnum find(String code) {
    for (AccountLedgerTransactTypeEnum transactType : AccountLedgerTransactTypeEnum.values()) {
      if (transactType.getCode().equals(code)) {
        return transactType;
      }
    }
    return null;
  }

}
