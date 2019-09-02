package com.huobi.client.model;

import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;

import lombok.Data;

import java.math.BigDecimal;

/**
 * The detail order information.
 */
@Data
public class Order {

  private AccountType accountType;
  private BigDecimal amount;
  private BigDecimal price;
  private long createdTimestamp = 0;
  private long canceledTimestamp = 0;
  private long finishedTimestamp = 0;
  private long orderId = 0;
  private String symbol;
  private OrderType type = null;
  private BigDecimal filledAmount;
  private BigDecimal filledCashAmount;
  private BigDecimal filledFees;
  private OrderSource source = null;
  private OrderState state = null;

}
