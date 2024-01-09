package com.huobi.client.req.account;

import com.huobi.constant.enums.BusinessLineAccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransferV2Request {
    private BusinessLineAccountTypeEnum from;
    private BusinessLineAccountTypeEnum to;
    private String currency;
    private BigDecimal amount;
    private String marginAccount;

}
