package com.huobi.model.subuser;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetSubUserAccountListResult {

  private Long uid;

  private String deductMode;

  private List<SubUserAccountInfo> list;

}
