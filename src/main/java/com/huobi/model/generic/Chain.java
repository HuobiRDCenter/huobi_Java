package com.huobi.model.generic;

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
public class Chain {

  private String chain;

  private String baseChain;

  private String baseChainProtocol;

  private Boolean isDynamic;

  private int numOfConfirmations;

  private int numOfFastConfirmations;

  private String depositStatus;

  private BigDecimal minDepositAmt;

  private String withdrawStatus;

  private BigDecimal minWithdrawAmt;

  private BigDecimal maxWithdrawAmt;

  private BigDecimal withdrawQuotaPerDay;

  private BigDecimal withdrawQuotaPerYear;

  private BigDecimal withdrawQuotaTotal;

  private int withdrawPrecision;

  private  String withdrawFeeType;

  private BigDecimal transactFeeWithdraw;

  private BigDecimal minTransactFeeWithdraw;

  private BigDecimal maxTransactFeeWithdraw;

  private BigDecimal transactFeeRateWithdraw;

}
