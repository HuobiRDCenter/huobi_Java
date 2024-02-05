package com.huobi.model.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserTransferabilityState {

  private Long subUid;

  private String accountType;

  private Boolean transferrable;

  private String errCode;

  private String errMessage;

}
