package com.huobi.service.huobi.parser.market;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.market.MarketBBOEvent;
import com.huobi.service.huobi.parser.HuobiModelParser;
import com.huobi.service.huobi.utils.DataUtils;

public class MarketBBOEventParser implements HuobiModelParser<MarketBBOEvent> {

  @Override
  public MarketBBOEvent parse(JSONObject json) {
    String dataKey = DataUtils.getDataKey(json);

    return MarketBBOEvent.builder()
        .ch(json.getString("ch"))
        .ts(json.getLong("ts"))
        .bbo(new MarketBBOParser().parse(json.getJSONObject(dataKey)))
        .build();
  }

  @Override
  public MarketBBOEvent parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketBBOEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
