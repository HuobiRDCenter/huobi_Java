package com.huobi.client.model;

import com.huobi.client.impl.AccountsInfoMap;
import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.enums.StopOrderOperator;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import lombok.ToString;

/**
 * The detail order information.
 */
@ToString
public class Order {

  private AccountType accountType;
  private BigDecimal amount;
  private BigDecimal price;
  private long createdTimestamp = 0;
  private long canceledTimestamp = 0;
  private long finishedTimestamp = 0;
  private long orderId = 0;
  private String symbol;
  private BigDecimal stopPrice;
  private OrderType type = null;
  private BigDecimal filledAmount;
  private BigDecimal filledCashAmount;
  private BigDecimal filledFees;
  private OrderSource source = null;
  private OrderState state = null;
  private StopOrderOperator operator = null;


  /**
   * Get the account type which created this order.
   *
   * @return The account type. {@link AccountType}
   */
  public AccountType getAccountType() {
    return accountType;
  }

  /**
   * Get the amount of base currency in this order.
   *
   * @return The amount.
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Get the limit price of limit order.
   *
   * @return The limit price.
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Get the UNIX formatted timestamp in UTC when the order was created.
   *
   * @return The timestamp
   */
  public long getCreatedTimestamp() {
    return createdTimestamp;
  }

  /**
   * Get the UNIX formatted timestamp in UTC when the order was canceled, if not canceled then has
   * value of 0.
   *
   * @return The timestamp
   */
  public long getCanceledTimestamp() {
    return canceledTimestamp;
  }

  /**
   * Get the UNIX formatted timestamp in UTC when the order was changed to a final state. This is
   * not the time the order is matched.
   *
   * @return The timestamp
   */
  public long getFinishedTimestamp() {
    return finishedTimestamp;
  }

  /**
   * Get the order id.
   *
   * @return The order id.
   */
  public long getOrderId() {
    return orderId;
  }

  /**
   * Get the symbol, like "btcusdt".
   *
   * @return The symbol.
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Get the order type, possible values are: buy-market, sell-market, buy-limit, sell-limit,
   * buy-ioc, sell-ioc, buy-limit-maker, sell-limit-maker.
   *
   * @return The order type, see {@link OrderType}
   */
  public OrderType getType() {
    return type;
  }

  /**
   * Get the amount which has been filled.
   *
   * @return The amount.
   */
  public BigDecimal getFilledAmount() {
    return filledAmount;
  }

  /**
   * Get the filled total in quote currency.
   *
   * @return The amount.
   */
  public BigDecimal getFilledCashAmount() {
    return filledCashAmount;
  }

  /**
   * Get the transaction fee paid so far.
   *
   * @return The fee.
   */
  public BigDecimal getFilledFees() {
    return filledFees;
  }

  /**
   * Get the source where the order was triggered, possible values: sys, web, api, app.
   *
   * @return The source, see {@link OrderSource}
   */
  public OrderSource getSource() {
    return source;
  }

