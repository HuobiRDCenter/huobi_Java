package com.huobi.model.crossmargin;

import java.math.BigDecimal;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrossMarginCurrencyInfo {

  private String currency;

  private BigDecimal interestRate;

  private BigDecimal minLoanAmt;

  private BigDecimal maxLoanAmt;

  private BigDecimal loanableAmt;

  private BigDecimal actualRate;

}
