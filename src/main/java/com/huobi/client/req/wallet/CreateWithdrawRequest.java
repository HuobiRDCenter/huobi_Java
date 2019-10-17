package com.huobi.client.req.wallet;

import java.math.BigDecimal;

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
public class CreateWithdrawRequest {

  private String address;

  private BigDecimal amount;

  private BigDecimal fee;

  private String currency;

  private String chain;

  private String addrTag;

}
