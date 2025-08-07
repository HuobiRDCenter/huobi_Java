package com.huobi.service.huobi.parser.market;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.market.MarketDepthEvent;
import com.huobi.service.huobi.parser.HuobiModelParser;
import com.huobi.service.huobi.utils.DataUtils;

public class MarketDepthEventParser implements HuobiModelParser<MarketDepthEvent> {

  @Override
  public MarketDepthEvent parse(JSONObject json) {
    String dataKey = DataUtils.getDataKey(json);
    return MarketDepthEvent.builder()
        .ch(json.getString("ch"))
        .ts(json.getLong("ts"))
        .depth(new MarketDepthParser().parse(json.getJSONObject(dataKey)))
        .build();
  }

  @Override
  public MarketDepthEvent parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketDepthEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
