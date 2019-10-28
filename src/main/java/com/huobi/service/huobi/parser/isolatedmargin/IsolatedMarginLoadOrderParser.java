package com.huobi.service.huobi.parser.isolatedmargin;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.isolatedmargin.IsolatedMarginLoadOrder;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class IsolatedMarginLoadOrderParser implements HuobiModelParser<IsolatedMarginLoadOrder> {

  @Override
  public IsolatedMarginLoadOrder parse(JSONObject json) {
    return IsolatedMarginLoadOrder.builder()
        .id(json.getLong("id"))
        .userId(json.getLong("user-id"))
        .accountId(json.getLong("account-id"))
        .symbol(json.getString("symbol"))
        .currency(json.getString("currency"))
        .paidCoin(json.getBigDecimal("paid-coin"))
        .paidPoint(json.getBigDecimal("paid-point"))
        .deductAmount(json.getBigDecimal("deduct-amount"))
        .deductRate(json.getBigDecimal("deduct-rate"))
        .deductCurrency(json.getString("deduct-currency"))
        .loanAmount(json.getBigDecimal("loan-amount"))
        .loanBalance(json.getBigDecimal("loan-balance"))
        .interestBalance(json.getBigDecimal("interest-balance"))
        .interestRate(json.getBigDecimal("interest-rate"))
        .interestAmount(json.getBigDecimal("interest-amount"))
        .state(json.getString("state"))
        .accruedAt(json.getLong("accrued-at"))
        .createdAt(json.getLong("created-at"))
        .updatedAt(json.getLong("updated-at"))
        .build();
  }

  @Override
  public IsolatedMarginLoadOrder parse(JSONArray json) {
    return null;
  }

  @Override
  public List<IsolatedMarginLoadOrder> parseArray(JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }
    List<IsolatedMarginLoadOrder> list = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      list.add(parse(jsonArray.getJSONObject(i)));
    }
    return list;
  }
}
