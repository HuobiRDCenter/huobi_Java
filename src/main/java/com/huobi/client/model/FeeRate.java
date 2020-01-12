package com.huobi.client.model;

import java.math.BigDecimal;

public class FeeRate {

  private String symbol;
  private BigDecimal makerFee;
  private BigDecimal takerFee;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public BigDecimal getMakerFee() {
    return makerFee;
  }

  public void setMakerFee(BigDecimal makerFee) {
    this.makerFee = makerFee;
  }

  public BigDecimal getTakerFee() {
    return takerFee;
  }

  public void setTakerFee(BigDecimal takerFee) {
    this.takerFee = takerFee;
  }
}
