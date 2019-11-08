package com.huobi.client.model.request;

import java.math.BigDecimal;

/**
 * The criteria for a withdraw request.
 */
public class WithdrawRequest {

  /**
   * The criteria for a withdraw request.
   *
   * @param address The destination address of this withdraw. (mandatory)
   * @param amount The amount of currency to withdraw. (mandatory)
   * @param currency The crypto currency to withdraw. (mandatory)
   */
  public WithdrawRequest(String address, BigDecimal amount, String currency) {
    this.address = address;
    this.amount = amount;
    this.currency = currency;
  }

  public WithdrawRequest(String address, BigDecimal amount, String currency,String chain) {
    this.address = address;
    this.amount = amount;
    this.currency = currency;
    this.chain = chain;
  }

  /**
   * The criteria for a withdraw request.
   *
   * @param address The destination address of this withdraw. (mandatory)
   * @param amount The amount of currency to withdraw. (mandatory)
   * @param currency The crypto currency to withdraw. (mandatory)
   * @param fee The fee to pay with this withdraw. (optional, can be null)
   * @param addressTag A tag specified for this address. (optional, can be null)
   */
  public WithdrawRequest(String address, BigDecimal amount, String currency, BigDecimal fee,
      String addressTag) {
    this.address = address;
    this.amount = amount;
    this.currency = currency;
    this.fee = fee;
    this.addressTag = addressTag;
  }

  public WithdrawRequest(String address, BigDecimal amount, String currency,String chain, BigDecimal fee, String addressTag) {
    this.address = address;
    this.amount = amount;
    this.currency = currency;
    this.chain = chain;
    this.fee = fee;
    this.addressTag = addressTag;
  }

  private final String address;

  private final BigDecimal amount;

  private final String currency;

  private BigDecimal fee;

  private String addressTag;

  private String chain;

  public String getAddress() {
    return address;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getAddressTag() {
    return addressTag;
  }

  public String getChain() {
    return chain;
  }
}
