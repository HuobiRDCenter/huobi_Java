package com.huobi.client.model.request;

public class SubUserApiKeyDeletionRequest {

  private Long subUid;

  private String accessKey;

  public SubUserApiKeyDeletionRequest() {}

  public SubUserApiKeyDeletionRequest(Long subUid, String accessKey) {
    this.subUid = subUid;
    this.accessKey = accessKey;
  }

  public Long getSubUid() {
    return subUid;
  }

  public void setSubUid(Long subUid) {
    this.subUid = subUid;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }
}
