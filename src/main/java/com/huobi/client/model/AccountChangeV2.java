package com.huobi.client.model;

import java.math.BigDecimal;

/**
 * The account change information received by subscription of account.
 */
public class AccountChangeV2 {

  private String currency;

  private Long accountId;

  private BigDecimal balance;

  private String changeType;

  private String accountType;

  private Long changeTime;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String getChangeType() {
    return changeType;
  }

  public void setChangeType(String changeType) {
    this.changeType = changeType;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public Long getChangeTime() {
    return changeTime;
  }

  public void setChangeTime(Long changeTime) {
    this.changeTime = changeTime;
  }
}
