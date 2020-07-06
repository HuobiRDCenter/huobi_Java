package com.huobi.client.model.request;

public class GetWithdrawAddressRequest {

  private String currency;

  private String chain;

  private String note;

  private Integer limit;

  private Long fromId;

  public GetWithdrawAddressRequest() {}

  public GetWithdrawAddressRequest(String currency) {
    this.currency = currency;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getChain() {
    return chain;
  }

  public void setChain(String chain) {
    this.chain = chain;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Long getFromId() {
    return fromId;
  }

  public void setFromId(Long fromId) {
    this.fromId = fromId;
  }
}
