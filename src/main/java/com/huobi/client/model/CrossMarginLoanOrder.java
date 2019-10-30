package com.huobi.client.model;

import java.math.BigDecimal;

public class CrossMarginLoanOrder {

  /**
   * Order id
   */
  private Long id;

  /**
   * Account id
   */
  private Long accountId;

  /**
   * User id
   */
  private Long userId;

  /**
   * The currency in the loan
   */
  private String currency;

  /**
   * The amount of the loan left
   */
  private BigDecimal loanBalance;

  /**
   * The amount of the origin loan
   */
  private BigDecimal loanAmount;

  /**
   * The amount of loan interest left
   */
  private BigDecimal interestBalance;

  /**
   * The accumulated loan interest
   */
  private BigDecimal interestAmount;

  /**
   * Point deduction amount
   */
  private BigDecimal filledPoints;

  /**
   * HT deduction amount
   */
  private BigDecimal filledHt;

  private String state;

  /**
   * The timestamp in milliseconds when the last accure happened
   */
  private Long accruedAt;

  /**
   * The timestamp in milliseconds when the order was created
   */
  private Long createdAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getLoanBalance() {
    return loanBalance;
  }

  public void setLoanBalance(BigDecimal loanBalance) {
    this.loanBalance = loanBalance;
  }

  public BigDecimal getLoanAmount() {
    return loanAmount;
  }

  public void setLoanAmount(BigDecimal loanAmount) {
    this.loanAmount = loanAmount;
  }

  public BigDecimal getInterestBalance() {
    return interestBalance;
  }

  public void setInterestBalance(BigDecimal interestBalance) {
    this.interestBalance = interestBalance;
  }

  public BigDecimal getInterestAmount() {
    return interestAmount;
  }

  public void setInterestAmount(BigDecimal interestAmount) {
    this.interestAmount = interestAmount;
  }

  public BigDecimal getFilledPoints() {
    return filledPoints;
  }

  public void setFilledPoints(BigDecimal filledPoints) {
    this.filledPoints = filledPoints;
  }

  public BigDecimal getFilledHt() {
    return filledHt;
  }

  public void setFilledHt(BigDecimal filledHt) {
    this.filledHt = filledHt;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Long getAccruedAt() {
    return accruedAt;
  }

  public void setAccruedAt(Long accruedAt) {
    this.accruedAt = accruedAt;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }
}
