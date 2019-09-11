package com.huobi.client.model.request;

import lombok.Getter;

import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderSide;
import com.huobi.client.model.enums.QueryDirection;

/**
 * The request of get open orders.
 */
@Getter
public class OpenOrderRequest {

  /**
   * The request of get open orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param accountType The order side, buy or sell. If no side defined, will return all open orders of the account. (mandatory)
   */
  public OpenOrderRequest(String symbol, AccountType accountType) {
    this.symbol = symbol;
    this.accountType = accountType;
  }

  /**
   * The request of get open orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param accountType The order side, buy or sell. If no side defined, will return all open orders of the account. (mandatory)
   * @param side The order side, buy or sell. If no side defined, will return all open orders of the account. (optional can be null)
   * @param size The number of orders to return. Range is [1, 500]. Default is 10. (optional, can be null)
   */
  public OpenOrderRequest(String symbol, AccountType accountType,
      OrderSide side,
      Integer size) {
    this.symbol = symbol;
    this.accountType = accountType;
    this.size = size;
    this.side = side;
  }

  public OpenOrderRequest(String symbol,
      AccountType accountType,
      OrderSide side,
      Integer size,
      Long from,
      QueryDirection direct) {
    this.symbol = symbol;
    this.accountType = accountType;
    this.size = size;
    this.side = side;
    this.from = from;
    if (direct != null) {
      this.direct = direct.toString();
    }
  }

  private final String symbol;

  private final AccountType accountType;

  private Integer size = null;

  private OrderSide side = null;

  private String direct = null;

  private Long from = null;

}
