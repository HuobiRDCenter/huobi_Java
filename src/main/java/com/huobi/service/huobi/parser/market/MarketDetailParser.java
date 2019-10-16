package com.huobi.service.huobi.parser.market;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.market.MarketDetail;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class MarketDetailParser implements HuobiModelParser<MarketDetail> {

  @Override
  public MarketDetail parse(JSONObject json) {
    return json.toJavaObject(MarketDetail.class);
  }

  @Override
  public MarketDetail parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketDetail> parseArray(JSONArray jsonArray) {
    return null;
  }
}
