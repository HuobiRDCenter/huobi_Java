package com.huobi.client.req.algo;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CancelAlgoOrderRequest {

  private List<String> clientOrderIds;

}
