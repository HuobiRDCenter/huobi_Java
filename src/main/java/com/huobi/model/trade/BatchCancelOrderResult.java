package com.huobi.model.trade;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchCancelOrderResult {

  private List<Long> successList;

  private List<CancelFailedResult> failedList;

}
