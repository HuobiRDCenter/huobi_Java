package com.huobi.client.req.subuser;

import lombok.*;

import com.huobi.constant.enums.SubUserManagementActionEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserManagementRequest {

  private Long subUid;

  private SubUserManagementActionEnum action;

}
