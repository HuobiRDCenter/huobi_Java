package com.huobi.client.model;

import java.math.BigDecimal;

public class TransactFeeRate {

  /**
   * Trading symbol
   */
  private String symbol;

  /**
   * Basic fee rate – passive side
   */
  private BigDecimal makerFeeRate;

  /**
   * Basic fee rate – aggressive side
   */
  private BigDecimal takerFeeRate;

  /**
   * Deducted fee rate – passive side. If deduction is inapplicable or disabled, return basic fee rate.
   */
  private BigDecimal actualMakerRate;

  /**
   * Deducted fee rate – aggressive side. If deduction is inapplicable or disabled, return basic fee rate.
   */
  private BigDecimal actualTakerRate;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public BigDecimal getMakerFeeRate() {
    return makerFeeRate;
  }

  public void setMakerFeeRate(BigDecimal makerFeeRate) {
    this.makerFeeRate = makerFeeRate;
  }

  public BigDecimal getTakerFeeRate() {
    return takerFeeRate;
  }

  public void setTakerFeeRate(BigDecimal takerFeeRate) {
    this.takerFeeRate = takerFeeRate;
  }

  public BigDecimal getActualMakerRate() {
    return actualMakerRate;
  }

  public void setActualMakerRate(BigDecimal actualMakerRate) {
    this.actualMakerRate = actualMakerRate;
  }

  public BigDecimal getActualTakerRate() {
    return actualTakerRate;
  }

  public void setActualTakerRate(BigDecimal actualTakerRate) {
    this.actualTakerRate = actualTakerRate;
  }
}
