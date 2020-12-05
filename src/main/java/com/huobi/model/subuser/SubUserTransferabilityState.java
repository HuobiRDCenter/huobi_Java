package com.huobi.model.subuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserTransferabilityState {

  private Long subUid;

  private String accountType;

  private Boolean transferrable;

  private String errCode;

  private String errMessage;

}
