package com.huobi.model.trade;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.DealRoleEnum;
import com.huobi.constant.enums.OrderStateEnum;
import com.huobi.constant.enums.OrderTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderUpdate {


  private Long orderId;

  private String clientOrderId;

  private Long matchId;

  private String symbol;

  private BigDecimal price;

  private BigDecimal unfilledAmount;

  private BigDecimal filledAmount;

  private BigDecimal filledCashAmount;

  @JSONField(deserialize = false)
  private OrderTypeEnum orderType;

  @JSONField(deserialize = false)
  private OrderStateEnum orderState;

  @JSONField(deserialize = false)
  private DealRoleEnum role;

}
