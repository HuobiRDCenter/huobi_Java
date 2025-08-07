package com.huobi.model.crossmargin;

import java.math.BigDecimal;

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
public class CrossMarginLoadOrder {

  private Long id;

  private Long userId;

  private Long accountId;

  private String currency;

  private BigDecimal loanAmount;

  private BigDecimal loanBalance;

  private BigDecimal interestAmount;

  private BigDecimal interestBalance;

  private BigDecimal filledPoints;

  private BigDecimal filledHt;

  private String state;

  private Long createdAt;

  private Long accruedAt;

}
