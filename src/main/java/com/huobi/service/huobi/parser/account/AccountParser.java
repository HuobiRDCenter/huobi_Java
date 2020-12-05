package com.huobi.service.huobi.parser.account;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.account.Account;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class AccountParser implements HuobiModelParser<Account> {

  @Override
  public Account parse(JSONObject json) {
    Account account = json.toJavaObject(Account.class);
    account.setType(json.getString("type"));
    account.setState(json.getString("state"));

    return account;
  }

  @Override
  public Account parse(JSONArray json) {
    return null;
  }

  @Override
  public List<Account> parseArray(JSONArray jsonArray) {
    List<Account> accountList = new ArrayList<>(jsonArray.size());
    for (int i=0;i<jsonArray.size();i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      accountList.add(parse(jsonObject));
    }

    return accountList;
  }
}
