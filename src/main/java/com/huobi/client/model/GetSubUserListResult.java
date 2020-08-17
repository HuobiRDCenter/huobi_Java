package com.huobi.client.model;

import java.util.List;

public class GetSubUserListResult {

  public GetSubUserListResult() {}

  public GetSubUserListResult(List<SubUserState> userList, Long nextId) {
    this.userList = userList;
    this.nextId = nextId;
  }

  private List<SubUserState> userList;

  private Long nextId;

  public List<SubUserState> getUserList() {
    return userList;
  }

  public void setUserList(List<SubUserState> userList) {
    this.userList = userList;
  }

  public Long getNextId() {
    return nextId;
  }

  public void setNextId(Long nextId) {
    this.nextId = nextId;
  }
}
