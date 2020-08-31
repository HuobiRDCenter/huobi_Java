package com.huobi.client.req.etf;

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
public class ETFSwapListRequest {

  private String etfName;

  private Integer offset;

  private Integer limit;

}
