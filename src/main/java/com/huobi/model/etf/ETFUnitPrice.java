package com.huobi.model.etf;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.client.impl.utils.JsonWrapper;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ETFUnitPrice {

  private String currency;
  private BigDecimal amount;

}
