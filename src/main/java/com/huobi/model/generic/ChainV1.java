package com.huobi.model.generic;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChainV1 {
    private Boolean adt;
    private String ac;
    private Boolean ao;
    private Boolean awt;
    private String chain;
    private String ct;
    private String code;
    private String currency;
    @JSONField(name = "deposit-desc")
    private String depositDesc;
    private Boolean de;
    private String dma;
    @JSONField(name = "deposit-tips-desc")
    private String depositTipsDesc;
    private String dn;
    private Integer fc;
    private String ft;
    @JSONField(name = "default")
    private Integer isDefault;
    @JSONField(name = "replace-chain-info-desc")
    private String replaceChainInfoDesc;
    @JSONField(name = "replace-chain-notification-desc")
    private String replaceChainNotificationDesc;
    @JSONField(name = "replace-chain-popup-desc")
    private String replaceChainPopupDesc;
    private String ca;
    private Integer cct;
    private Integer sc;
    private String sda;
    @JSONField(name = "suspend-deposit-desc")
    private String suspendDepositDesc;
    private String swa;
    @JSONField(name = "suspend-withdraw-desc")
    private String suspendWithdrawDesc;
    private Boolean v;
    @JSONField(name = "withdraw-desc")
    private String withdrawDesc;
    private Boolean we;
    private String wma;
    private Integer wp;
    private String fn;
    @JSONField(name = "withdraw-tips-desc")
    private String withdrawTipsDesc;
    @JSONField(name = "suspend-visible-desc")
    private String suspendVisibleDesc;

}
