package com.huobi.client.model;

import java.math.BigDecimal;

/**
 * An depth entry consisting of price and amount.
 */
public class DepthEntry {

  private BigDecimal price;
  private BigDecimal amount;

  /**
   * Get the price of the depth.
   *
   * @return The price.
   */
  public BigDecimal getPrice() {
    return this.price;
  }

  /**
   * Get the amount of the depth.
   *
   * @return The amount.
   */
  public BigDecimal getAmount() {
    return this.amount;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
