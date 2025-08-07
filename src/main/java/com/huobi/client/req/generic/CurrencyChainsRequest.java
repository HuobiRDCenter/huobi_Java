package com.huobi.client.req.generic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrencyChainsRequest {

  private String currency;

  @Builder.Default
  private boolean authorizedUser = true;

}
