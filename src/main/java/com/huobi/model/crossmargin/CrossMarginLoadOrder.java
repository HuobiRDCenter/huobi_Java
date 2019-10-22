package com.huobi.model.crossmargin;

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

  @JSONField(deserialize = false)
  private LoanOrderStateEnum state;

  private Long createdAt;

  private Long accruedAt;

}
