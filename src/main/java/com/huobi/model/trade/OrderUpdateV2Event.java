package com.huobi.model.trade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderUpdateV2Event {

  private String action;

  private String topic;

  private OrderUpdateV2 orderUpdate;


}
