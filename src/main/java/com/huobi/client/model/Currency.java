package com.huobi.client.model;

import java.util.List;

public class Currency {

  /**
   * Name of Currency
   */
  private String currency;

  /**
   * Instrument status: normal,delisted
   */
  private String instStatus;

  private List<Chain> chains;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getInstStatus() {
    return instStatus;
  }

  public void setInstStatus(String instStatus) {
    this.instStatus = instStatus;
  }

  public List<Chain> getChains() {
    return chains;
  }

  public void setChains(List<Chain> chains) {
    this.chains = chains;
  }
}
