package com.huobi.model.trade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.OrderStateEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CancelFailedResult {

  private Long orderId;

  private String errMsg;

  private String errCode;

  private Integer orderState;

}
