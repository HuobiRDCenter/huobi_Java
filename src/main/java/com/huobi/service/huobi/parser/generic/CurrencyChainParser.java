package com.huobi.service.huobi.parser.generic;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.generic.CurrencyChain;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class CurrencyChainParser implements HuobiModelParser<CurrencyChain> {

  @Override
  public CurrencyChain parse(JSONObject json) {
    return json.toJavaObject(CurrencyChain.class);
  }

  @Override
  public CurrencyChain parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CurrencyChain> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(CurrencyChain.class);
  }
}
