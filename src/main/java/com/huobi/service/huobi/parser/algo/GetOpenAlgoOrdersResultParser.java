package com.huobi.service.huobi.parser.algo;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.algo.GetOpenAlgoOrdersResult;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class GetOpenAlgoOrdersResultParser implements HuobiModelParser<GetOpenAlgoOrdersResult> {

  @Override
  public GetOpenAlgoOrdersResult parse(JSONObject json) {
    return GetOpenAlgoOrdersResult.builder()
        .list(new AlgoOrderParser().parseArray(json.getJSONArray("data")))
        .nextId(json.getLong("nextId"))
        .build();
  }

  @Override
  public GetOpenAlgoOrdersResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<GetOpenAlgoOrdersResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