  /**
   * Get the order state: submitted, partial-filled, cancelling, filled, canceled.
   *
   * @return The order state, see {@link OrderState}
   */
  public OrderState getState() {
    return state;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setCreatedTimestamp(long createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public void setCanceledTimestamp(long canceledTimestamp) {
    this.canceledTimestamp = canceledTimestamp;
  }

  public void setFinishedTimestamp(long finishedTimestamp) {
    this.finishedTimestamp = finishedTimestamp;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setType(OrderType type) {
    this.type = type;
  }

  public void setFilledAmount(BigDecimal filledAmount) {
    this.filledAmount = filledAmount;
  }

  public void setFilledCashAmount(BigDecimal filledCashAmount) {
    this.filledCashAmount = filledCashAmount;
  }

  public void setFilledFees(BigDecimal filledFees) {
    this.filledFees = filledFees;
  }

  public void setSource(OrderSource source) {
    this.source = source;
  }

  public void setState(OrderState state) {
    this.state = state;
  }

  /**
   * Get stop price
   * @return The stop price
   */
  public BigDecimal getStopPrice() {
    return stopPrice;
  }

  public void setStopPrice(BigDecimal stopPrice) {
    this.stopPrice = stopPrice;
  }

  /**
   * Get the operaror
   * @return gte ,lte
   */
  public StopOrderOperator getOperator() {
    return operator;
  }

  public void setOperator(StopOrderOperator operator) {
    this.operator = operator;
  }

  public static RestApiJsonParser<List<Order>> getListParser(String apiKey){
    return (jsonWrapper -> {
      List<Order> orderList = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Order order = parse(item,apiKey);
        orderList.add(order);
      });
      return orderList;
    });
  }

  public static RestApiJsonParser<Order> getParser(String apiKey) {
    return (jsonWrapper -> {
      JsonWrapper data = jsonWrapper.getJsonObject("data");
      return parse(data,apiKey);
    });
  }

  public static Order parse(JsonWrapper data,String apiKey) {

    BigDecimal filledAmount = data.getBigDecimalOrDefault("field-amount",null);
    if (filledAmount == null) {
      filledAmount = data.getBigDecimalOrDefault("filled-amount",null);
    }

    BigDecimal filledCashAmount = data.getBigDecimalOrDefault("field-cash-amount",null);
    if (filledCashAmount == null) {
      filledCashAmount = data.getBigDecimalOrDefault("filled-cash-amount",null);
    }

    BigDecimal filledFees = data.getBigDecimalOrDefault("field-fees",null);
    if (filledFees == null) {
      filledFees = data.getBigDecimalOrDefault("filled-fees",null);
    }

    Order order = new Order();
    order.setOrderId(data.getLong("id"));
    order.setSymbol(data.getString("symbol"));
    order.setPrice(data.getBigDecimal("price"));
    order.setAmount(data.getBigDecimal("amount"));
    order.setAccountType(AccountsInfoMap.getAccount(apiKey, data.getLong("account-id")).getType());
    order.setCreatedTimestamp(TimeService.convertCSTInMillisecondToUTC(data.getLong("created-at")));
    order.setType(OrderType.lookup(data.getString("type")));

    order.setFilledAmount(filledAmount);
    order.setFilledCashAmount(filledCashAmount);
    order.setFilledFees(filledFees);

    order.setSource(OrderSource.lookup(data.getString("source")));
    order.setState(OrderState.lookup(data.getString("state")));
    order.setStopPrice(data.getBigDecimalOrDefault("stop-price", null));
    order.setOperator(StopOrderOperator.find(data.getStringOrDefault("operator", null)));


    order.setCanceledTimestamp(TimeService.convertCSTInMillisecondToUTC(data.getLongOrDefault("canceled-at",0)));
    order.setFinishedTimestamp(TimeService.convertCSTInMillisecondToUTC(data.getLongOrDefault("finished-at",0)));
    return order;
  }

  public static Order parserForWebSocket(JsonWrapper data, String symbol, String apiKey){
    Order order = new Order();
    order.setOrderId(data.getLong("order-id"));
    order.setSymbol(symbol);
    order.setAccountType(AccountsInfoMap.getAccount(apiKey, data.getLong("account-id")).getType());
    order.setAmount(data.getBigDecimal("order-amount"));
    order.setPrice(data.getBigDecimal("order-price"));
    order.setCreatedTimestamp(TimeService.convertCSTInMillisecondToUTC(data.getLong("created-at")));
    order.setType(OrderType.lookup(data.getString("order-type")));
    order.setFilledAmount(data.getBigDecimal("filled-amount"));
    order.setFilledCashAmount(data.getBigDecimal("filled-cash-amount"));
    order.setFilledFees(data.getBigDecimal("filled-fees"));
    order.setState(OrderState.lookup(data.getString("order-state")));
    order.setSource(OrderSource.lookup(data.getString("order-source")));
    return order;
  }
}
