package com.huobi.client.model;

public class DepositAddress {

  /**
   * Crypto currency
   */
  private String currency;

  /**
   * Block chain name
   */
  private String chain;

  /**
   * Deposit address
   */
  private String address;

  /**
   * Deposit address tag
   */
  private String addressTag;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getChain() {
    return chain;
  }

  public void setChain(String chain) {
    this.chain = chain;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddressTag() {
    return addressTag;
  }

  public void setAddressTag(String addressTag) {
    this.addressTag = addressTag;
  }
}
