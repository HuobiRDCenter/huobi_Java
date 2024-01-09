package com.huobi.model.isolatedmargin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeveragePositionLimitResult {
    private String currency;
    private String maxHoldings;
}
