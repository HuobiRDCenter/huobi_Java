package com.huobi.model.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserApiKeyModificationResult {

  private String note;

  private String permission;

  private String ipAddresses;

}
