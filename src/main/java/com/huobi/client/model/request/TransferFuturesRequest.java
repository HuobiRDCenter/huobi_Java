package com.huobi.client.model.request;

import java.math.BigDecimal;


import com.huobi.client.model.enums.TransferFuturesDirection;

public class TransferFuturesRequest {

  private String currency;

  private BigDecimal amount;

  private TransferFuturesDirection direction;

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

  public TransferFuturesDirection getDirection() {
    return direction;
  }

  public void setDirection(TransferFuturesDirection direction) {
    this.direction = direction;
  }
}
