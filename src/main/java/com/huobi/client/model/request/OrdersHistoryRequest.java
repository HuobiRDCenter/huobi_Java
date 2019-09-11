package com.huobi.client.model.request;

import java.util.Date;

import lombok.Getter;

import com.huobi.client.model.enums.QueryDirection;

/**
 * The request of historical orders.
 */
@Getter
public class OrdersHistoryRequest {

  public OrdersHistoryRequest(){}
  /**
   * The request of historical orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   */
  public OrdersHistoryRequest(String symbol) {
    this.symbol = symbol;
  }

  /**
   * The request of historical orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param startDate Start date.  (optional, can be null)
   * @param endDate End date.  (optional, can be null)
   * @param size The size of orders.  (optional, can be null)
   */
  public OrdersHistoryRequest(String symbol, Date startDate, Date endDate, Integer size, QueryDirection direct) {
    this.symbol = symbol;
    this.startDate = startDate;
    this.endDate = endDate;
    this.size = size;
    this.direct = direct;
  }


  private String symbol;
  private Date startDate = null;
  private Date endDate = null;
  private Integer size = null;
  private QueryDirection direct;


}
