package com.huobi.client.model.request;

import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.enums.StopOrderOperator;

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
   * @param price The limit price of limit order, only needed for limit order. (mandatory for buy-limit, sell-limit, buy-limit-maker and
   * sell-limit-maker)
   */
  public NewOrderRequest(String symbol, AccountType accountType, OrderType type, BigDecimal amount,
      BigDecimal price) {
    this.accountType = accountType;
    this.symbol = symbol;
    this.amount = amount;
    this.price = price;
    this.type = type;
  }

  public NewOrderRequest(String symbol, AccountType accountType, OrderType type, BigDecimal amount,
      BigDecimal price, String clientOrderId, BigDecimal stopPrice, StopOrderOperator operator) {
    this.accountType = accountType;
    this.symbol = symbol;
    this.amount = amount;
    this.price = price;
    this.type = type;
    this.clientOrderId = clientOrderId;
    this.stopPrice = stopPrice;
    this.operator = operator;
  }

  public static NewOrderRequest spotBuyLimit(String symbol, BigDecimal price, BigDecimal amount) {
    return spotBuyLimit(null, symbol, price, amount);
  }

  public static NewOrderRequest spotBuyLimit(String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return new NewOrderRequest(symbol, AccountType.SPOT, OrderType.BUY_LIMIT, amount, price, clientOrderId, null, null);
  }

  public static NewOrderRequest spotSellLimit(String symbol, BigDecimal price, BigDecimal amount) {
    return spotSellLimit(null, symbol, price, amount);
  }

  public static NewOrderRequest spotSellLimit(String clientOrderId, String symbol, BigDecimal price, BigDecimal amount) {
    return new NewOrderRequest(symbol, AccountType.SPOT, OrderType.SELL_LIMIT, amount, price, clientOrderId, null, null);
  }

  public static NewOrderRequest spotBuyMarket(String symbol, BigDecimal amount) {
    return spotBuyMarket(null, symbol, amount);
  }

  public static NewOrderRequest spotBuyMarket(String clientOrderId, String symbol, BigDecimal amount) {
    return new NewOrderRequest(symbol, AccountType.SPOT, OrderType.BUY_MARKET, amount, null, clientOrderId, null, null);
  }

  public static NewOrderRequest spotSellMarket(String symbol, BigDecimal amount) {
    return spotSellMarket(null, symbol, amount);
  }

  public static NewOrderRequest spotSellMarket(String clientOrderId, String symbol, BigDecimal amount) {
    return new NewOrderRequest(symbol, AccountType.SPOT, OrderType.SELL_MARKET, amount, null, clientOrderId, null, null);
  }

  public static NewOrderRequest spotBuyStopOrder(String symbol, BigDecimal stopPrice, BigDecimal price, BigDecimal amount,
      StopOrderOperator operator) {
    return spotBuyStopOrder(null, symbol, stopPrice, price, amount, operator);
  }

  public static NewOrderRequest spotBuyStopOrder(String clientOrderId, String symbol, BigDecimal stopPrice, BigDecimal price, BigDecimal amount,
      StopOrderOperator operator) {
    return new NewOrderRequest(symbol, AccountType.SPOT, OrderType.BUY_STOP_LIMIT, amount, price, clientOrderId, stopPrice, operator);
  }

  public static NewOrderRequest spotSellStopOrder(String symbol, BigDecimal stopPrice, BigDecimal price, BigDecimal amount,
      StopOrderOperator operator) {
    return spotSellStopOrder(null, symbol, stopPrice, price, amount, operator);
  }

  public static NewOrderRequest spotSellStopOrder(String clientOrderId, String symbol, BigDecimal stopPrice, BigDecimal price, BigDecimal amount,
      StopOrderOperator operator) {
    return new NewOrderRequest(symbol, AccountType.SPOT, OrderType.SELL_STOP_LIMIT, amount, price, clientOrderId, stopPrice, operator);
  }




  private AccountType accountType;

  private String symbol;

  private BigDecimal amount;

  private BigDecimal price;

  private OrderType type;

  private String clientOrderId;

  private BigDecimal stopPrice;

  private StopOrderOperator operator;

  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public OrderType getType() {
    return type;
  }

  public void setType(OrderType type) {
    this.type = type;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public void setClientOrderId(String clientOrderId) {
    this.clientOrderId = clientOrderId;
  }

  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  public void setStopPrice(BigDecimal stopPrice) {
    this.stopPrice = stopPrice;
  }

  public StopOrderOperator getOperator() {
    return operator;
  }

  public void setOperator(StopOrderOperator operator) {
    this.operator = operator;
  }
}
