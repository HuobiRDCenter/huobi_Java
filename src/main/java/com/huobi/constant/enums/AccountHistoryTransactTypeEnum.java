package com.huobi.constant.enums;

public enum AccountHistoryTransactTypeEnum {

  TRADE("trade"),
  ETF("etf"),
  TRANSACT_FEE("transact-fee"),
  FEE_DEDUCTION("fee-deduction"),
  TRANSFER("transfer"),
  CREDIT("credit"),
  LIQUIDATION("liquidation"),
  INTEREST("interest"),
  DEPOSIT("deposit"),
  WITHDRAW("withdraw"),
  WITHDRAW_FEE("withdraw-fee"),
  EXCHANGE("exchange"),
  REBATE("rebate"),
  OTHER_TYPES("other-types")

  ;
  private final String code;

  AccountHistoryTransactTypeEnum(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public static AccountHistoryTransactTypeEnum find(String code) {
    for (AccountHistoryTransactTypeEnum transactType : AccountHistoryTransactTypeEnum.values()) {
      if (transactType.getCode().equals(code)) {
        return transactType;
      }
    }
    return null;
  }

}
