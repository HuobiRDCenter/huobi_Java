package com.huobi.client.model;

public class SubUserAccount {

  private Long accountId;

  private String subType;

  private String accountStatus;

  public SubUserAccount() {}

  public SubUserAccount(Long accountId, String subType, String accountStatus) {
    this.accountId = accountId;
    this.subType = subType;
    this.accountStatus = accountStatus;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public String getSubType() {
    return subType;
  }

  public void setSubType(String subType) {
    this.subType = subType;
  }

  public String getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
  }
}
