package com.huobi.client.model;

import java.math.BigDecimal;

public class Chain {

  /**
   * Chain name
   */
  private String chain;

  /**
   * Deposit status	: allowed,prohibited
   */
  private String depositStatus;

  /**
   * Withdraw status	: allowed,prohibited
   */
  private String withdrawStatus;

  /**
   * Number of confirmations required for deposit success (trading & withdrawal allowed once reached)
   */
  private int numOfConfirmations;

  /**
   * Number of confirmations required for quick success (trading allowed but withdrawal disallowed once reached)
   */
  private int numOfFastConfirmations;

  /**
   * Minimal withdraw fee in each request (only applicable to withdrawFeeType = circulated)
   */
  private BigDecimal minTransactFeeWithdraw;

  /**
   * Maximum withdraw fee in each request (only applicable to withdrawFeeType = circulated or ratio)
   */
  private BigDecimal maxTransactFeeWithdraw;

  /**
   * Minimal deposit amount in each request
   */
  private BigDecimal minDepositAmt;

  /**
   * Minimal withdraw amount in each request
   */
  private BigDecimal minWithdrawAmt;

  /**
   * Maximum withdraw amount in each request
   */
  private BigDecimal maxWithdrawAmt;

  /**
   * Type of withdraw fee (only one type can be applied to each currency)	: fixed,circulated,ratio
   */
  private  String withdrawFeeType;

  /**
   * Withdraw amount precision
   */
  private int withdrawPrecision;

  /**
   * Maximum withdraw amount in a day
   */
  private BigDecimal withdrawQuotaPerDay;

  /**
   * Maximum withdraw amount in a year
   */
  private BigDecimal withdrawQuotaPerYear;

  /**
   * Maximum withdraw amount in total
   */
  private BigDecimal withdrawQuotaTotal;

  /**
   * Withdraw fee in each request (only applicable to withdrawFeeType = fixed)
   */
  private BigDecimal transactFeeWithdraw;

  /**
   * Withdraw fee in each request (only applicable to withdrawFeeType = ratio)
   */
  private BigDecimal transactFeeRateWithdraw;

  public String getChain() {
    return chain;
  }

  public void setChain(String chain) {
    this.chain = chain;
  }

  public String getDepositStatus() {
    return depositStatus;
  }

  public void setDepositStatus(String depositStatus) {
    this.depositStatus = depositStatus;
  }

  public String getWithdrawStatus() {
    return withdrawStatus;
  }

  public void setWithdrawStatus(String withdrawStatus) {
    this.withdrawStatus = withdrawStatus;
  }

  public int getNumOfConfirmations() {
    return numOfConfirmations;
  }

  public void setNumOfConfirmations(int numOfConfirmations) {
    this.numOfConfirmations = numOfConfirmations;
  }

  public int getNumOfFastConfirmations() {
    return numOfFastConfirmations;
  }

  public void setNumOfFastConfirmations(int numOfFastConfirmations) {
    this.numOfFastConfirmations = numOfFastConfirmations;
  }

  public BigDecimal getMinTransactFeeWithdraw() {
    return minTransactFeeWithdraw;
  }

  public void setMinTransactFeeWithdraw(BigDecimal minTransactFeeWithdraw) {
    this.minTransactFeeWithdraw = minTransactFeeWithdraw;
  }

  public BigDecimal getMaxTransactFeeWithdraw() {
    return maxTransactFeeWithdraw;
  }

  public void setMaxTransactFeeWithdraw(BigDecimal maxTransactFeeWithdraw) {
    this.maxTransactFeeWithdraw = maxTransactFeeWithdraw;
  }

  public BigDecimal getMinDepositAmt() {
    return minDepositAmt;
  }

  public void setMinDepositAmt(BigDecimal minDepositAmt) {
    this.minDepositAmt = minDepositAmt;
  }

  public BigDecimal getMinWithdrawAmt() {
    return minWithdrawAmt;
  }

  public void setMinWithdrawAmt(BigDecimal minWithdrawAmt) {
    this.minWithdrawAmt = minWithdrawAmt;
  }

  public BigDecimal getMaxWithdrawAmt() {
    return maxWithdrawAmt;
  }

  public void setMaxWithdrawAmt(BigDecimal maxWithdrawAmt) {
    this.maxWithdrawAmt = maxWithdrawAmt;
  }

  public String getWithdrawFeeType() {
    return withdrawFeeType;
  }

  public void setWithdrawFeeType(String withdrawFeeType) {
    this.withdrawFeeType = withdrawFeeType;
  }

  public int getWithdrawPrecision() {
    return withdrawPrecision;
  }

  public void setWithdrawPrecision(int withdrawPrecision) {
    this.withdrawPrecision = withdrawPrecision;
  }

  public BigDecimal getWithdrawQuotaPerDay() {
    return withdrawQuotaPerDay;
  }

  public void setWithdrawQuotaPerDay(BigDecimal withdrawQuotaPerDay) {
    this.withdrawQuotaPerDay = withdrawQuotaPerDay;
  }

  public BigDecimal getWithdrawQuotaPerYear() {
    return withdrawQuotaPerYear;
  }

  public void setWithdrawQuotaPerYear(BigDecimal withdrawQuotaPerYear) {
    this.withdrawQuotaPerYear = withdrawQuotaPerYear;
  }

  public BigDecimal getWithdrawQuotaTotal() {
    return withdrawQuotaTotal;
  }

  public void setWithdrawQuotaTotal(BigDecimal withdrawQuotaTotal) {
    this.withdrawQuotaTotal = withdrawQuotaTotal;
  }

  public BigDecimal getTransactFeeWithdraw() {
    return transactFeeWithdraw;
  }

  public void setTransactFeeWithdraw(BigDecimal transactFeeWithdraw) {
    this.transactFeeWithdraw = transactFeeWithdraw;
  }

  public BigDecimal getTransactFeeRateWithdraw() {
    return transactFeeRateWithdraw;
  }

  public void setTransactFeeRateWithdraw(BigDecimal transactFeeRateWithdraw) {
    this.transactFeeRateWithdraw = transactFeeRateWithdraw;
  }
}
