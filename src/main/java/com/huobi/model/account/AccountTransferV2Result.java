package com.huobi.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransferV2Result {
    private String success;
    private Long data;
    private Long code;
    private String message;
}
