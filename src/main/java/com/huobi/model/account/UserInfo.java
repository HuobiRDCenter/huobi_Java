package com.huobi.model.account;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfo {
    private Integer pointSwitch;
    private Integer currencySwitch;
    private String deductionCurrency;
}
