package com.huobi.client.req.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawAddressRequest {

  private String currency;

  private String chain;

  private String note;

  private Integer limit;

  private Long fromId;

}
