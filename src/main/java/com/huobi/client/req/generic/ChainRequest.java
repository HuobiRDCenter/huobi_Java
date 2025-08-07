package com.huobi.client.req.generic;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChainRequest {
    private String showDesc;
    private String currency;
    private Long ts;
}
