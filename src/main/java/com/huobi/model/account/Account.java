package com.huobi.model.account;

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
public class Account {

  private Long id;

  private AccountTypeEnum type;

  private AccountStateEnum state;

  private String subtype;

}
