package com.huobi.model.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
