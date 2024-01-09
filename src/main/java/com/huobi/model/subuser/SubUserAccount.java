package com.huobi.model.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserAccount {

  private Long accountId;

  private String subType;

  private String accountStatus;

}
