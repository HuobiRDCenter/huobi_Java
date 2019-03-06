package com.huobi.client.model.request;

import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderType;
import java.math.BigDecimal;

/**
 * The request of placing a new order.
 */
public class NewOrderRequest {

  /**
   * The request of placing a new order.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param accountType Account type. (mandatory)
   * @param type The order type. (mandatory)
   * @param amount The amount to buy (quote currency) or to sell (base currency). (mandatory)
   * @param price The limit price of limit order, only needed for limit order. (mandatory for
   * buy-limit, sell-limit, buy-limit-maker and sell-limit-maker)
   */
  public NewOrderRequest(String symbol, AccountType accountType, OrderType type, BigDecimal amount,
      BigDecimal price) {
    this.accountType = accountType;
    this.symbol = symbol;
    this.amount = amount;
    this.price = price;
    this.type = type;
  }

  private final AccountType accountType;

  private final String symbol;

  private final BigDecimal amount;

  private final BigDecimal price;

  private final OrderType type;

  public AccountType getAccountType() {
    return accountType;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public OrderType getType() {
    return type;
  }
}
