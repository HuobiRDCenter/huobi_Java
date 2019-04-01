package com.huobi.client.model.request;

import com.huobi.client.model.enums.LoanOrderStates;
import com.huobi.client.model.enums.QueryDirection;
import java.util.Date;

/**
 * The criteria of getting margin orders.
 */
public class LoanOrderRequest {

  /**
   * The criteria of getting margin orders.
   *
   * @param symbol The symbol, like "btcusdt" (mandatory).
   */
  public LoanOrderRequest(String symbol) {
    this.symbol = symbol;
  }

  /**
   * The criteria of getting margin orders.
   *
   * @param symbol The symbol, like "btcusdt" (mandatory).
   * @param startDate The search starts date (optional, can be null).
   * @param endDate The search end date (optional, can be null).
   * @param states The loan order states, it could be created, accrual, cleared or invalid
   * (optional, can be null).
   * @param fromId Search order id to begin with (optional, can be null).
   * @param size The number of orders to return. (optional, can be null).
   * @param direction The query direction, prev or next. (optional, can be null)
   */
  public LoanOrderRequest(String symbol, Date startDate, Date endDate,
      LoanOrderStates states, Long fromId, Long size, QueryDirection direction) {
    this.symbol = symbol;
    this.startDate = startDate;
    this.endDate = endDate;
    this.states = states;
    this.fromId = fromId;
    this.size = size;
    this.direction = direction;
  }

  private final String symbol;

  private Date startDate = null;

  private Date endDate = null;

  private LoanOrderStates states = null;

  private Long fromId = null;

  private Long size = null;

  private QueryDirection direction = null;

  public String getSymbol() {
    return symbol;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public LoanOrderStates getStates() {
    return states;
  }

  public Long getFromId() {
    return fromId;
  }

  public Long getSize() {
    return size;
  }

  public QueryDirection getDirection() {
    return direction;
  }
}
