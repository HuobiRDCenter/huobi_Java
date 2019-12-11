package com.huobi.client.model;

import java.math.BigDecimal;

public class TradeClearing {

  /**
   * Trading symbol
   */
  private String symbol;

  /**
   * Trade ID
   */
  private Long tradeId;

  /**
   * Order ID
   */
  private Long orderId;

  /**
   * Trade price
   */
  private BigDecimal tradePrice;

  /**
   * Trade volume
   */
  private BigDecimal tradeVolume;

  /**
   * Order side, valid value: buy,sell
   */
  private String orderSide;

  /**
   * Order type, valid value: buy-market, sell-market,buy-limit,sell-limit,buy-ioc,sell-ioc,buy-limit-maker,sell-limit-maker,buy-stop-limit,sell-stop-limit
   */
  private String orderType;

  /**
   * Aggressor or not, valid value: true, false
   */
  private Boolean aggressor;

  /**
   * Trade time, unix time in millisecond
   */
  private Long tradeTime;

  /**
   * Transaction fee
   */
  private BigDecimal transactFee;

  /**
   * Transaction fee deduction
   */
  private BigDecimal feeDeduct;

  /**
   * Transaction fee deduction type, valid value: ht,point
   */
  private String feeDeductType;


  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Long getTradeId() {
    return tradeId;
  }

  public void setTradeId(Long tradeId) {
    this.tradeId = tradeId;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public BigDecimal getTradePrice() {
    return tradePrice;
  }

  public void setTradePrice(BigDecimal tradePrice) {
    this.tradePrice = tradePrice;
  }

  public BigDecimal getTradeVolume() {
    return tradeVolume;
  }

  public void setTradeVolume(BigDecimal tradeVolume) {
    this.tradeVolume = tradeVolume;
  }

  public String getOrderSide() {
    return orderSide;
  }

  public void setOrderSide(String orderSide) {
    this.orderSide = orderSide;
  }

  public Boolean getAggressor() {
    return aggressor;
  }

  public void setAggressor(Boolean aggressor) {
    this.aggressor = aggressor;
  }

  public Long getTradeTime() {
    return tradeTime;
  }

  public void setTradeTime(Long tradeTime) {
    this.tradeTime = tradeTime;
  }

  public BigDecimal getTransactFee() {
    return transactFee;
  }

  public void setTransactFee(BigDecimal transactFee) {
    this.transactFee = transactFee;
  }

  public BigDecimal getFeeDeduct() {
    return feeDeduct;
  }

  public void setFeeDeduct(BigDecimal feeDeduct) {
    this.feeDeduct = feeDeduct;
  }

  public String getFeeDeductType() {
    return feeDeductType;
  }

  public void setFeeDeductType(String feeDeductType) {
    this.feeDeductType = feeDeductType;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }
}
