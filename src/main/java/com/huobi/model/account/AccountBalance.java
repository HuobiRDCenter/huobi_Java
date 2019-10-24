package com.huobi.model.account;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.AccountStateEnum;
import com.huobi.constant.enums.AccountTypeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountBalance {

  private Long id;

  private AccountTypeEnum type;

  private AccountStateEnum state;

  private List<Balance> list;

  private String subType;

}
