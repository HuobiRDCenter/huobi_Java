package com.huobi.service.huobi.parser.trade;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.trade.CancelFailedResult;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class CancelFailedResultParser implements HuobiModelParser<CancelFailedResult> {

  @Override
  public CancelFailedResult parse(JSONObject json) {
    return CancelFailedResult.builder()
        .errCode(json.getString("err-code"))
        .errMsg(json.getString("err-msg"))
        .orderId(json.getLong("order-id"))
        .orderState(json.getInteger("order-state"))
        .build();
  }

  @Override
  public CancelFailedResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CancelFailedResult> parseArray(JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    List<CancelFailedResult> list = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject data = jsonArray.getJSONObject(i);
      list.add(parse(data));
    }
    return list;
  }
}
