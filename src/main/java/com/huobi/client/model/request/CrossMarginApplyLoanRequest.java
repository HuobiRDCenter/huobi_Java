package com.huobi.client.model.request;

import java.math.BigDecimal;


public class CrossMarginApplyLoanRequest {

  /**
   * Currency
   */
  private String currency;

  /**
   * The amount of currency to borrow
   */
  private BigDecimal amount;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
