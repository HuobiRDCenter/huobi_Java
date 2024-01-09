package com.huobi.client.req.algo;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CancelAlgoAllOrderRequest {
    private Integer timeout;

}
