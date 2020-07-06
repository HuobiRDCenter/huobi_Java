package com.huobi.client.model.request;

public class SubUserApiKeyModificationRequest {

  private Long subUid;

  private String accessKey;

  private String note;

  private String permission;

  private String ipAddresses;

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

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public String getIpAddresses() {
    return ipAddresses;
  }

  public void setIpAddresses(String ipAddresses) {
    this.ipAddresses = ipAddresses;
  }
}
