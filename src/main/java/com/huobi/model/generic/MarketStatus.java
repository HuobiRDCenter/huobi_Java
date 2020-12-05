package com.huobi.model.generic;

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
public class MarketStatus {

  private Integer marketStatus;

  private Long haltStartTime;

  private Long haltEndTime;

  private Integer haltReason;

  private String affectedSymbols;

}
