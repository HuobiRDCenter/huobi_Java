package com.huobi.client.req.account;

import java.math.BigDecimal;

import lombok.*;

import com.huobi.constant.enums.AccountFuturesTransferTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountFuturesTransferRequest {

  private String currency;

  private BigDecimal amount;

  private AccountFuturesTransferTypeEnum type;
}
