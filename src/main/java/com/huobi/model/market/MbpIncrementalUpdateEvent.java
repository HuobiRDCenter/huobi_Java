package com.huobi.model.market;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MbpIncrementalUpdateEvent {

  private String action;

  private String topic;

  private Long ts;

  private Long prevSeqNum;

  private Long seqNum;

  private List<PriceLevel> bids;

  private List<PriceLevel> asks;

}
