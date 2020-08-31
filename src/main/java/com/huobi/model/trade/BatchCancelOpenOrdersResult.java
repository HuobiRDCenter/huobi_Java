package com.huobi.model.trade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchCancelOpenOrdersResult {

  private Integer successCount;

  private Integer failedCount;

  private Long nextId;

}
