package com.huobi.service.huobi.parser.trade;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.trade.OrderUpdateV2;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class OrderUpdateV2Parser implements HuobiModelParser<OrderUpdateV2> {

  @Override
  public OrderUpdateV2 parse(JSONObject json) {
    return json.toJavaObject(OrderUpdateV2.class);
  }

  @Override
  public OrderUpdateV2 parse(JSONArray json) {
    return null;
  }

  @Override
  public List<OrderUpdateV2> parseArray(JSONArray jsonArray) {
    return null;
  }
}
