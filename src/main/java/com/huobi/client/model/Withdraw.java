package com.huobi.client.model;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.enums.WithdrawState;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import lombok.ToString;

/**
 * The latest status for withdraws.
 */
@ToString
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

  public static RestApiJsonParser<List<Withdraw>> getListParser(){
    return (jsonWrapper -> {
      List<Withdraw> withdraws = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Withdraw withdraw = new Withdraw();
        withdraw.setId(item.getLong("id"));
        withdraw.setCurrency(item.getString("currency"));
        withdraw.setTxHash(item.getString("tx-hash"));
        withdraw.setAmount(item.getBigDecimal("amount"));
        withdraw.setAddress(item.getString("address"));
        withdraw.setAddressTag(item.getString("address-tag"));
        withdraw.setFee(item.getBigDecimal("fee"));
        withdraw.setWithdrawState(WithdrawState.lookup(item.getString("state")));
        withdraw.setCreatedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("created-at")));
        withdraw.setUpdatedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("updated-at")));
        withdraws.add(withdraw);
      });
      return withdraws;
    });
  }
}
