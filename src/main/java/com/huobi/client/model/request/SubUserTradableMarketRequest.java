package com.huobi.client.model.request;

public class SubUserTradableMarketRequest {

  private String subUids;

  private String accountType;

  private String activation;

  public SubUserTradableMarketRequest() {}

  public SubUserTradableMarketRequest(String subUids, String accountType, String activation) {
    this.subUids = subUids;
    this.accountType = accountType;
    this.activation = activation;
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

  public String getActivation() {
    return activation;
  }

  public void setActivation(String activation) {
    this.activation = activation;
  }
}
