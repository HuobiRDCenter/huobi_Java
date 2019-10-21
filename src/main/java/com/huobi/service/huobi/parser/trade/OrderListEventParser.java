package com.huobi.service.huobi.parser.trade;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.trade.OrderListEvent;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class OrderListEventParser implements HuobiModelParser<OrderListEvent> {

  @Override
  public OrderListEvent parse(JSONObject json) {
    return OrderListEvent.builder()
        .topic(json.getString("topic"))
        .ts(json.getLong("ts"))
        .orderList(new OrderParser().parseArray(json.getJSONArray("data")))
        .build();
  }

  @Override
  public OrderListEvent parse(JSONArray json) {
    return null;
  }

  @Override
  public List<OrderListEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
