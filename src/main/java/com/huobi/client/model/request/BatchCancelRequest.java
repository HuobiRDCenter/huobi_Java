package com.huobi.client.model.request;

import java.util.List;

public class BatchCancelRequest {

  private String symbol;

  private List<Long> orderIds;

  private List<String> clientOrderIds;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public List<Long> getOrderIds() {
    return orderIds;
  }

  public void setOrderIds(List<Long> orderIds) {
    this.orderIds = orderIds;
  }

  public List<String> getClientOrderIds() {
    return clientOrderIds;
  }

  public void setClientOrderIds(List<String> clientOrderIds) {
    this.clientOrderIds = clientOrderIds;
  }
}
