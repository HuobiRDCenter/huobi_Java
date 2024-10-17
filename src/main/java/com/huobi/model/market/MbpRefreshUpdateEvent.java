package com.huobi.model.market;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MbpRefreshUpdateEvent {

  private String topic;

  private Long ts;

  private Long seqNum;

  private List<PriceLevel> bids;

  private List<PriceLevel> asks;

}
