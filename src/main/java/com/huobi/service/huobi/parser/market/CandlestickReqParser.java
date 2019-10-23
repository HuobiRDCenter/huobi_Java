package com.huobi.service.huobi.parser.market;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.market.CandlestickReq;
import com.huobi.service.huobi.parser.HuobiModelParser;
import com.huobi.service.huobi.utils.DataUtils;

public class CandlestickReqParser implements HuobiModelParser<CandlestickReq> {

  @Override
  public CandlestickReq parse(JSONObject json) {

    String dataKey = DataUtils.getDataKey(json);

    return CandlestickReq.builder()
        .ch(json.getString("ch"))
        .ts(json.getLong("ts"))
        .candlestickList(new CandlestickParser().parseArray(json.getJSONArray(dataKey)))
        .build();
  }

  @Override
  public CandlestickReq parse(JSONArray json) {
    return null;
  }

  @Override
  public List<CandlestickReq> parseArray(JSONArray jsonArray) {
    return null;
  }
}
