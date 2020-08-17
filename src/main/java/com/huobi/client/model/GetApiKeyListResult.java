package com.huobi.client.model;

import java.util.List;

public class GetApiKeyListResult {

  private List<ApiKeyInfo> list;

  public GetApiKeyListResult() {}

  public GetApiKeyListResult(List<ApiKeyInfo> list) {
    this.list = list;
  }

  public List<ApiKeyInfo> getList() {
    return list;
  }

  public void setList(List<ApiKeyInfo> list) {
    this.list = list;
  }
}
