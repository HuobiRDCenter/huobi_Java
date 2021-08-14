package com.huobi.service.huobi.parser.isolatedmargin;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.isolatedmargin.IsolatedMarginCurrencyInfo;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class IsolatedMarginCurrencyInfoParser implements HuobiModelParser<IsolatedMarginCurrencyInfo> {

  @Override
  public IsolatedMarginCurrencyInfo parse(JSONObject json) {
    return IsolatedMarginCurrencyInfo.builder()
        .currency(json.getString("currency"))
        .interestRate(json.getBigDecimal("interest-rate"))
        .minLoanAmt(json.getBigDecimal("min-loan-amt"))
        .maxLoanAmt(json.getBigDecimal("max-loan-amt"))
        .loanableAmt(json.getBigDecimal("loanable-amt"))
        .actualRate(json.getBigDecimal("actual-rate"))
        .build();
  }

  @Override
  public IsolatedMarginCurrencyInfo parse(JSONArray json) {
    return null;
  }

  @Override
  public List<IsolatedMarginCurrencyInfo> parseArray(JSONArray jsonArray) {

    if (jsonArray == null || jsonArray.isEmpty()) {
      return new ArrayList<>();
    }

    List<IsolatedMarginCurrencyInfo> list = new ArrayList<>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      list.add(parse(jsonObject));
    }
    return list;
  }
}
