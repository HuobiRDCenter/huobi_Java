package com.huobi.model.wallet;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WithdrawAddress {

  private String currency;

  private String chain;

  private String note;

  private String addressTag;

  private String address;

}
