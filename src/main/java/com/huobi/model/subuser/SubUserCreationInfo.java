package com.huobi.model.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserCreationInfo {

  private Long uid;

  private String userName;

  private String note;

  private String errCode;

  private String errMessage;

}
