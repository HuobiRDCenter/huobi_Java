package com.huobi.model.trade;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Deprecated
public class OrderUpdate {


  private Long orderId;

  private String clientOrderId;

  private Long matchId;

  private String symbol;

  private BigDecimal price;

  private BigDecimal unfilledAmount;

  private BigDecimal filledAmount;

  private BigDecimal filledCashAmount;

  private String orderType;

  private String orderState;

  private String role;

}
