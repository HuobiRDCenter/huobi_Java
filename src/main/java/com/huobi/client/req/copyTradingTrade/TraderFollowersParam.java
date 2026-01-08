package com.huobi.client.req.copyTradingTrade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TraderFollowersParam {
    private String instType;
    private String begin;
    private String end;
    private String after;
    private String before;
    private String limit;
}
