package com.huobi.model.etf;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.EtfStatusEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ETFConfig {

  private int purchaseMinAmount;
  private int purchaseMaxAmount;
  private int redemptionMinAmount;
  private int redemptionMaxAmount;
  private BigDecimal purchaseFeeRate;
  private BigDecimal redemptionFeeRate;
  private EtfStatusEnum status;
  private List<ETFUnitPrice> unitPriceList;

}
