package com.huobi.client.model;

import java.util.List;

public class SubUserCreationResult {

  private List<SubUserCreationInfo> results;

  public SubUserCreationResult(){}

  public SubUserCreationResult(List<SubUserCreationInfo> results) {
    this.results = results;
  }

  public List<SubUserCreationInfo> getResults() {
    return results;
  }

  public void setResults(List<SubUserCreationInfo> results) {
    this.results = results;
  }
}
