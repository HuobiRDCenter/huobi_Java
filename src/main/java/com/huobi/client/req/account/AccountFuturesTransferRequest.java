package com.huobi.client.req.account;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.huobi.constant.enums.AccountFuturesTransferTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountFuturesTransferRequest {

  private String currency;

  private BigDecimal amount;

  private AccountFuturesTransferTypeEnum type;
}
