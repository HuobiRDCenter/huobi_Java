package com.huobi.client.model.request;

import java.util.List;

public class SubUserCreationRequest {

  private List<SubUserCreationParam> userList;

  public SubUserCreationRequest(List<SubUserCreationParam> userList) {
    this.userList = userList;
  }

  public List<SubUserCreationParam> getUserList() {
    return userList;
  }

  public void setUserList(List<SubUserCreationParam> userList) {
    this.userList = userList;
  }
}
