package com.huobi.client.model;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.TimeService;

/**
 * The market bbo.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MarketBBO {

  private String symbol;

  private Long quoteTime;

  private BigDecimal bid;

  private BigDecimal bidSize;

  private BigDecimal ask;

  private BigDecimal askSize;


  public static MarketBBO parse(JsonWrapper jsonWrapper) {
    return MarketBBO.builder()
        .symbol(jsonWrapper.getStringOrDefault("symbol",null))
        .quoteTime(TimeService.convertCSTInMillisecondToUTC(jsonWrapper.getLong("quoteTime")))
        .bid(jsonWrapper.getBigDecimalOrDefault("bid",null))
        .bidSize(jsonWrapper.getBigDecimalOrDefault("bidSize",null))
        .ask(jsonWrapper.getBigDecimalOrDefault("ask",null))
        .askSize(jsonWrapper.getBigDecimalOrDefault("askSize",null))
        .build();
  }



}
