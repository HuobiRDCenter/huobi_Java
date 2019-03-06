package com.huobi.client.model;

import java.math.BigDecimal;

public class UnitPrice {

  private String currency;
  private BigDecimal amount;

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
