package com.huobi.client.model.request;

import com.huobi.client.model.enums.QuerySort;

public class SubUserDepositHistoryRequest {

  private Long subUid;

  private String currency;

  private Long startTime;

  private Long endTime;

  private QuerySort sort;

  private Integer limit;

  private Long fromId;

  public SubUserDepositHistoryRequest(Long subUid) {
    this.subUid = subUid;
  }

  public SubUserDepositHistoryRequest(Long subUid,
      String currency,
      Long startTime,
      Long endTime,
      QuerySort sort,
      Integer limit,
      Long fromId) {
    this.subUid = subUid;
    this.currency = currency;
    this.startTime = startTime;
    this.endTime = endTime;
    this.sort = sort;
    this.limit = limit;
    this.fromId = fromId;
  }

  public Long getSubUid() {
    return subUid;
  }

  public void setSubUid(Long subUid) {
    this.subUid = subUid;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Long getStartTime() {
    return startTime;
  }

  public void setStartTime(Long startTime) {
    this.startTime = startTime;
  }

  public Long getEndTime() {
    return endTime;
  }

  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }

  public QuerySort getSort() {
    return sort;
  }

  public void setSort(QuerySort sort) {
    this.sort = sort;
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
