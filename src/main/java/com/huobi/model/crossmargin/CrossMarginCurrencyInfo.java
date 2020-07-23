package com.huobi.model.crossmargin;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrossMarginCurrencyInfo {

  private String currency;

  private BigDecimal interestRate;

  private BigDecimal minLoanAmt;

  private BigDecimal maxLoanAmt;

  private BigDecimal loanableAmt;

  private BigDecimal actualRate;

}
