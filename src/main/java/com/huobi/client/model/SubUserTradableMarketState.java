package com.huobi.client.model;

public class SubUserTradableMarketState {

  private Long subUid;

  private String accountType;

  private String activation;

  private String errCode;

  private String errMessage;

  public SubUserTradableMarketState() {}

  public SubUserTradableMarketState(Long subUid, String accountType, String activation, String errCode, String errMessage) {
    this.subUid = subUid;
    this.accountType = accountType;
    this.activation = activation;
    this.errCode = errCode;
    this.errMessage = errMessage;
  }

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

  public String getActivation() {
    return activation;
  }

  public void setActivation(String activation) {
    this.activation = activation;
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
