package com.huobi.client.model.request;

public class SubUserCreationParam {

  private String userName;

  private String note;

  public SubUserCreationParam(String userName, String note) {
    this.userName = userName;
    this.note = note;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }
}
