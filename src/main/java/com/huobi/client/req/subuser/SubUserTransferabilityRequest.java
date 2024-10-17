package com.huobi.client.req.subuser;

import lombok.*;

import com.huobi.constant.enums.TransferabilityAccountTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserTransferabilityRequest {

  private String subUids;

  private TransferabilityAccountTypeEnum accountType;

  private String transferrable;

}
