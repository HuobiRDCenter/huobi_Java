package com.huobi.model.subuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserTradableMarketState {

  private Long subUid;

  private String accountType;

  private String activation;

  private String errCode;

  private String errMessage;

}
