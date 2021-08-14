package com.huobi.model.subuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserApiKeyGenerationResult {

  private String note;

  private String accessKey;

  private String secretKey;

  private String permission;

  private String ipAddresses;

}
