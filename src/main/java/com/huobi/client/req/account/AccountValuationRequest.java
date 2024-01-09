package com.huobi.client.req.account;

import com.huobi.constant.enums.AccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountValuationRequest {
    AccountTypeEnum accountType;
    String valuationCurrency;
}
