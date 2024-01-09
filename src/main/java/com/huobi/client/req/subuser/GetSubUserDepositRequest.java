package com.huobi.client.req.subuser;

import lombok.*;

import com.huobi.constant.enums.QuerySortEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetSubUserDepositRequest {

  private Long subUid;

  private String currency;

  private Long startTime;

  private Long endTime;

  private QuerySortEnum sort;

  private Integer limit;

  private Long fromId;

}
