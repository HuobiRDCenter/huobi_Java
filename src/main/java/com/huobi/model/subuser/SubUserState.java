package com.huobi.model.subuser;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserState {

  private Long uid;

  private String userState;

  private String subUserName;

  private String note;

}
