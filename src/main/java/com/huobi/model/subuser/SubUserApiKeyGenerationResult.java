package com.huobi.model.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserApiKeyGenerationResult {

  private String note;

  private String accessKey;

  private String secretKey;

  private String permission;

  private String ipAddresses;

}
