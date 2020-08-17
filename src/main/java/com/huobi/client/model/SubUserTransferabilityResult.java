package com.huobi.client.model;

import java.util.List;

public class SubUserTransferabilityResult {

  private List<SubUserTransferabilityState> list;

  public SubUserTransferabilityResult() {}

  public SubUserTransferabilityResult(List<SubUserTransferabilityState> list) {
    this.list = list;
  }

  public List<SubUserTransferabilityState> getList() {
    return list;
  }

  public void setList(List<SubUserTransferabilityState> list) {
    this.list = list;
  }
}
