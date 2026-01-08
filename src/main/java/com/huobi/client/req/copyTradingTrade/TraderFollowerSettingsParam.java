package com.huobi.client.req.copyTradingTrade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TraderFollowerSettingsParam {

    /**
     * 产品类型
     */
    private String instType;

    /**
     * 开启或关闭跟单
     */
    private Boolean enable;

    /**
     *
     * 分润比例，开启跟单时可配。
     */
    private String profitSharingRatio;

    /**
     * 跟单人数上限，开启跟单时可配。
     */
    private String maxFollowers;
}
