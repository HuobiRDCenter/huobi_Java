package com.huobi.model.market;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MbpIncrementalUpdateEvent {

  private String action;

  private String topic;

  private Long ts;

  private Long prevSeqNum;

  private Long seqNum;

  private List<PriceLevel> bids;

  private List<PriceLevel> asks;

}
