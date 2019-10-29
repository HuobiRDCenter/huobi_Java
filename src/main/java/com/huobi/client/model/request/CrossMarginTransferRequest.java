package com.huobi.client.model.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.client.model.enums.CrossMarginTransferType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrossMarginTransferRequest {

  /**
   * transfer type form -> to
   */
  private CrossMarginTransferType type;

  /**
   * Currency
   */
  private String currency;

  /**
   * Transfer Amount
   */
  private BigDecimal amount;

}
