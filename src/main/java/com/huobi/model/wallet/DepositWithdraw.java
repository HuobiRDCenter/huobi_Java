package com.huobi.model.wallet;

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
public class DepositWithdraw {

  private Long id;

  private String type;

  private String currency;

  private String txHash;

  private String chain;

  private BigDecimal amount;

  private String address;

  private String addressTag;

  private BigDecimal fee;

  private String state;

  private String errorCode;

  private String errorMessage;

  private Long createdAt;

  private Long updatedAt;

}
