package com.huobi.client.model;

public class CancelResult {

  private Long orderId;

  private String clientOrderId;

  private String errCode;

  private String errMsg;

  private String orderState;

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public void setClientOrderId(String clientOrderId) {
    this.clientOrderId = clientOrderId;
  }

  public String getErrCode() {
    return errCode;
  }

  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

  public String getOrderState() {
    return orderState;
  }

  public void setOrderState(String orderState) {
    this.orderState = orderState;
  }
}
