package com.huobi.client.req.margin;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.MarginTransferDirectionEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IsolatedMarginTransferRequest {

  private MarginTransferDirectionEnum direction;

  private String symbol;

  private String currency;

  private BigDecimal amount;

}
