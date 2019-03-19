package com.huobi.client.model;

import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderType;
import java.math.BigDecimal;

/**
 * The match result information.
 */
public class MatchResult {

  private long createdTimestamp;
  private BigDecimal filledAmount;
  private BigDecimal filledFees;
  private long id;
  private long matchId;
  private long orderId;
  private BigDecimal price;
  private OrderSource source;
  private String symbol;
  private OrderType type;

  /**
   * Get the UNIX formatted timestamp in UTC when the match and fill is done.
   *
   * @return The timestamp.
   */
  public long getCreatedTimestamp() {
    return createdTimestamp;
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
   * Get the transaction fee paid so far.
   *
   * @return The fee.
   */
  public BigDecimal getFilledFees() {
    return filledFees;
  }

  /**
   * Get the internal id.
   *
   * @return The id.
   */
  public long getId() {
    return id;
  }

  /**
   * Get the match id of this match.
   *
   * @return The id.
   */
  public long getMatchId() {
    return matchId;
  }

  /**
   * Get the order id of this order.
   *
   * @return The id.
   */
  public long getOrderId() {
    return orderId;
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
   * Get the source where the order was triggered, possible values: sys, web, api, app.
   *
   * @return The source, see {@link OrderSource}
   */
  public OrderSource getSource() {
    return source;
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

  public void setCreatedTimestamp(long createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public void setFilledAmount(BigDecimal filledAmount) {
    this.filledAmount = filledAmount;
  }

  public void setFilledFees(BigDecimal filledFees) {
    this.filledFees = filledFees;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setMatchId(long matchId) {
    this.matchId = matchId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setSource(OrderSource source) {
    this.source = source;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setType(OrderType type) {
    this.type = type;
  }
}
