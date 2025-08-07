package com.huobi.client.req.trade;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BatchOrdersRequest {
    @JSONField(name = "account-id")
    private String accountId;
    private String symbol;
    private String type;
    private String amount;
    private String price;
    private String source;
    @JSONField(name = "client-order-id")
    private String clientOrderId;
    @JSONField(name = "self-match-prevent")
    private Integer selfMatchPrevent;
    @JSONField(name = "stop-price")
    private String stopPrice;
    private String operator;
}
