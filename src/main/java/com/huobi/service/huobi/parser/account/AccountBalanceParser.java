package com.huobi.service.huobi.parser.account;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.constant.enums.AccountStateEnum;
import com.huobi.constant.enums.AccountTypeEnum;
import com.huobi.model.account.AccountBalance;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class AccountBalanceParser implements HuobiModelParser<AccountBalance> {

  @Override
  public AccountBalance parse(JSONObject json) {

    AccountBalance accountBalance = json.toJavaObject(AccountBalance.class);
    accountBalance.setType(AccountTypeEnum.find(json.getString("type")));
    accountBalance.setState(AccountStateEnum.find(json.getString("state")));
    accountBalance.setUserId(json.getLong("user-id"));
    accountBalance.setList(new BalanceParser().parseArray(json.getJSONArray("list")));

    return accountBalance;
  }

  @Override
  public AccountBalance parse(JSONArray json) {
    return null;
  }

  @Override
  public List<AccountBalance> parseArray(JSONArray jsonArray) {

    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    List<AccountBalance> list = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      list.add(parse(jsonObject));
    }

    return list;
  }
}
