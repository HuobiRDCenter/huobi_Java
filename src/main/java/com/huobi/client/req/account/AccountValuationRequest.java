package com.huobi.client.req.account;

import com.huobi.constant.enums.AccountTypeEnum;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountValuationRequest {
    AccountTypeEnum accountType;
    String valuationCurrency;
}
