package com.huobi.client.model.event;

import java.util.List;

import com.huobi.client.model.Order;

/**
 * The order received by subscription of order list request.
 */
public class OrderListEvent {

  private String topic;

  private Long timestamp;

  private List<Order> orderList = null;

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public List<Order> getOrderList() {
    return orderList;
  }

  public void setOrderList(List<Order> orderList) {
    this.orderList = orderList;
  }
}
