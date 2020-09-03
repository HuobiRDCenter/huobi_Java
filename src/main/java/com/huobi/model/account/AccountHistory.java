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
public class AccountHistory {

  private Long accountId;

  private String currency;

  private BigDecimal transactAmt;

  private String transactType;

  private BigDecimal availBalance;

  private BigDecimal acctBalance;

  private Long transactTime;

  private Long recordId;

  private Long nextId;

}
