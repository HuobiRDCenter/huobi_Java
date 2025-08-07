package com.huobi.service.huobi.parser.crossmargin;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.crossmargin.CrossMarginCurrencyInfo;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class CrossMarginCurrencyInfoParser implements HuobiModelParser<CrossMarginCurrencyInfo> {

  @Override
  public CrossMarginCurrencyInfo parse(JSONObject json) {
    return CrossMarginCurrencyInfo.builder()
        .currency(json.getString("currency"))
        .interestRate(json.getBigDecimal("interest-rate"))
        .minLoanAmt(json.getBigDecimal("min-loan-amt"))
        .maxLoanAmt(json.getBigDecimal("max-loan-amt"))
        .loanableAmt(json.getBigDecimal("loanable-amt"))
        .actualRate(json.getBigDecimal("actual-rate"))
        .build();
  }

  @Override
  public CrossMarginCurrencyInfo parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CrossMarginCurrencyInfo> parseArray(JSONArray jsonArray) {

    if (jsonArray == null || jsonArray.isEmpty()) {
      return new ArrayList<>();
    }

    List<CrossMarginCurrencyInfo> list = new ArrayList<>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      list.add(parse(jsonObject));
    }
    return list;
  }
}
