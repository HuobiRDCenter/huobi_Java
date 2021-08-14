package com.huobi.service.huobi.parser.market;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.market.MarketDepth;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class MarketDepthParser implements HuobiModelParser<MarketDepth> {

  @Override
  public MarketDepth parse(JSONObject json) {
    return MarketDepth.builder()
        .version(json.getLong("version"))
        .ts(json.getLong("ts"))
        .bids(new PriceLevelParser().parseArray(json.getJSONArray("bids")))
        .asks(new PriceLevelParser().parseArray(json.getJSONArray("asks")))
        .build();
  }

  @Override
  public MarketDepth parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketDepth> parseArray(JSONArray jsonArray) {
    return null;
  }
}
