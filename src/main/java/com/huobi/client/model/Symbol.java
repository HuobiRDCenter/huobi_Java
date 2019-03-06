package com.huobi.client.model;

/**
 * The Huobi supported symbols.
 */
public class Symbol {

  private String baseCurrency;
  private String quoteCurrency;
  private int pricePrecision;
  private int amountPrecision;
  private String symbolPartition;
  private String symbol;

  /**
   * Get the base currency in a trading symbol.
   *
   * @return The currency.
   */
  public String getBaseCurrency() {
    return baseCurrency;
  }

  /**
   * Get the quote currency in a trading symbol.
   *
   * @return The currency.
   */
  public String getQuoteCurrency() {
    return quoteCurrency;
  }

  /**
   * Get the quote currency precision when quote price (decimal places).
   *
   * @return The precision.
   */
  public int getPricePrecision() {
    return pricePrecision;
  }

  /**
   * Get the base currency precision when quote amount (decimal places).
   *
   * @return The precision.
   */
  public int getAmountPrecision() {
    return amountPrecision;
  }

  /**
   * Get the trading section, possible values: [main，innovation，bifurcation].
   *
   * @return The partition.
   */
  public String getSymbolPartition() {
    return symbolPartition;
  }

  /**
   * Get the symbol, like "btcusdt".
   *
   * @return The symbol.
   */
  public String getSymbol() {
    return symbol;
  }

  public void setBaseCurrency(String baseCurrency) {
    this.baseCurrency = baseCurrency;
  }

  public void setQuoteCurrency(String quoteCurrency) {
    this.quoteCurrency = quoteCurrency;
  }

  public void setPricePrecision(int pricePrecision) {
    this.pricePrecision = pricePrecision;
  }

  public void setAmountPrecision(int amountPrecision) {
    this.amountPrecision = amountPrecision;
  }

  public void setSymbolPartition(String symbolPartition) {
    this.symbolPartition = symbolPartition;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }
}
