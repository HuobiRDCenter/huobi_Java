package com.huobi.model.trade;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeClearing {

  private String symbol;

  private Long orderId;

  private BigDecimal tradePrice;

  private BigDecimal tradeVolume;

  private String orderSide;

  private String orderType;

  private Boolean aggressor;

  private Long tradeId;

  private Long tradeTime;

  private BigDecimal transactFee;

  private BigDecimal feeDeduct;

  private String feeDeductType;

}
