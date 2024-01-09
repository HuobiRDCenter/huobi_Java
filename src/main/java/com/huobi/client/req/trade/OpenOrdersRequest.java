package com.huobi.client.req.trade;

import lombok.*;

import com.huobi.constant.enums.OrderSideEnum;
import com.huobi.constant.enums.QueryDirectionEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OpenOrdersRequest {

  private  String symbol;

  private Long accountId;

  private Integer size;

  private OrderSideEnum side;

  private QueryDirectionEnum direct;

  private Long from;

  private String types;

}
