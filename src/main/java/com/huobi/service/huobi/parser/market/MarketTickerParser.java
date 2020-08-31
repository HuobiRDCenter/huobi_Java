package com.huobi.service.huobi.parser.market;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.market.MarketTicker;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class MarketTickerParser implements HuobiModelParser<MarketTicker> {

  @Override
  public MarketTicker parse(JSONObject json) {
    return json.toJavaObject(MarketTicker.class);
  }

  @Override
  public MarketTicker parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketTicker> parseArray(JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    return jsonArray.toJavaList(MarketTicker.class);
  }
}
