package com.huobi.service.huobi.parser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.CandlestickEvent;
import com.huobi.service.huobi.utils.DataUtils;

public class CandlestickEventParser implements HuobiModelParser<CandlestickEvent> {

  @Override
  public CandlestickEvent parse(JSONObject json) {

    String dataKey = DataUtils.getDataKey(json);

    return CandlestickEvent.builder()
        .ch(json.getString("ch"))
        .ts(json.getLong("ts"))
        .candlestick(new CandlestickParser().parse(json.getJSONObject(dataKey)))
        .build();
  }

  @Override
  public List<CandlestickEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
