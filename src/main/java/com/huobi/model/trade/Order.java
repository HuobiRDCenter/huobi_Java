package com.huobi.model.trade;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.huobi.constant.enums.OrderSourceEnum;
import com.huobi.constant.enums.OrderStateEnum;
import com.huobi.constant.enums.OrderTypeEnum;
import com.huobi.constant.enums.StopOrderOperatorEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  private Long id;

  private String symbol;

  private Long accountId;

  private BigDecimal amount;

  private BigDecimal price;

  @JSONField(deserialize = false)
  private OrderTypeEnum type;

  private BigDecimal filledAmount;

  private BigDecimal filledCashAmount;

  private BigDecimal filledFees;

  @JSONField(deserialize = false)
  private OrderSourceEnum source;

  @JSONField(deserialize = false)
  private OrderStateEnum state;

  private Long createdAt;

  private Long canceledAt;

  private Long finishedAt;

  private BigDecimal stopPrice;

  @JSONField(deserialize = false)
  private StopOrderOperatorEnum operator;

}
