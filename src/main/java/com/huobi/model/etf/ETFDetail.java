package com.huobi.model.etf;

import java.math.BigDecimal;
import java.util.List;

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
public class ETFDetail {

  private BigDecimal rate;

  private BigDecimal fee;

  private BigDecimal pointCardAmount;

  private List<ETFUnitPrice> obtainCurrencyList;

  private List<ETFUnitPrice> usedCurrencyList;


}
