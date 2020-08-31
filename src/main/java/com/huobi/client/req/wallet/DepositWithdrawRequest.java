package com.huobi.client.req.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.DepositWithdrawTypeEnum;
import com.huobi.constant.enums.QueryDirectionEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepositWithdrawRequest {

  private DepositWithdrawTypeEnum type;

  private String currency;

  private Long from;

  private Integer size;

  private QueryDirectionEnum direction;

}
