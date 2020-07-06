package com.huobi.client.model.request;

public class GetSubUserAccountListRequest {

  private Long subUid;

  public GetSubUserAccountListRequest() {}

  public GetSubUserAccountListRequest(Long subUid) {
    this.subUid = subUid;
  }

  public Long getSubUid() {
    return subUid;
  }

  public void setSubUid(Long subUid) {
    this.subUid = subUid;
  }
}
