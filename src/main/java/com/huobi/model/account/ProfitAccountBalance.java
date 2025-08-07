package com.huobi.model.account;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfitAccountBalance {
    private String distributionType;
    private Float balance;
    private Boolean success;
    private String accountBalance;
}
