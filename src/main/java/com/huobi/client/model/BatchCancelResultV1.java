package com.huobi.client.model;

import java.util.List;

public class BatchCancelResultV1 {

  private List<String> successList;

  private List<CancelResult> failedList;

  public List<String> getSuccessList() {
    return successList;
  }

  public void setSuccessList(List<String> successList) {
    this.successList = successList;
  }

  public List<CancelResult> getFailedList() {
    return failedList;
  }

  public void setFailedList(List<CancelResult> failedList) {
    this.failedList = failedList;
  }
}
