package com.huobi.model.trade;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BatchCancelOpenOrdersResult {

  private Integer successCount;

  private Integer failedCount;

  private Long nextId;

}
