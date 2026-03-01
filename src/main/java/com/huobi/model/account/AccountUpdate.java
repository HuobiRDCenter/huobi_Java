package com.huobi.model.account;

import java.math.BigDecimal;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountUpdate {

  private String currency;

  private Long accountId;

  private BigDecimal balance;

  private BigDecimal available;

  private String changeType;

  private String accountType;

  private Long changeTime;

}
