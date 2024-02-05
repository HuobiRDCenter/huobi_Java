package com.huobi.model.subuser;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetSubUserListResult {

  private List<SubUserState> userList;

  private Long nextId;

}
