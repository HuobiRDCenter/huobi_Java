package com.huobi.client.model;

import java.util.List;

public class GetWithdrawAddressResult {

  private List<WithdrawAddress> list;

  private Long nextId;

  public GetWithdrawAddressResult() {}

  public GetWithdrawAddressResult(List<WithdrawAddress> list, Long nextId) {
    this.list = list;
    this.nextId = nextId;
  }

  public List<WithdrawAddress> getList() {
    return list;
  }

  public void setList(List<WithdrawAddress> list) {
    this.list = list;
  }

  public Long getNextId() {
    return nextId;
  }

  public void setNextId(Long nextId) {
    this.nextId = nextId;
  }
}
