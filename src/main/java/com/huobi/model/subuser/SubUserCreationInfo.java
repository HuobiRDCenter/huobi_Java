package com.huobi.model.subuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserCreationInfo {

  private Long uid;

  private String userName;

  private String node;

  private String errCode;

  private String errMessage;

}
