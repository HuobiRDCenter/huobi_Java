package com.huobi.client.model;

import java.util.List;

public class SubUserAccountInfo {

  private String accountType;

  private String activation;

  private Boolean transferrable;

  private List<SubUserAccount> accountIds;

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

  public Boolean getTransferrable() {
    return transferrable;
  }

  public void setTransferrable(Boolean transferrable) {
    this.transferrable = transferrable;
  }

  public List<SubUserAccount> getAccountIds() {
    return accountIds;
  }

  public void setAccountIds(List<SubUserAccount> accountIds) {
    this.accountIds = accountIds;
  }
}
