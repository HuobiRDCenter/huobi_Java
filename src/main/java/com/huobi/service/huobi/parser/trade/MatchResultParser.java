package com.huobi.service.huobi.parser.trade;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.trade.MatchResult;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class MatchResultParser implements HuobiModelParser<MatchResult> {

  @Override
  public MatchResult parse(JSONObject json) {

    MatchResult result = json.toJavaObject(MatchResult.class);
    result.setOrderId(json.getLong("order-id"));
    result.setMatchId(json.getLong("match-id"));
    result.setType(json.getString("type"));
    result.setSource(json.getString("source"));
    result.setFilledAmount(json.getBigDecimal("filled-amount"));
    result.setFilledFees(json.getBigDecimal("filled-fees"));
    result.setCreatedAt(json.getLong("created-at"));
    result.setRole(json.getString("role"));
    result.setFilledPoints(json.getBigDecimal("filled-points"));
    result.setFeeDeductCurrency(json.getString("fee-deduct-currency"));
    result.setFeeCurrency(json.getString("fee-currency"));
    result.setTradeId(json.getLong("trade-id"));
    return result;
  }

  @Override
  public MatchResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MatchResult> parseArray(JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    List<MatchResult> list = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject data = jsonArray.getJSONObject(i);
      list.add(parse(data));
    }
    return list;
  }
}
