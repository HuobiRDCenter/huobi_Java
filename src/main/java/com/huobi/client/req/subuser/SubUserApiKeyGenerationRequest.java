package com.huobi.client.req.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserApiKeyGenerationRequest {

  private String otpToken;

  private Long subUid;

  private String note;

  private String permission;

  private String ipAddresses;

}
