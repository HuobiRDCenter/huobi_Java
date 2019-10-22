package com.huobi.model.crossmargin;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.model.account.Balance;
import com.huobi.constant.enums.AccountTypeEnum;
import com.huobi.constant.enums.CrossMarginAccountStateEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CrossMarginAccount {

  private Long id;

  @JSONField(deserialize = false)
  private AccountTypeEnum type;

  @JSONField(deserialize = false)
  private CrossMarginAccountStateEnum state;

  private BigDecimal riskRate;

  private BigDecimal acctBalanceSum;

  private BigDecimal debtBalanceSum;

  private List<Balance> balanceList;

}
