package com.huobi.client.req.subuser;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserCreationRequest {

  private List<SubUserCreationParam> userList;

}
