package com.huobi.client.req.subuser;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreditRequest {
    private Long transactionId;
    private String currency;
    private BigDecimal amount;
    private Long accountId;
    private Long userId;
}
