package com.huobi.client.model;

import java.util.List;

public class GetSubUserAccountListResult {

  private Long uid;

  private List<SubUserAccountInfo> list;

  public Long getUid() {
    return uid;
  }

  public void setUid(Long uid) {
    this.uid = uid;
  }

  public List<SubUserAccountInfo> getList() {
    return list;
  }

  public void setList(List<SubUserAccountInfo> list) {
    this.list = list;
  }
}
