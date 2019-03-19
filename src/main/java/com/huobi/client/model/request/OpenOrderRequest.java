package com.huobi.client.model.request;

import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderSide;

/**
 * The request of get open orders.
 */
public class OpenOrderRequest {

  /**
   * The request of get open orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param accountType The order side, buy or sell. If no side defined, will return all open orders
   * of the account. (mandatory)
   */
  public OpenOrderRequest(String symbol, AccountType accountType) {
    this.symbol = symbol;
    this.accountType = accountType;
  }

  /**
   * The request of get open orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param accountType The order side, buy or sell. If no side defined, will return all open orders
   * of the account. (mandatory)
   * @param side The order side, buy or sell. If no side defined, will return all open orders of the
   * account. (optional can be null)
   * @param size The number of orders to return. Range is [1, 500]. Default is 10. (optional, can be
   * null)
   */
  public OpenOrderRequest(String symbol, AccountType accountType,
      OrderSide side,
      Integer size) {
    this.symbol = symbol;
    this.accountType = accountType;
    this.size = size;
    this.side = side;
  }

  private final String symbol;

  private final AccountType accountType;

  private Integer size = null;

  private OrderSide side = null;

  public String getSymbol() {
    return symbol;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public Integer getSize() {
    return size;
  }

  public OrderSide getSide() {
    return side;
  }
}
