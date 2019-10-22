package com.huobi.model.generic;

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
public class Symbol {

  private String symbol;

  private String baseCurrency;

  private String quoteCurrency;

  private int pricePrecision;

  private int amountPrecision;

  private String symbolPartition;

  private Integer valuePrecision;

  private BigDecimal minOrderAmt;

  private BigDecimal maxOrderAmt;

  private BigDecimal minOrderValue;

  private Integer leverageRatio;

}
