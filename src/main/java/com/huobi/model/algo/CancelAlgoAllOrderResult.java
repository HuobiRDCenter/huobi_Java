package com.huobi.model.algo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelAlgoAllOrderResult {
    private Long currentTime;
    private Long triggerTime;
}
