package com.huobi.client.model;

import com.huobi.client.model.enums.WithdrawState;
import java.math.BigDecimal;

/**
 * The latest status for withdraws.
 */
public class Withdraw {

  private long id;
  private String currency;
  private String txHash;
  private BigDecimal amount;
  private String address;
  private String addressTag;
  private BigDecimal fee;
  private long createdTimestamp;
  private long updatedTimestamp;
  private WithdrawState withdrawState;

  /**
   * Get the transfer id.
   *
   * @return The id.
   */
  public long getId() {
    return id;
  }

  /**
   * Get the crypto currency to withdraw.
   *
   * @return The currency.
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * Get the on-chain transaction hash.
   *
   * @return The hash value.
   */
  public String getTxHash() {
    return txHash;
  }

  /**
   * Get the number of crypto asset transferred in its minimum unit.
   *
   * @return The amount.
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Get the withdraw source address.
   *
   * @return The address.
   */
  public String getAddress() {
    return address;
  }

  /**
   * Get the user defined address tag.
   *
   * @return The address.
   */
  public String getAddressTag() {
    return addressTag;
  }

  /**
   * Get the amount of fee taken by Huobi in this crypto's minimum unit.
   *
   * @return The amount of fee.
   */
  public BigDecimal getFee() {
    return fee;
  }

  /**
   * Get the UNIX formatted timestamp in UTC for the transfer creation.
   *
   * @return The timestamp.
   */
  public long getCreatedTimestamp() {
    return createdTimestamp;
  }

  /**
   * Get the UNIX formatted timestamp in UTC for the transfer's latest update.
   *
   * @return The timestamp.
   */
  public long getUpdatedTimestamp() {
    return updatedTimestamp;
  }

  /**
   * Get the withdraw state of this transfer.
   *
   * @return The withdraw state. {@link WithdrawState}
   */
  public WithdrawState getWithdrawState() {
    return withdrawState;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setTxHash(String txHash) {
    this.txHash = txHash;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setAddressTag(String addressTag) {
    this.addressTag = addressTag;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  public void setCreatedTimestamp(long createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public void setUpdatedTimestamp(long updatedTimestamp) {
    this.updatedTimestamp = updatedTimestamp;
  }

  public void setWithdrawState(WithdrawState withdrawState) {
    this.withdrawState = withdrawState;
  }
}
