package com.huobi.client.req.account;


import java.math.BigDecimal;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PointTransferRequest {

  private Long fromUid;

  private Long toUid;

  private Long groupId;

  private BigDecimal amount;

}
