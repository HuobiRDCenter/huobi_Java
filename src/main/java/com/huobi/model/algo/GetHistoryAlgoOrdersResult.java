package com.huobi.model.algo;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetHistoryAlgoOrdersResult {

  private List<AlgoOrder> list;

  private Long nextId;

}
