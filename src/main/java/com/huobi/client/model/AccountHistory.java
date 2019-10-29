package com.huobi.client.model;

import java.math.BigDecimal;

public class AccountHistory {

  /**
   * Account ID
   */
  private Long accountId;

  /**
   * Unique record ID in the database
   */
  private Long recordId;

  /**
   * Currency
   */
  private String currency;

  /**
   * Amount change (positive value if income, negative value if outcome)
   */
  private BigDecimal transactAmt;

  /**
   * Amount change types
   */
  private String transactType;

  /**
   * Available balance
   */
  private BigDecimal availBalance;

  /**
   * Account balance
   */
  private BigDecimal acctBalance;

  /**
   * Transaction time (database time)
   */
  private Long transactTime;

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Long getRecordId() {
    return recordId;
  }

  public void setRecordId(Long recordId) {
    this.recordId = recordId;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getTransactAmt() {
    return transactAmt;
  }

  public void setTransactAmt(BigDecimal transactAmt) {
    this.transactAmt = transactAmt;
  }

  public String getTransactType() {
    return transactType;
  }

  public void setTransactType(String transactType) {
    this.transactType = transactType;
  }

  public BigDecimal getAvailBalance() {
    return availBalance;
  }

  public void setAvailBalance(BigDecimal availBalance) {
    this.availBalance = availBalance;
  }

  public BigDecimal getAcctBalance() {
    return acctBalance;
  }

  public void setAcctBalance(BigDecimal acctBalance) {
    this.acctBalance = acctBalance;
  }

  public Long getTransactTime() {
    return transactTime;
  }

  public void setTransactTime(Long transactTime) {
    this.transactTime = transactTime;
  }
}
