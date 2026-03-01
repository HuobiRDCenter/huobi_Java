package com.huobi.model.account;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountBalance {

  private Long id;

  private String type;

  private String state;

  private List<Balance> list;

  private String subType;

}
