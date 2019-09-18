package com.huobi.client.model.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.huobi.client.model.enums.TransferFuturesDirection;

@Data
@Builder
@AllArgsConstructor
public class TransferFuturesRequest {

  private String currency;

  private BigDecimal amount;

  private TransferFuturesDirection direction;

}
