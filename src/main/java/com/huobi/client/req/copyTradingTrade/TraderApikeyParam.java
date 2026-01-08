package com.huobi.client.req.copyTradingTrade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TraderApikeyParam {

    /**
     * 产品类型
     *
     * PERP 永续合约
     *
     * SPOT 现货交易
     */
    private String instType;
}
