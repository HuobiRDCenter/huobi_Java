package com.huobi.client.req.copyTradingTrade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TraderUnrealizedProfitSharingSummaryParam {

    private String instType;
    private String ccy;
}
