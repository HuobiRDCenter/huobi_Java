package com.huobi.client.req.copyTradingTrade;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TraderFollowerDeleteParam {

    // 跟随者用户id
    // 最大单次可移除10位跟单者
    private List<String> followerUids;

    // 产品类型
    //PERP 永续合约
    //SPOT 现货交易
    private String instType;
}
