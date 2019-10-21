package com.huobi.service.huobi.parser.trade;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.trade.OrderDetailEvent;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class OrderDetailEventParser implements HuobiModelParser<OrderDetailEvent> {

  @Override
  public OrderDetailEvent parse(JSONObject json) {
    return OrderDetailEvent.builder()
        .topic(json.getString("topic"))
        .ts(json.getLong("ts"))
        .order(new OrderParser().parse(json.getJSONObject("data")))
        .build();
  }

  @Override
  public OrderDetailEvent parse(JSONArray json) {
    return null;
  }

  @Override
  public List<OrderDetailEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
