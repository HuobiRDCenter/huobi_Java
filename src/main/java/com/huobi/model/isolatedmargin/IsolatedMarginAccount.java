package com.huobi.model.isolatedmargin;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.constant.enums.AccountStateEnum;
import com.huobi.constant.enums.AccountTypeEnum;
import com.huobi.constant.enums.IsolatedMarginAccountStateEnum;
import com.huobi.model.account.Balance;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IsolatedMarginAccount {

  private Long id;

  private String symbol;

  private BigDecimal flPrice;

  private String flType;

  private BigDecimal riskRate;

  @JSONField(deserialize = false)
  private AccountTypeEnum type;

  @JSONField(deserialize = false)
  private IsolatedMarginAccountStateEnum state;

  private List<Balance> balanceList;

}
