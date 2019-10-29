package com.huobi.client.model;

import java.math.BigDecimal;

public class ChainQuota {

  /**
   * Block chain name
   */
  private String chain;

  /**
   * Maximum withdraw amount in each request
   */
  private BigDecimal maxWithdrawAmt;

  /**
   * withdrawQuotaPerDay
   */
  private BigDecimal withdrawQuotaPerDay;

  /**
   * Remaining withdraw quota in the day
   */
  private BigDecimal remainWithdrawQuotaPerDay;

  /**
   * Maximum withdraw amount in a year
   */
  private BigDecimal withdrawQuotaPerYear;

  /**
   * Remaining withdraw quota in the year
   */
  private BigDecimal remainWithdrawQuotaPerYear;

  /**
   * Maximum withdraw amount in total
   */
  private BigDecimal withdrawQuotaTotal;

  /**
   * Remaining withdraw quota in total
   */
  private BigDecimal remainWithdrawQuotaTotal;

  public String getChain() {
    return chain;
  }

  public void setChain(String chain) {
    this.chain = chain;
  }

  public BigDecimal getMaxWithdrawAmt() {
    return maxWithdrawAmt;
  }

  public void setMaxWithdrawAmt(BigDecimal maxWithdrawAmt) {
    this.maxWithdrawAmt = maxWithdrawAmt;
  }

  public BigDecimal getWithdrawQuotaPerDay() {
    return withdrawQuotaPerDay;
  }

  public void setWithdrawQuotaPerDay(BigDecimal withdrawQuotaPerDay) {
    this.withdrawQuotaPerDay = withdrawQuotaPerDay;
  }

  public BigDecimal getRemainWithdrawQuotaPerDay() {
    return remainWithdrawQuotaPerDay;
  }

  public void setRemainWithdrawQuotaPerDay(BigDecimal remainWithdrawQuotaPerDay) {
    this.remainWithdrawQuotaPerDay = remainWithdrawQuotaPerDay;
  }

  public BigDecimal getWithdrawQuotaPerYear() {
    return withdrawQuotaPerYear;
  }

  public void setWithdrawQuotaPerYear(BigDecimal withdrawQuotaPerYear) {
    this.withdrawQuotaPerYear = withdrawQuotaPerYear;
  }

  public BigDecimal getRemainWithdrawQuotaPerYear() {
    return remainWithdrawQuotaPerYear;
  }

  public void setRemainWithdrawQuotaPerYear(BigDecimal remainWithdrawQuotaPerYear) {
    this.remainWithdrawQuotaPerYear = remainWithdrawQuotaPerYear;
  }

  public BigDecimal getWithdrawQuotaTotal() {
    return withdrawQuotaTotal;
  }

  public void setWithdrawQuotaTotal(BigDecimal withdrawQuotaTotal) {
    this.withdrawQuotaTotal = withdrawQuotaTotal;
  }

  public BigDecimal getRemainWithdrawQuotaTotal() {
    return remainWithdrawQuotaTotal;
  }

  public void setRemainWithdrawQuotaTotal(BigDecimal remainWithdrawQuotaTotal) {
    this.remainWithdrawQuotaTotal = remainWithdrawQuotaTotal;
  }
}
