package com.huobi.model.algo;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CancelAlgoAllOrderResult {
    private Long currentTime;
    private Long triggerTime;
}
