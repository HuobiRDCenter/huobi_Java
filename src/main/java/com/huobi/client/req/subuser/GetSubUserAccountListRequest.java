package com.huobi.client.req.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetSubUserAccountListRequest {

  private Long subUid;
}
