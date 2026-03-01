package com.huobi.client.req.wallet;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WithdrawAddressRequest {

  private String currency;

  private String chain;

  private String note;

  private Integer limit;

  private Long fromId;

}
