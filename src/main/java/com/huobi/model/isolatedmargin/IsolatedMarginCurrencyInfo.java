package com.huobi.model.isolatedmargin;

import java.math.BigDecimal;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IsolatedMarginCurrencyInfo {

  private String currency;

  private BigDecimal interestRate;

  private BigDecimal minLoanAmt;

  private BigDecimal maxLoanAmt;

  private BigDecimal loanableAmt;

  private BigDecimal actualRate;

}
