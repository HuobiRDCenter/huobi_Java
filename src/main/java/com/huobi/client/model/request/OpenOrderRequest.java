package com.huobi.client.model.request;


import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderSide;
import com.huobi.client.model.enums.QueryDirection;

/**
 * The request of get open orders.
 */
public class OpenOrderRequest {

  /**
   * The request of get open orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param accountType The account type, margin,otc,point,spot (mandatory)
   */
  public OpenOrderRequest(String symbol, AccountType accountType) {
    this.symbol = symbol;
    this.accountType = accountType;
  }

  /**
   * The request of get open orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param accountType The account type, margin,otc,point,spot (mandatory)
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

  /**
   *
   * @param symbol  The symbol, like "btcusdt". (mandatory)
   * @param accountType  The account type, margin,otc,point,spot (mandatory)
   * @param side The order side, buy or sell. If no side defined, will return all open orders of the account. (optional can be null)
   * @param size The number of orders to return. Range is [1, 500]. Default is 10. (optional, can be null)
   * @param from The query start id.
   * @param direct The direction of query .pre or next
   */
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

  public String getSymbol() {
    return symbol;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public OrderSide getSide() {
    return side;
  }

  public void setSide(OrderSide side) {
    this.side = side;
  }

  public String getDirect() {
    return direct;
  }

  public void setDirect(String direct) {
    this.direct = direct;
  }

  public Long getFrom() {
    return from;
  }

  public void setFrom(Long from) {
    this.from = from;
  }
}
