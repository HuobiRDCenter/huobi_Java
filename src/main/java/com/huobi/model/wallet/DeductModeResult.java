package com.huobi.model.wallet;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeductModeResult {
    private String subUid;
    private String deductMode;
    private String errCode;
    private String errMessage;
}
