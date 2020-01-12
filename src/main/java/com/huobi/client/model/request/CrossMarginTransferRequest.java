package com.huobi.client.model.request;

import java.math.BigDecimal;

import com.huobi.client.model.enums.CrossMarginTransferType;

public class CrossMarginTransferRequest {

  /**
   * transfer type form -> to
   */
  private CrossMarginTransferType type;

  /**
   * Currency
   */
  private String currency;

  /**
   * Transfer Amount
   */
  private BigDecimal amount;

  public CrossMarginTransferType getType() {
    return type;
  }

  public void setType(CrossMarginTransferType type) {
    this.type = type;
  }

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
