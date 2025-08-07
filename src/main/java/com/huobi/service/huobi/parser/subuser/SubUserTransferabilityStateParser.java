package com.huobi.service.huobi.parser.subuser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.subuser.SubUserTransferabilityState;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class SubUserTransferabilityStateParser implements HuobiModelParser<SubUserTransferabilityState> {

  @Override
  public SubUserTransferabilityState parse(JSONObject json) {
    return null;
  }

  @Override
  public SubUserTransferabilityState parse(JSONArray json) {
    return null;
  }

  @Override
  public List<SubUserTransferabilityState> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(SubUserTransferabilityState.class);
  }
}
