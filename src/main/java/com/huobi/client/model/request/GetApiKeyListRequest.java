package com.huobi.client.model.request;

public class GetApiKeyListRequest {

  private Long uid;

  private String accessKey;

  public GetApiKeyListRequest() {}

  public GetApiKeyListRequest(Long uid) {
    this.uid = uid;
  }

  public GetApiKeyListRequest(Long uid, String accessKey) {
    this.uid = uid;
    this.accessKey = accessKey;
  }

  public Long getUid() {
    return uid;
  }

  public void setUid(Long uid) {
    this.uid = uid;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }
}
