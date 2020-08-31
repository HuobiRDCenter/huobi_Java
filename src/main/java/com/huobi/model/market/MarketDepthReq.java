package com.huobi.model.market;

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
public class MarketDepthReq {

  private String ch;

  private Long ts;

  private MarketDepth depth;

}
