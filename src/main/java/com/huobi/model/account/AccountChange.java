package com.huobi.model.account;

import java.math.BigDecimal;

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
public class AccountChange {

  private Long accountId;

  private String currency;

  private String type;

  private BigDecimal balance;

}
