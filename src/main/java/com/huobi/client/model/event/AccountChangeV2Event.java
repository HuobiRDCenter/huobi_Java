package com.huobi.client.model.event;

import com.huobi.client.model.AccountChangeV2;

public class AccountChangeV2Event {

  private String ch;

  private AccountChangeV2 accountChange;

  public String getCh() {
    return ch;
  }

  public void setCh(String ch) {
    this.ch = ch;
  }

  public AccountChangeV2 getAccountChange() {
    return accountChange;
  }

  public void setAccountChange(AccountChangeV2 accountChange) {
    this.accountChange = accountChange;
  }
}
