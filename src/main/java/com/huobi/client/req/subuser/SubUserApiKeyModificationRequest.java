package com.huobi.client.req.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserApiKeyModificationRequest {

  private Long subUid;

  private String accessKey;

  private String note;

  private String permission;

  private String ipAddresses;

}
