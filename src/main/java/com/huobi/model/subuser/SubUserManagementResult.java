package com.huobi.model.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserManagementResult {

  private Long subUid;

  private String userState;

}
