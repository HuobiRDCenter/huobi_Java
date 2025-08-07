package com.huobi.model.account;

import java.math.BigDecimal;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
