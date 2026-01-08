package com.huobi.client.req.copyTradingTrade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TraderTransferParam {

    /**
     * 划转的资金数量
     */
    private String amt;

    /**
     * 转出账户类型
     * 1:现货账户
     *
     * 2:U本位合约账户
     *
     * 3:交易账户(统一账户预留类型)
     *
     * 4:合约跟单账户
     *
     * 5:现货跟单账户(预留类型)
     *
     * 同类型账户不可相互划转，否则报错。
     */
    private String from;

    /**
     * 转入账户类型
     *
     * 1:现货账户
     *
     * 2:U本位合约账户
     *
     * 3:交易账户(统一账户预留类型)
     *
     * 4:合约跟单账户
     *
     * 5:现货跟单账户(预留类型)
     *
     * 同类型账户不可相互划转，否则报错。
     */
    private String to;

    /**
     * 币种，当前仅支持USDT。
     *
     * 若不传，默认为USDT
     */
    private String ccy;
}
