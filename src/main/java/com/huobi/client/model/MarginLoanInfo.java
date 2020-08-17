package com.huobi.client.model;

import java.util.List;

public class MarginLoanInfo {

  private String symbol;

  private List<MarginLoanCurrencyInfo> currencies;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public List<MarginLoanCurrencyInfo> getCurrencies() {
    return currencies;
  }

  public void setCurrencies(List<MarginLoanCurrencyInfo> currencies) {
    this.currencies = currencies;
  }
}
