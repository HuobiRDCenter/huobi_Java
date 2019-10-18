package com.huobi.service.huobi.parser.trade;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.trade.FeeRate;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class FeeRateParser implements HuobiModelParser<FeeRate> {

  @Override
  public FeeRate parse(JSONObject json) {
    return FeeRate.builder()
        .symbol(json.getString("symbol"))
        .takerFee(json.getBigDecimal("taker-fee"))
        .makerFee(json.getBigDecimal("maker-fee"))
        .build();
  }

  @Override
  public FeeRate parse(JSONArray json) {
    return null;
  }

  @Override
  public List<FeeRate> parseArray(JSONArray jsonArray) {

    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    List<FeeRate> list = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject data = jsonArray.getJSONObject(i);
      list.add(parse(data));
    }

    return list;
  }
}
