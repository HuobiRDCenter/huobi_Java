package com.huobi.client.model.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.enums.QueryDirection;

/**
 * The request of historical orders.
 */
public class OrdersRequest {

  /**
   * The request of historical orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param state Order state , SUBMITTED etc. (mandatory)
   */
  public OrdersRequest(String symbol, OrderState state) {
    List<OrderState> stateList = new ArrayList<OrderState>();
    if (state != null) {
      stateList.add(state);
    }
    this.symbol = symbol;
    this.states = stateList;
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
    List<OrderState> stateList = new ArrayList<OrderState>();
    if (state != null) {
      stateList.add(state);
    }

    List<OrderType> typeList = new ArrayList<>();
    if (type != null) {
      typeList.add(type);
    }

    this.symbol = symbol;
    this.states = stateList;
    this.types = typeList;
    this.startDate = startDate;
    this.endDate = endDate;
    this.startId = startId;
    this.size = size;
    this.direct = direct;
  }

  public OrdersRequest(String symbol, List<OrderState> stateList,
      List<OrderType> typeList, Date startDate, Date endDate, Long startId, Integer size, QueryDirection direct) {

    this.symbol = symbol;
    this.states = stateList;
    this.types = typeList;
    this.startDate = startDate;
    this.endDate = endDate;
    this.startId = startId;
    this.size = size;
    this.direct = direct;
  }

  public OrdersRequest(HistoricalOrdersRequest request) {
    List<OrderState> stateList = new ArrayList<OrderState>();
    if (request.getState() != null) {
      stateList.add(request.getState());
    }

    List<OrderType> typeList = new ArrayList<>();
    if (request.getType() != null) {
      typeList.add(request.getType());
    }

    this.symbol = request.getSymbol();
    this.states = stateList;
    this.types = typeList;
    this.startDate = request.getStartDate();
    this.endDate = request.getEndDate();
    this.startId = request.getStartId();
    this.size = request.getSize();
  }

  private final String symbol;
  private List<OrderState> states;
  private List<OrderType> types = null;
  private Date startDate = null;
  private Date endDate = null;
  private Long startId = null;
  private Integer size = null;
  private QueryDirection direct;


  public String getTypesString(){
    String typeString = null;
    if (this.getTypes() != null && this.getTypes().size() > 0) {
      StringBuffer typeBuffer = new StringBuffer();
      this.getTypes().forEach(orderType -> {
        typeBuffer.append(orderType.toString()).append(",");
      });

      typeString = typeBuffer.substring(0, typeBuffer.length() - 1);
    }
    return typeString;
  }

  public String getStatesString(){
    String stateString = null;
    if (this.getStates() != null && this.getStates().size() > 0) {
      StringBuffer statesBuffer = new StringBuffer();
      this.getStates().forEach(orderType -> {
        statesBuffer.append(orderType.toString()).append(",");
      });
      stateString = statesBuffer.substring(0, statesBuffer.length() - 1);
    }
    return stateString;
  }


  public String getSymbol() {
    return symbol;
  }

  public List<OrderState> getStates() {
    return states;
  }

  public void setStates(List<OrderState> states) {
    this.states = states;
  }

  public List<OrderType> getTypes() {
    return types;
  }

  public void setTypes(List<OrderType> types) {
    this.types = types;
  }

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

  public Long getStartId() {
    return startId;
  }

  public void setStartId(Long startId) {
    this.startId = startId;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public QueryDirection getDirect() {
    return direct;
  }

  public void setDirect(QueryDirection direct) {
    this.direct = direct;
  }
}
