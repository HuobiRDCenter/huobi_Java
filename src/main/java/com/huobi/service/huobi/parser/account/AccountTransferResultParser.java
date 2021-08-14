package com.huobi.service.huobi.parser.account;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.account.AccountTransferResult;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class AccountTransferResultParser implements HuobiModelParser<AccountTransferResult> {

  @Override
  public AccountTransferResult parse(JSONObject json) {
    return AccountTransferResult.builder()
        .transactId(json.getLong("transact-id"))
        .transactTime(json.getLong("transact-time"))
        .build();
  }

  @Override
  public AccountTransferResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<AccountTransferResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
