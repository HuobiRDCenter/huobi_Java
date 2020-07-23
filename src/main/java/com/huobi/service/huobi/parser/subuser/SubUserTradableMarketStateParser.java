package com.huobi.service.huobi.parser.subuser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.subuser.SubUserTradableMarketState;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class SubUserTradableMarketStateParser implements HuobiModelParser<SubUserTradableMarketState> {

  @Override
  public SubUserTradableMarketState parse(JSONObject json) {
    return null;
  }

  @Override
  public SubUserTradableMarketState parse(JSONArray json) {
    return null;
  }

  @Override
  public List<SubUserTradableMarketState> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(SubUserTradableMarketState.class);
  }
}
