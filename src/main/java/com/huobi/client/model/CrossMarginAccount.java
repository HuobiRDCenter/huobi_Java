package com.huobi.client.model;

import java.math.BigDecimal;
import java.util.List;

public class CrossMarginAccount {

  private Long id;

  private String type;

  private String state;

  private BigDecimal riskRate;

  private BigDecimal acctBalanceSum;

  private BigDecimal debtBalanceSum;

  private List<Balance> list;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public BigDecimal getRiskRate() {
    return riskRate;
  }

  public void setRiskRate(BigDecimal riskRate) {
    this.riskRate = riskRate;
  }

  public BigDecimal getAcctBalanceSum() {
    return acctBalanceSum;
  }

  public void setAcctBalanceSum(BigDecimal acctBalanceSum) {
    this.acctBalanceSum = acctBalanceSum;
  }

  public BigDecimal getDebtBalanceSum() {
    return debtBalanceSum;
  }

  public void setDebtBalanceSum(BigDecimal debtBalanceSum) {
    this.debtBalanceSum = debtBalanceSum;
  }

  public List<Balance> getList() {
    return list;
  }

  public void setList(List<Balance> list) {
    this.list = list;
  }
}
