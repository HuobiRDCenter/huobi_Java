package com.huobi.model.market;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
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
public class MarketDetailMerged {

  private Long id;

  private BigDecimal amount;

  private Long count;

  private BigDecimal open;

  private BigDecimal close;

  private BigDecimal low;

  private BigDecimal high;

  private BigDecimal vol;

  @JSONField(deserialize = false)
  private PriceLevel bid;

  @JSONField(deserialize = false)
  private PriceLevel ask;

}
