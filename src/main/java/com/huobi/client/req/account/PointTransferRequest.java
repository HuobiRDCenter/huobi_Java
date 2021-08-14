package com.huobi.client.req.account;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointTransferRequest {

  private Long fromUid;

  private Long toUid;

  private Long groupId;

  private BigDecimal amount;

}
