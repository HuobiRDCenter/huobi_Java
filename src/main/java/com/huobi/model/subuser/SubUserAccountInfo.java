package com.huobi.model.subuser;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubUserAccountInfo {

  private String accountType;

  private String activation;

  private Boolean transferrable;

  private List<SubUserAccount> accountIds;

}
