package com.huobi.model.account;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountTransferResult {

  private Long transactId;

  private Long transactTime;

}
