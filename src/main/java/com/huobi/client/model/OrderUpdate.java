package com.huobi.client.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

}
