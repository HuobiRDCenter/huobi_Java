package com.huobi.client.req.account;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetBalanceRequest {
    private Long subUid;
}
