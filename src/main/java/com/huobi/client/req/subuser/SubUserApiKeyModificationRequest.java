package com.huobi.client.req.subuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserApiKeyModificationRequest {

  private Long subUid;

  private String accessKey;

  private String note;

  private String permission;

  private String ipAddresses;

}
