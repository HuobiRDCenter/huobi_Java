package com.huobi.client.model.request;

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
public class CrossMarginApplyLoanRequest {

  /**
   * Currency
   */
  private String currency;

  /**
   * The amount of currency to borrow
   */
  private BigDecimal amount;

}
