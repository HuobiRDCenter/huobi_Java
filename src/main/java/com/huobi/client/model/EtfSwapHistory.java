package com.huobi.client.model;

import com.huobi.client.model.enums.EtfSwapType;
import java.math.BigDecimal;
import java.util.List;

public class EtfSwapHistory {

  private long createdTimestamp;
  private String currency;
  private BigDecimal amount;
  private EtfSwapType type;
  private int status;
  private BigDecimal rate;
  private BigDecimal fee;
  private BigDecimal pointCardAmount;
  private List<UnitPrice> usedCurrencyList;
  private List<UnitPrice> obtainCurrencyList;

  public long getCreatedTimestamp() {
    return createdTimestamp;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public EtfSwapType getType() {
    return type;
  }

  public int getStatus() {
    return status;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getPointCardAmount() {
    return pointCardAmount;
  }

  public List<UnitPrice> getUsedCurrencyList() {
    return usedCurrencyList;
  }

  public List<UnitPrice> getObtainCurrencyList() {
    return obtainCurrencyList;
  }

  public void setCreatedTimestamp(long createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setType(EtfSwapType type) {
    this.type = type;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  public void setPointCardAmount(BigDecimal pointCardAmount) {
    this.pointCardAmount = pointCardAmount;
  }

  public void setUsedCurrencyList(List<UnitPrice> usedCurrencyList) {
    this.usedCurrencyList = usedCurrencyList;
  }

  public void setObtainCurrencyList(List<UnitPrice> obtainCurrencyList) {
    this.obtainCurrencyList = obtainCurrencyList;
  }
}
