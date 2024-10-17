package com.huobi.model.algo;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CancelAlgoOrderResult {

  private List<String> accepted;

  private List<String> rejected;

}
