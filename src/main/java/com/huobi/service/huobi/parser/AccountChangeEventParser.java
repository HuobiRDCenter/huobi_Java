package com.huobi.service.huobi.parser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.account.AccountChangeEvent;

public class AccountChangeEventParser implements HuobiModelParser<AccountChangeEvent> {

  @Override
  public AccountChangeEvent parse(JSONObject json) {

    JSONObject data = json.getJSONObject("data");


    return AccountChangeEvent.builder()
        .event(data.getString("event"))
        .list(new AccountChangeParser().parseArray(data.getJSONArray("list")))
        .build();
  }

  @Override
  public List<AccountChangeEvent> parseArray(JSONArray jsonArray) {
    return null;
  }
}
