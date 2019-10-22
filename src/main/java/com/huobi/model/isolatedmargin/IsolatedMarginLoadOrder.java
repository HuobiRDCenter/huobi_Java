package com.huobi.model.isolatedmargin;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.LoanOrderStateEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IsolatedMarginLoadOrder {

  private Long id;

  private Long userId;

  private Long accountId;

  private String symbol;

  private String currency;

  private BigDecimal paidCoin;

  private BigDecimal paidPoint;

  private BigDecimal deductAmount;

  private BigDecimal deductRate;

  private String deductCurrency;

  private BigDecimal loanAmount;

  private BigDecimal loanBalance;

  private BigDecimal interestRate;

  private BigDecimal interestAmount;

  private BigDecimal interestBalance;

  @JSONField(deserialize = false)
  private LoanOrderStateEnum state;

  private Long createdAt;

  private Long updatedAt;

  private Long accruedAt;

}
