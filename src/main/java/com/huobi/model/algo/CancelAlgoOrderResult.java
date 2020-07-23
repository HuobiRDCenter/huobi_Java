package com.huobi.model.algo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelAlgoOrderResult {

  private List<String> accepted;

  private List<String> rejected;

}
