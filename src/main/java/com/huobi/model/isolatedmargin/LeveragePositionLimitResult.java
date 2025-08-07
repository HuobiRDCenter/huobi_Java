package com.huobi.model.isolatedmargin;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeveragePositionLimitResult {
    private String currency;
    private String maxHoldings;
}
