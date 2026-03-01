package com.huobi.service.huobi.parser.trade;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.trade.OrderUpdateV2Event;
import com.huobi.model.trade.TradeClearingEvent;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class TradeClearingEventParser implements HuobiModelParser<TradeClearingEvent> {

  @Override
  public TradeClearingEvent parse(JSONObject json) {
    return TradeClearingEvent.builder()
        .action(json.getString("action"))
        .topic(json.getString("ch"))
        .tradeClearing(new TradeClearingParser().parse(json.getJSONObject("data")))
        .build();
  }

  @Override
  public TradeClearingEvent parse(JSONArray json) {
    return null;
  }

  @Override
  public List<TradeClearingEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
