package com.huobi.client.req.account;

import com.huobi.constant.enums.BusinessLineAccountTypeEnum;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountTransferV2Request {
    private BusinessLineAccountTypeEnum from;
    private BusinessLineAccountTypeEnum to;
    private String currency;
    private BigDecimal amount;
    private String marginAccount;

}
