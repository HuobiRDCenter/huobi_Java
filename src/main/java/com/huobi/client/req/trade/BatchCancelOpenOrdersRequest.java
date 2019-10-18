package com.huobi.client.req.trade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.AccountTypeEnum;
import com.huobi.constant.enums.OrderSideEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BatchCancelOpenOrdersRequest {

  private AccountTypeEnum accountType;

  private String symbol;

  private OrderSideEnum side;

  private Integer size;

}
