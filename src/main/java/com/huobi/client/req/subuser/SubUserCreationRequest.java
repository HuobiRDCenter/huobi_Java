package com.huobi.client.req.subuser;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserCreationRequest {

  private List<SubUserCreationParam> userList;

}
