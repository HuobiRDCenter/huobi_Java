package com.huobi.model.account;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The balance of specified account.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Balance {

  private String currency;

  private String type;

  private BigDecimal balance;

}
