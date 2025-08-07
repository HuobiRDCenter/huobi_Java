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
public class MatchResult {

  private Long id;

  private Long orderId;

  private Long matchId;

  private String symbol;

  private String type;

  private String source;

  private BigDecimal price;

  private BigDecimal filledAmount;

  private BigDecimal filledFees;

  private String role;

  private BigDecimal filledPoints;

  private String feeDeductCurrency;

  private long createdAt;

  private String feeCurrency;

  private long tradeId;

  private String feeDeductState;


}
