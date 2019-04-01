package com.huobi.client.model;

import com.huobi.client.model.enums.AccountState;
import com.huobi.client.model.enums.AccountType;
import java.math.BigDecimal;
import java.util.List;

/**
 * The balance of the margin loan account.
 */
public class MarginBalanceDetail {

  private long id;
  private String symbol;
  private AccountState state;
  private AccountType type;
  private BigDecimal riskRate;
  private BigDecimal flPrice;
  private String flType;
  private List<Balance> subAccountBalance;


  /**
   * The id of margin account.
   *
   * @return The id.
   */
  public long getId() {
    return id;
  }

  /**
   * The margin loan pair, e.g. btcusdt, bccbtc
   *
   * @return
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * The loan state.
   *
   * @return The state.
   */
  public AccountState getState() {
    return state;
  }


  public AccountType getType() {
    return type;
  }

  /**
   * The risk rate
   *
   * @return The rate value.
   */
  public BigDecimal getRiskRate() {
    return riskRate;
  }

  /**
   * The price which triggers closeout.
   *
   * @return The price value.
   */
  public BigDecimal getFlPrice() {
    return flPrice;
  }


  public String getFlType() {
    return flType;
  }

  /**
   * The list of margin accounts and their details.
   *
   * @return The balance list.
   */
  public List<Balance> getSubAccountBalance() {
    return subAccountBalance;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setState(AccountState state) {
    this.state = state;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

  public void setRiskRate(BigDecimal riskRate) {
    this.riskRate = riskRate;
  }

  public void setFlPrice(BigDecimal flPrice) {
    this.flPrice = flPrice;
  }

  public void setFlType(String flType) {
    this.flType = flType;
  }

  public void setSubAccountBalance(List<Balance> subAccountBalance) {
    this.subAccountBalance = subAccountBalance;
  }
}
