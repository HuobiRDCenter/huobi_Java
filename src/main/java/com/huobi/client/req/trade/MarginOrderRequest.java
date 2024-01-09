package com.huobi.client.req.trade;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarginOrderRequest {
    private String symbol;
    @JSONField(name = "account-id")
    private String accountId;
    private String amount;
    @JSONField(name = "market-amount")
    private String marketAmount;
    @JSONField(name = "borrow-amount")
    private String borrowAmount;
    private String type;
    @JSONField(name = "trade-purpose")
    private String tradePurpose;
    private String price;
    @JSONField(name = "stop-price")
    private String stopPrice;
    private String operator;
    private String source;


}
