package com.huobi.client.model;

public class SubUserTransferabilityState {

  private Long subUid;

  private String accountType;

  private Boolean transferrable;

  private String errCode;

  private String errMessage;

  public Long getSubUid() {
    return subUid;
  }

  public void setSubUid(Long subUid) {
    this.subUid = subUid;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public Boolean getTransferrable() {
    return transferrable;
  }

  public void setTransferrable(Boolean transferrable) {
    this.transferrable = transferrable;
  }

  public String getErrCode() {
    return errCode;
  }

  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }

  public String getErrMessage() {
    return errMessage;
  }

  public void setErrMessage(String errMessage) {
    this.errMessage = errMessage;
  }
}
