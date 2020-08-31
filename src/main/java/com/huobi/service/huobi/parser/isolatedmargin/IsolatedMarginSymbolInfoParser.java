package com.huobi.service.huobi.parser.isolatedmargin;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.isolatedmargin.IsolatedMarginSymbolInfo;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class IsolatedMarginSymbolInfoParser implements HuobiModelParser<IsolatedMarginSymbolInfo> {

  @Override
  public IsolatedMarginSymbolInfo parse(JSONObject json) {
    return IsolatedMarginSymbolInfo.builder()
        .symbol(json.getString("symbol"))
        .currencies(new IsolatedMarginCurrencyInfoParser().parseArray(json.getJSONArray("currencies")))
        .build();
  }

  @Override
  public IsolatedMarginSymbolInfo parse(JSONArray json) {
    return null;
  }

  @Override
  public List<IsolatedMarginSymbolInfo> parseArray(JSONArray jsonArray) {

    if (jsonArray == null || jsonArray.isEmpty()) {
      return new ArrayList<>();
    }

    List<IsolatedMarginSymbolInfo> list = new ArrayList<>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      list.add(parse(jsonObject));
    }

    return list;
  }
}
