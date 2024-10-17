package com.huobi.model.isolatedmargin;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IsolatedMarginSymbolInfo {

  private String symbol;

  private List<IsolatedMarginCurrencyInfo> currencies;

}
