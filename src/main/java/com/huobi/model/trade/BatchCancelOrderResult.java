package com.huobi.model.trade;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BatchCancelOrderResult {

  private List<Long> successList;

  private List<CancelFailedResult> failedList;

}
