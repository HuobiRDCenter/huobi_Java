package com.huobi.client.req.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.huobi.constant.enums.AccountTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountAssetValuationRequest {

  AccountTypeEnum accountType;

  String valuationCurrency;

  Long subUid;

}
