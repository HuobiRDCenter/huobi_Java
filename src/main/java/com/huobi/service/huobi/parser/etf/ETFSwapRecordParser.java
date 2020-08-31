package com.huobi.service.huobi.parser.etf;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.constant.enums.EtfSwapTypeEnum;
import com.huobi.model.etf.ETFSwapRecord;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class ETFSwapRecordParser implements HuobiModelParser<ETFSwapRecord> {

  @Override
  public ETFSwapRecord parse(JSONObject json) {
    return ETFSwapRecord.builder()
        .id(json.getLong("id"))
        .gmtCreated(json.getLong("gmt_created"))
        .currency(json.getString("currency"))
        .amount(json.getBigDecimal("amount"))
        .type(EtfSwapTypeEnum.find(json.getString("type")))
        .status(json.getInteger("status"))
        .detail(new ETFDetailParser().parse(json.getJSONObject("detail")))
        .build();
  }

  @Override
  public ETFSwapRecord parse(JSONArray json) {
    return null;
  }

  @Override
  public List<ETFSwapRecord> parseArray(JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    List<ETFSwapRecord> list = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      list.add(parse(jsonArray.getJSONObject(i)));
    }
    return list;
  }
}
