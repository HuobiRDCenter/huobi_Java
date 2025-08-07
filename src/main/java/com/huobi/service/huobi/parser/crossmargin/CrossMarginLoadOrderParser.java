package com.huobi.service.huobi.parser.crossmargin;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.crossmargin.CrossMarginLoadOrder;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class CrossMarginLoadOrderParser implements HuobiModelParser<CrossMarginLoadOrder> {

  @Override
  public CrossMarginLoadOrder parse(JSONObject json) {
    return CrossMarginLoadOrder.builder()
        .id(json.getLong("id"))
        .userId(json.getLong("user-id"))
        .accountId(json.getLong("account-id"))
        .currency(json.getString("currency"))
        .loanAmount(json.getBigDecimal("loan-amount"))
        .loanBalance(json.getBigDecimal("loan-balance"))
        .interestBalance(json.getBigDecimal("interest-balance"))
        .interestAmount(json.getBigDecimal("interest-amount"))
        .filledPoints(json.getBigDecimal("filled-points"))
        .filledHt(json.getBigDecimal("filled-ht"))
        .state(json.getString("state"))
        .accruedAt(json.getLong("accrued-at"))
        .createdAt(json.getLong("created-at"))
        .build();
  }

  @Override
  public CrossMarginLoadOrder parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CrossMarginLoadOrder> parseArray(JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }
    List<CrossMarginLoadOrder> list = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      list.add(parse(jsonArray.getJSONObject(i)));
    }
    return list;
  }
}
