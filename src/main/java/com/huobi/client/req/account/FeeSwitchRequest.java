package com.huobi.client.req.account;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeeSwitchRequest {
    private Integer switchType;
    private String deductionCurrency;
}
