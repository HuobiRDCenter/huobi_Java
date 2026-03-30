package com.huobi.client.req.trade;

import com.huobi.constant.enums.AccountActionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubTradeClearingRequest {

    private AccountActionEnum accountAction;

    private String symbols;

    private int[] modes;

}
