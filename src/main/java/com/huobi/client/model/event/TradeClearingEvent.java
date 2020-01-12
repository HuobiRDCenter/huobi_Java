package com.huobi.client.model.event;

import com.huobi.client.model.TradeClearing;

public class TradeClearingEvent {

  private String ch;

  private TradeClearing data;

  public String getCh() {
    return ch;
  }

  public void setCh(String ch) {
    this.ch = ch;
  }

  public TradeClearing getData() {
    return data;
  }

  public void setData(TradeClearing data) {
    this.data = data;
  }
}
