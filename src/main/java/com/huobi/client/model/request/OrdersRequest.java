package com.huobi.client.model.request;

import java.util.Date;

import lombok.Getter;

import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.enums.QueryDirection;

/**
 * The request of historical orders.
 */
@Getter
public class OrdersRequest {

  /**
   * The request of historical orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param state Order state , SUBMITTED etc. (mandatory)
   */
  public OrdersRequest(String symbol, OrderState state) {
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
  public OrdersRequest(String symbol, OrderState state,
      OrderType type, Date startDate, Date endDate, Long startId, Integer size, QueryDirection direct) {
    this.symbol = symbol;
    this.state = state;
    this.type = type;
    this.startDate = startDate;
    this.endDate = endDate;
    this.startId = startId;
    this.size = size;
    this.direct = direct;
  }


  public OrdersRequest(HistoricalOrdersRequest request) {
    this.symbol = request.getSymbol();
    this.state = request.getState();
    this.type = request.getType();
    this.startDate = request.getStartDate();
    this.endDate = request.getEndDate();
    this.startId = request.getStartId();
    this.size = request.getSize();
  }

  private final String symbol;
  private final OrderState state;
  private OrderType type = null;
  private Date startDate = null;
  private Date endDate = null;
  private Long startId = null;
  private Integer size = null;
  private QueryDirection direct;


}
