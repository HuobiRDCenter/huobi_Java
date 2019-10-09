package com.huobi.client.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.enums.DealRole;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderUpdate {

  private Long matchId;

  private Long orderId;

  private String symbol;

  private OrderState state;

  private OrderType type;

  private DealRole role;

  private BigDecimal price;

  private BigDecimal filledAmount;

  private BigDecimal filledCashAmount;

  private BigDecimal unfilledAmount;

  private String clientOrderId;

  public static OrderUpdate parse(JsonWrapper data,String symbol) {
    return OrderUpdate.builder()
        .matchId(data.getLong("match-id"))
        .orderId(data.getLong("order-id"))
        .symbol(symbol)
        .state(OrderState.lookup(data.getString("order-state")))
        .type(OrderType.lookup(data.getString("order-type")))
        .role(DealRole.find(data.getString("role")))
        .price(data.getBigDecimal("price"))
        .filledAmount(data.getBigDecimal("filled-amount"))
        .filledCashAmount(data.getBigDecimal("filled-cash-amount"))
        .unfilledAmount(data.getBigDecimal("unfilled-amount"))
        .clientOrderId(data.getStringOrDefault("client-order-id", null))
        .build();
  }

}
