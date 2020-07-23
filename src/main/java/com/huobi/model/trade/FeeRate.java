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
public class FeeRate {

  private String symbol;

  private BigDecimal makerFeeRate;

  private BigDecimal takerFeeRate;

  private BigDecimal actualMakerRate;

  private BigDecimal actualTakerRate;

}
