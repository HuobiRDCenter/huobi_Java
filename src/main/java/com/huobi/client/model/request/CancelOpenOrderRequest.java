package com.huobi.client.model.request;

import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderSide;

/**
 * The request of cancel open orders.
 */
public class CancelOpenOrderRequest {

  /**
   * The request of cancel open orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param accountType Account type. (mandatory)
   */
  public CancelOpenOrderRequest(String symbol, AccountType accountType) {
    this.symbol = symbol;
    this.accountType = accountType;
  }

  /**
   * The request of cancel open orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param accountType Account type. (mandatory)
   * @param side The order side, buy or sell. If no side defined, will cancel all open orders of the
   * account. (optional, can be null)
   * @param size The number of orders to cancel. Range is [1, 100]. Default is 100. (optional, can
   * be null)
   */
  public CancelOpenOrderRequest(
      String symbol, AccountType accountType, OrderSide side, Integer size) {
    this.symbol = symbol;
    this.accountType = accountType;
    this.side = side;
    this.size = size;
  }

  private final AccountType accountType;
  private final String symbol;
  private OrderSide side = null;
  private Integer size = null;

  public AccountType getAccountType() {
    return accountType;
  }

  public String getSymbol() {
    return symbol;
  }

  public OrderSide getSide() {
    return side;
  }

  public Integer getSize() {
    return size;
  }
}
