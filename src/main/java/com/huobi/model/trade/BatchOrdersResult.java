package com.huobi.model.trade;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BatchOrdersResult {
    @JSONField(name = "order-id")
    private Long orderId;
    @JSONField(name = "client-order-id")
    private String clientOrderId;
    @JSONField(name = "err-code")
    private String errCode;
    @JSONField(name = "err-msg")
    private String errMsg;

}
