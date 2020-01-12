package com.huobi.client.model.request;

import java.math.BigDecimal;


public class CrossMarginRepayLoanRequest {

  /**
   * Borrow order id
   */
  private Long orderId;

  /**
   * The amount of currency to repay
   */
  private BigDecimal amount;

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
