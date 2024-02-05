package com.huobi.model.market;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MarketTickerEvent {
    private String ch;

    private Long ts;

    private MarketTicker ticker;

}
