package com.huobi.service.huobi.parser.subuser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.subuser.GetSubUserListResult;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class GetSubUserListResultParser implements HuobiModelParser<GetSubUserListResult> {

  @Override
  public GetSubUserListResult parse(JSONObject json) {
    return GetSubUserListResult.builder()
        .userList(new SubUserStateParser().parseArray(json.getJSONArray("data")))
        .nextId(json.getLong("nextId"))
        .build();
  }

  @Override
  public GetSubUserListResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<GetSubUserListResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
