package com.huobi.client.model.request;

import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderType;
import java.util.Date;


/**
 * The criteria of getting match result
 */
public class MatchResultRequest {

  /**
   * The criteria of getting match result.
   *
   * @param symbol The symbol, like "btcusdt" (mandatory).
   */
  public MatchResultRequest(String symbol) {
    this.symbol = symbol;
  }

  /**
   * The criteria of getting match result.
   *
   * @param symbol The symbol, like "btcusdt" (mandatory).
   * @param type The types of order to include in the search (optional, can be null).
   * @param startDate Search starts date (optional, can be null).
   * @param endDate Search ends date (optional, can be null).
   * @param size The number of orders to return, range [1-100] default is 100. (optional, can be
   * null).
   * @param from Search order id to begin with. (optional, can be null).
   */
  public MatchResultRequest(String symbol, OrderType type, Date startDate, Date endDate,
      Integer size, String from) {
    this.symbol = symbol;
    this.type = type;
    this.startDate = startDate;
    this.endDate = endDate;
    this.size = size;
    this.from = from;
  }

  private final String symbol;

  private OrderType type = null;

  private Date startDate = null;

  private Date endDate = null;

  private Integer size = null;

  private String from = null;

  public String getSymbol() {
    return symbol;
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

  public Integer getSize() {
    return size;
  }

  public String getFrom() {
    return from;
  }
}
