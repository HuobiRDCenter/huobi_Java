package com.huobi.client.model.request;

import java.math.BigDecimal;

public class AccountTransferRequest {

  private Long fromUser;

  private String fromAccountType;

  private Long fromAccount;

  private Long toUser;

  private String toAccountType;

  private Long toAccount;

  private String currency;

  private BigDecimal amount;

  public Long getFromUser() {
    return fromUser;
  }

  public void setFromUser(Long fromUser) {
    this.fromUser = fromUser;
  }

  public String getFromAccountType() {
    return fromAccountType;
  }

  public void setFromAccountType(String fromAccountType) {
    this.fromAccountType = fromAccountType;
  }

  public Long getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(Long fromAccount) {
    this.fromAccount = fromAccount;
  }

  public Long getToUser() {
    return toUser;
  }

  public void setToUser(Long toUser) {
    this.toUser = toUser;
  }

  public String getToAccountType() {
    return toAccountType;
  }

  public void setToAccountType(String toAccountType) {
    this.toAccountType = toAccountType;
  }

  public Long getToAccount() {
    return toAccount;
  }

  public void setToAccount(Long toAccount) {
    this.toAccount = toAccount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
