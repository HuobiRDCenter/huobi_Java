package com.huobi.model.subuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiKeyInfo {

  private String accessKey;

  private String note;

  private String permission;

  private String ipAddresses;

  private Integer validDays;

  private String status;

  private Long createTime;

  private Long updateTime;
}
