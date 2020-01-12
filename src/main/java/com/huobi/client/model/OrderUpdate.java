package com.huobi.client.model;

import java.math.BigDecimal;


import com.huobi.client.model.enums.DealRole;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;

public class OrderUpdate {

  private Long matchId;

  private Long orderId;

  private String symbol;

  private OrderState state;

  private OrderType type;

  private DealRole role;

  private BigDecimal price;

  private BigDecimal filledAmount;

  private BigDecimal filledCashAmount;

  private BigDecimal unfilledAmount;

  private String clientOrderId;

  public Long getMatchId() {
    return matchId;
  }

  public void setMatchId(Long matchId) {
    this.matchId = matchId;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public OrderState getState() {
    return state;
  }

  public void setState(OrderState state) {
    this.state = state;
  }

  public OrderType getType() {
    return type;
  }

  public void setType(OrderType type) {
    this.type = type;
  }

  public DealRole getRole() {
    return role;
  }

  public void setRole(DealRole role) {
    this.role = role;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getFilledAmount() {
    return filledAmount;
  }

  public void setFilledAmount(BigDecimal filledAmount) {
    this.filledAmount = filledAmount;
  }

  public BigDecimal getFilledCashAmount() {
    return filledCashAmount;
  }

  public void setFilledCashAmount(BigDecimal filledCashAmount) {
    this.filledCashAmount = filledCashAmount;
  }

  public BigDecimal getUnfilledAmount() {
    return unfilledAmount;
  }

  public void setUnfilledAmount(BigDecimal unfilledAmount) {
    this.unfilledAmount = unfilledAmount;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public void setClientOrderId(String clientOrderId) {
    this.clientOrderId = clientOrderId;
  }
}
