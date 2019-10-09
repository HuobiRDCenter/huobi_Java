package com.huobi.client.model;

import java.math.BigDecimal;

import com.huobi.client.impl.utils.JsonWrapper;

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

  public static UnitPrice parse(JsonWrapper item) {
    UnitPrice unitPrice = new UnitPrice();
    unitPrice.setCurrency(item.getString("currency"));
    unitPrice.setAmount(item.getBigDecimal("amount"));
    return unitPrice;
  }
}
