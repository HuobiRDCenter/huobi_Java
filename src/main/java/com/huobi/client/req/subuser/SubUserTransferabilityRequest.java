package com.huobi.client.req.subuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.huobi.constant.enums.TransferabilityAccountTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserTransferabilityRequest {

  private String subUids;

  private TransferabilityAccountTypeEnum accountType;

  private String transferrable;

}
