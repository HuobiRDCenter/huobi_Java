package com.huobi.service.huobi.parser.market;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.market.MarketTrade;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class MarketTradeParser implements HuobiModelParser<MarketTrade> {

  @Override
  public MarketTrade parse(JSONObject json) {

    Long tradeId = json.getLong("trade-id");
    if (tradeId == null) {
      tradeId = json.getLong("tradeId");
    }

    return MarketTrade.builder()
        .id(json.get("id") == null ? null : json.get("id").toString())
        .tradeId(tradeId)
        .price(new BigDecimal(json.getString("price")))
        .amount(new BigDecimal(json.getString("amount")))
        .direction(json.getString("direction"))
        .ts(json.getLong("ts"))
        .build();
  }

  @Override
  public MarketTrade parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketTrade> parseArray(JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    List<MarketTrade> list = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      list.add(parse(jsonObject));
    }
    return list;
  }
}
