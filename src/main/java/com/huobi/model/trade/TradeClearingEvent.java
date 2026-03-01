package com.huobi.model.trade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TradeClearingEvent {
  private String action;

  private String topic;

  private TradeClearing tradeClearing;

}
