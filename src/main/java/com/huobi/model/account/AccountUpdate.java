package com.huobi.model.account;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdate {

  private String currency;

  private Long accountId;

  private BigDecimal balance;

  private BigDecimal available;

  private String changeType;

  private String accountType;

  private Long changeTime;

}
