package com.huobi.client.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapperArray;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeeRate {

  private String symbol;
  private BigDecimal makerFee;
  private BigDecimal takerFee;

  public static RestApiJsonParser<List<FeeRate>> getListParser(){
    return (jsonWrapper -> {
      List<FeeRate> rateList = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {

        rateList.add(FeeRate.builder()
            .symbol(item.getString("symbol"))
            .makerFee(item.getBigDecimalOrDefault("maker-fee", null))
            .takerFee(item.getBigDecimalOrDefault("taker-fee", null))
            .build());
      });
      return rateList;
    });
  }
}
