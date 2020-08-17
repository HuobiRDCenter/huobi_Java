package com.huobi.client.model;

import java.math.BigDecimal;

public class MarginLoanCurrencyInfo {

  /**
   * Currency
   */
  private String currency;

  /**
   * Basic daily interest rate
   */
  private BigDecimal interestRate;

  /**
   * Minimal loanable amount
   */
  private BigDecimal minLoanAmt;

  /**
   * Maximum loanable amount
   */
  private BigDecimal maxLoanAmt;

  /**
   * Remaining loanable amount
   */
  private BigDecimal loanableAmt;

  /**
   * Actual interest rate (if deduction is inapplicable or disabled, return basic daily interest rate)
   */
  private BigDecimal actualRate;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getInterestRate() {
    return interestRate;
  }

  public void setInterestRate(BigDecimal interestRate) {
    this.interestRate = interestRate;
  }

  public BigDecimal getMinLoanAmt() {
    return minLoanAmt;
  }

  public void setMinLoanAmt(BigDecimal minLoanAmt) {
    this.minLoanAmt = minLoanAmt;
  }

  public BigDecimal getMaxLoanAmt() {
    return maxLoanAmt;
  }

  public void setMaxLoanAmt(BigDecimal maxLoanAmt) {
    this.maxLoanAmt = maxLoanAmt;
  }

  public BigDecimal getLoanableAmt() {
    return loanableAmt;
  }

  public void setLoanableAmt(BigDecimal loanableAmt) {
    this.loanableAmt = loanableAmt;
  }

  public BigDecimal getActualRate() {
    return actualRate;
  }

  public void setActualRate(BigDecimal actualRate) {
    this.actualRate = actualRate;
  }
}
