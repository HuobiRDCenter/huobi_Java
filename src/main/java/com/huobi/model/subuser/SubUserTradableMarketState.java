package com.huobi.model.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserTradableMarketState {

  private Long subUid;

  private String accountType;

  private String activation;

  private String errCode;

  private String errMessage;

}
