package com.huobi.model.account;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountTransferV2Result {
    private String success;
    private Long data;
    private Long code;
    private String message;
}
