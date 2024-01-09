package com.huobi.client.req.algo;

import lombok.*;

import com.huobi.constant.enums.QuerySortEnum;
import com.huobi.constant.enums.algo.AlgoOrderSideEnum;
import com.huobi.constant.enums.algo.AlgoOrderStatusEnum;
import com.huobi.constant.enums.algo.AlgoOrderTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetHistoryAlgoOrdersRequest {

  private Long accountId;

  private String symbol;

  private AlgoOrderSideEnum orderSide;

  private AlgoOrderTypeEnum orderType;

  private AlgoOrderStatusEnum orderStatus;

  private Long startTime;

  private Long endTime;

  private QuerySortEnum sort;

  private Integer limit;

  private Long fromId;


}
