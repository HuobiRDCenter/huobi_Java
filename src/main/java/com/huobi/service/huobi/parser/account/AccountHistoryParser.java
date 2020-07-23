package com.huobi.service.huobi.parser.account;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.account.AccountHistory;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class AccountHistoryParser implements HuobiModelParser<AccountHistory> {

  @Override
  public AccountHistory parse(JSONObject json) {
    return AccountHistory.builder()
        .accountId(json.getLong("account-id"))
        .currency(json.getString("currency"))
        .transactAmt(json.getBigDecimal("transact-amt"))
        .transactType(json.getString("transact-type"))
        .availBalance(json.getBigDecimal("avail-balance"))
        .acctBalance(json.getBigDecimal("acct-balance"))
        .transactTime(json.getLong("transact-time"))
        .recordId(json.getLong("record-id"))
        .build();
  }

  @Override
  public AccountHistory parse(JSONArray json) {
    return null;
  }

  @Override
  public List<AccountHistory> parseArray(JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    List<AccountHistory> list = new ArrayList<>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      list.add(parse(jsonObject));
    }
    return list;
  }
}
