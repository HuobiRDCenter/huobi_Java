package com.huobi.model.market;

import java.util.List;

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
public class MarketDepth {

  private long version;

  private Long ts;

  private List<PriceLevel> bids;

  private List<PriceLevel> asks;

}
