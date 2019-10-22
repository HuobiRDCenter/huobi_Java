package com.huobi.service.huobi.parser.trade;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.trade.OrderListReq;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class OrderListReqParser implements HuobiModelParser<OrderListReq> {

  @Override
  public OrderListReq parse(JSONObject json) {
    return OrderListReq.builder()
        .topic(json.getString("topic"))
        .ts(json.getLong("ts"))
        .orderList(new OrderParser().parseArray(json.getJSONArray("data")))
        .build();
  }

  @Override
  public OrderListReq parse(JSONArray json) {
    return null;
  }

  @Override
  public List<OrderListReq> parseArray(JSONArray jsonArray) {
    return null;
  }
}
