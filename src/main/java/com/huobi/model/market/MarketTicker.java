package com.huobi.model.market;

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
public class MarketTicker {

  private String symbol;

  private BigDecimal open;

  private BigDecimal close;

  private BigDecimal low;

  private BigDecimal high;

  private BigDecimal amount;

  private Long count;

  private BigDecimal vol;

  private BigDecimal bid;

  private BigDecimal bidSize;

  private BigDecimal ask;

  private BigDecimal askSize;

}
