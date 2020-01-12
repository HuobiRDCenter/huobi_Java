package com.huobi.client.model;

import java.util.List;

public class WithdrawQuota {

  /**
   * Crypto currency
   */
  private String currency;

  private List<ChainQuota> chains;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public List<ChainQuota> getChains() {
    return chains;
  }

  public void setChains(List<ChainQuota> chains) {
    this.chains = chains;
  }
}
