package com.huobi.client.req.trade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CancelOrderRequest {

    private Long orderId;

    private String symbol;

}
