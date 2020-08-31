package com.huobi.client.req.margin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IsolatedMarginLoanInfoRequest {

  private String symbols;

}
