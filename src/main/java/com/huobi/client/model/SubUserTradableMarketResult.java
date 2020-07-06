package com.huobi.client.model;

import java.util.List;

public class SubUserTradableMarketResult {

  private List<SubUserTradableMarketState> list;

  public SubUserTradableMarketResult() {}

  public SubUserTradableMarketResult(List<SubUserTradableMarketState> list) {
    this.list = list;
  }

  public List<SubUserTradableMarketState> getList() {
    return list;
  }

  public void setList(List<SubUserTradableMarketState> list) {
    this.list = list;
  }
}
