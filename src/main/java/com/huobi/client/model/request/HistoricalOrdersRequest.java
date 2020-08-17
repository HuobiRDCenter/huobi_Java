package com.huobi.client.model.request;

import java.util.Date;

import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;

import lombok.Data;

/**
 * The request of historical orders.
 */
@Data
public class HistoricalOrdersRequest {

  /**
   * The request of historical orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param state Order state , SUBMITTED etc. (mandatory)
   */
  public HistoricalOrdersRequest(String symbol, OrderState state) {
    this.symbol = symbol;
    this.state = state;
  }
  /**
   * The request of historical orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param state Order state , SUBMITTED etc. (mandatory)
   * @param type Order type.  (optional, can be null)
   * @param startDate Start date.  (optional, can be null)
   * @param endDate End date.  (optional, can be null)
   * @param startId Start id.  (optional, can be null)
   * @param size The size of orders.  (optional, can be null)
   */
  public HistoricalOrdersRequest(String symbol, OrderState state,
      OrderType type, Date startDate, Date endDate, Long startId, Integer size) {
    this.symbol = symbol;
    this.state = state;
    this.type = type;
    this.startDate = startDate;
    this.endDate = endDate;
    this.startId = startId;
    this.size = size;
  }

  public HistoricalOrdersRequest(String symbol, OrderState state,
      OrderType type, Long startTime, Long endTime, Long startId, Integer size) {
    this.symbol = symbol;
    this.state = state;
    this.type = type;
    this.startTime = startTime;
    this.endTime = endTime;
    this.startId = startId;
    this.size = size;
  }

  /**
   * The request of historical orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param state Order state , SUBMITTED etc. (mandatory)
   * @param type Order type.  (optional, can be null)
   * @param startTime Start timestamp.  (optional, can be null)
   * @param endTime Start timestamp.  (optional, can be null)
   * @param startDate Start date.  (optional, can be null)
   * @param endDate End date.  (optional, can be null)
   * @param startId Start id.  (optional, can be null)
   * @param size The size of orders.  (optional, can be null)
   */
  public HistoricalOrdersRequest(String symbol, OrderState state,
      OrderType type, Long startTime, Long endTime ,Date startDate, Date endDate, Long startId, Integer size) {
    this.symbol = symbol;
    this.state = state;
    this.type = type;
    this.startDate = startDate;
    this.endDate = endDate;
    this.startTime = startTime;
    this.endTime = endTime;
    this.startId = startId;
    this.size = size;
  }

  private final String symbol;
  private final OrderState state;
  private OrderType type = null;
  private Date startDate = null;
  private Date endDate = null;
  private Long startTime = null;
  private Long endTime = null;
  private Long startId = null;
  private Integer size = null;

  public String getSymbol() {
    return symbol;
  }

  public OrderState getState() {
    return state;
  }

  public OrderType getType() {
    return type;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public Long getStartId() {
    return startId;
  }

  public Integer getSize() {
    return size;
  }

  public Long getStartTime() {
    return startTime;
  }

  public Long getEndTime() {
    return endTime;
  }
}
