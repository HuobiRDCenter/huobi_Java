package com.huobi.client.model.request;

import java.util.Date;


import com.huobi.client.model.enums.LoanOrderStates;
import com.huobi.client.model.enums.QueryDirection;

public class CrossMarginLoanOrderRequest {

  /**
   * Search starts date, in format yyyy-mm-dd
   */
  private Date startDate;

  /**
   * Search ends date, in format yyyy-mm-dd
   */
  private Date endDate;

  /**
   * Currency
   */
  private String currency;

  /**
   * Order status	: created,accrual,cleared,invalid
   */
  private LoanOrderStates state;

  /**
   * Search order id to begin with
   */
  private Long from;

  /**
   * Search direction when 'from' is used	 : next, prev
   */
  private QueryDirection direction;

  /**
   * The number of orders to return
   */
  private Integer size;

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public LoanOrderStates getState() {
    return state;
  }

  public void setState(LoanOrderStates state) {
    this.state = state;
  }

  public Long getFrom() {
    return from;
  }

  public void setFrom(Long from) {
    this.from = from;
  }

  public QueryDirection getDirection() {
    return direction;
  }

  public void setDirection(QueryDirection direction) {
    this.direction = direction;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }
}
