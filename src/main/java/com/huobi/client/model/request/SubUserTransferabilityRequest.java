package com.huobi.client.model.request;

public class SubUserTransferabilityRequest {

  private String subUids;

  private String accountType;

  private String transferrable;

  public SubUserTransferabilityRequest() {}

  public SubUserTransferabilityRequest(String subUids, String accountType, String transferrable) {
    this.subUids = subUids;
    this.accountType = accountType;
    this.transferrable = transferrable;
  }

  public String getSubUids() {
    return subUids;
  }

  public void setSubUids(String subUids) {
    this.subUids = subUids;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getTransferrable() {
    return transferrable;
  }

  public void setTransferrable(String transferrable) {
    this.transferrable = transferrable;
  }
}
