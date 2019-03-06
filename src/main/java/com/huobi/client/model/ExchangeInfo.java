package com.huobi.client.model;

import java.util.List;

/**
 * Get the Huobi supported the symbols and currencies.
 */
public class ExchangeInfo {

  private List<Symbol> symbolList;
  private List<String> currencies;

  /**
   * Get the symbol list.
   *
   * @return The symbol list, see {@link Symbol}
   */
  public List<Symbol> getSymbolList() {
    return symbolList;
  }
  
  /**
   * Get the currency list.
   *
   * @return The currency list.
   */
  public List<String> getCurrencies() {
    return currencies;
  }

  public void setCurrencies(List<String> currencies) {
    this.currencies = currencies;
  }

  public void setSymbolList(List<Symbol> symbolList) {
    this.symbolList = symbolList;
  }
}
