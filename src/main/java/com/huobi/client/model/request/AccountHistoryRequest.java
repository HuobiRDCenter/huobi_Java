package com.huobi.client.model.request;

import java.util.List;


import com.huobi.client.model.enums.QuerySort;
import com.huobi.client.model.enums.TransactType;

public class AccountHistoryRequest {

  /**
   * user business account id
   */
  private Long accountId;

  /**
   * currency
   */
  private String currency;

  /**
   * Amount change types (multiple selection allowed)
   */
  private List<TransactType> typeList;

  /**
   * Far point of time of the query window (unix time in millisecond). Searching based on transact-time. The maximum size of the query window is 1 hour. The query window can be shifted within 30 days.
   */
  private Long startTime;

  /**
   * Near point of time of the query window (unix time in millisecond). Searching based on transact-time. The maximum size of the query window is 1 hour. The query window can be shifted within 30 days.
   */
  private Long endTime;

  /**
   * Sorting order	asc or desc
   */
  private QuerySort sort;

  /**
   * Maximum number of items in each response
   */
  private Integer size;


  public String getTypeString(){
    String typeString = null;
    if (this.getTypeList() != null && this.getTypeList().size() > 0) {
      StringBuffer typesBuffer = new StringBuffer();
      this.getTypeList().forEach(type -> {
        typesBuffer.append(type.getCode()).append(",");
      });
      typeString = typesBuffer.substring(0, typesBuffer.length() - 1);
    }
    return typeString;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public List<TransactType> getTypeList() {
    return typeList;
  }

  public void setTypeList(List<TransactType> typeList) {
    this.typeList = typeList;
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

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }
}
