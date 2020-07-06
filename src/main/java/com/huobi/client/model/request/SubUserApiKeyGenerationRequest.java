package com.huobi.client.model.request;

public class SubUserApiKeyGenerationRequest {

  private String otpToken;

  private Long subUid;

  private String note;

  private String permission;

  private String ipAddresses;

  public String getOtpToken() {
    return otpToken;
  }

  public void setOtpToken(String otpToken) {
    this.otpToken = otpToken;
  }

  public Long getSubUid() {
    return subUid;
  }

  public void setSubUid(Long subUid) {
    this.subUid = subUid;
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
