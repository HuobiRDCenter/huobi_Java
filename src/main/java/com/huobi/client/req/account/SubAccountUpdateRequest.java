package com.huobi.client.req.account;

import com.huobi.constant.enums.AccountActionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.AccountUpdateModeEnum;
import com.huobi.constant.enums.BalanceModeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubAccountUpdateRequest {

    private AccountActionEnum accountAction;

    private AccountUpdateModeEnum accountUpdateMode;

}
