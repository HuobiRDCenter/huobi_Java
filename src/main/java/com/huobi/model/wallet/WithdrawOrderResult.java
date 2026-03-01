package com.huobi.model.wallet;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WithdrawOrderResult {
    private String address;
    @JSONField(name = "client-order-id")
    private String clientOrderId;
    @JSONField(name = "address-tag")
    private String addressTag;
    private BigDecimal amount;
    @JSONField(name = "blockchain-confirm")
    private Integer blockchainConfirm;
    private String chain;
    @JSONField(name = "created-at")
    private Long createdAt;
    private String currency;
    @JSONField(name = "error-code")
    private String errorCode;
    @JSONField(name = "error-msg")
    private String errorMsg;
    private BigDecimal fee;
    @JSONField(name = "from-addr-tag")
    private String fromAddrTag;
    @JSONField(name = "from-address")
    private String fromAddress;
    private Long id;
    @JSONField(name = "request-id")
    private String requestId;
    private String state;
    @JSONField(name = "tx-hash")
    private String txHash;
    private String type;
    @JSONField(name = "updated-at")
    private Long updatedAt;
    @JSONField(name = "user-id")
    private Long userId;
    @JSONField(name = "wallet-confirm")
    private Integer walletConfirm;
}
