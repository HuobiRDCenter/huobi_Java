package com.huobi.model.trade;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.DealRoleEnum;
import com.huobi.constant.enums.OrderSourceEnum;
import com.huobi.constant.enums.OrderTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchResult {

  private Long id;

  private Long orderId;

  private Long matchId;

  private String symbol;

  @JSONField(deserialize = false)
  private OrderTypeEnum type;

  @JSONField(deserialize = false)
  private OrderSourceEnum source;

  private BigDecimal price;

  private BigDecimal filledAmount;

  private BigDecimal filledFees;

  @JSONField(deserialize = false)
  private DealRoleEnum role;

  private BigDecimal filledPoints;

  private String feeDeductCurrency;

  private long createdAt;

}
