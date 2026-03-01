package com.huobi.service.huobi.parser.market;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.market.MarketDetailMerged;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class MarketDetailMergedParser implements HuobiModelParser<MarketDetailMerged> {

  @Override
  public MarketDetailMerged parse(JSONObject json) {


    MarketDetailMerged detailMerged =  json.toJavaObject(MarketDetailMerged.class);
    detailMerged.setAsk(new PriceLevelParser().parse(json.getJSONArray("ask")));
    detailMerged.setBid(new PriceLevelParser().parse(json.getJSONArray("bid")));
    return detailMerged;

  }

  @Override
  public MarketDetailMerged parse(JSONArray json) {
    return null;
  }

  @Override
  public List<MarketDetailMerged> parseArray(JSONArray jsonArray) {
    return null;
  }
}
