package com.huobi.model.etf;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.EtfSwapTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ETFSwapRecord {

  private Long id;

  private String currency;

  private BigDecimal amount;

  private EtfSwapTypeEnum type;

  private Integer status;

  private Long gmtCreated;

  private ETFDetail detail;


}
