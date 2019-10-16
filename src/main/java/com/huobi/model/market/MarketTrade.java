package com.huobi.model.market;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.TradeDirectionEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MarketTrade {

  private String id;

  private Long tradeId;

  private BigDecimal price;

  private BigDecimal amount;

  private TradeDirectionEnum direction;

  private Long ts;

}
