package com.huobi.service.huobi.parser.subuser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.subuser.ApiKeyInfo;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class ApiKeyInfoParser implements HuobiModelParser<ApiKeyInfo> {

  @Override
  public ApiKeyInfo parse(JSONObject json) {
    return null;
  }

  @Override
  public ApiKeyInfo parse(JSONArray json) {
    return null;
  }

  @Override
  public List<ApiKeyInfo> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(ApiKeyInfo.class);
  }
}
