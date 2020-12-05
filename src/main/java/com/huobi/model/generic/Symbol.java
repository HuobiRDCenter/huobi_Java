package com.huobi.model.generic;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
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

  @JSONField(name = "base-currency")
  private String baseCurrency;

  @JSONField(name = "quote-currency")
  private String quoteCurrency;

  @JSONField(name = "price-precision")
  private int pricePrecision;

  @JSONField(name = "amount-precision")
  private int amountPrecision;

  @JSONField(name = "symbol-partition")
  private String symbolPartition;

  @JSONField(name = "value-precision")
  private Integer valuePrecision;

  @JSONField(name = "min-order-amt")
  private BigDecimal minOrderAmt;

  @JSONField(name = "max-order-amt")
  private BigDecimal maxOrderAmt;

  @JSONField(name = "min-order-value")
  private BigDecimal minOrderValue;

  @JSONField(name = "leverage-ratio")
  private Integer leverageRatio;

  private String state;

  @JSONField(name = "limit-order-min-order-amt")
  private BigDecimal limitOrderMinOrderAmt;

  @JSONField(name = "limit-order-max-order-amt")
  private BigDecimal limitOrderMaxOrderAmt;

  @JSONField(name = "sell-market-min-order-amt")
  private BigDecimal sellMarketMinOrderAmt;

  @JSONField(name = "sell-market-max-order-amt")
  private BigDecimal sellMarketMaxOrderAmt;

  @JSONField(name = "buy-market-max-order-value")
  private BigDecimal buyMarketMaxOrderValue;

  @JSONField(name = "max-order-value")
  private BigDecimal maxOrderValue;

  private String underlying;

  @JSONField(name = "mgmt-fee-rate")
  private BigDecimal mgmtFeeRate;

  @JSONField(name = "charge-time")
  private String chargeTime;

  @JSONField(name = "rebal-time")
  private String rebalTime;

  @JSONField(name = "rebal-threshold")
  private BigDecimal rebalThreshold;

  @JSONField(name = "init-nav")
  private BigDecimal initNav;

}
