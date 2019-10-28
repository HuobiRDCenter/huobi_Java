package com.huobi.service.huobi.parser.isolatedmargin;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.isolatedmargin.IsolatedMarginAccount;
import com.huobi.service.huobi.parser.HuobiModelParser;
import com.huobi.service.huobi.parser.account.BalanceParser;

public class IsolatedMarginAccountParser implements HuobiModelParser<IsolatedMarginAccount> {

  @Override
  public IsolatedMarginAccount parse(JSONObject json) {
    IsolatedMarginAccount account = json.toJavaObject(IsolatedMarginAccount.class);
    account.setType(json.getString("type"));
    account.setState(json.getString("state"));
    account.setFlPrice(json.getBigDecimal("fl-price"));
    account.setFlType(json.getString("fl-type"));
    account.setRiskRate(json.getBigDecimal("risk-rate"));
    account.setBalanceList(new BalanceParser().parseArray(json.getJSONArray("list")));
    return account;
  }

  @Override
  public IsolatedMarginAccount parse(JSONArray json) {
    return null;
  }

  @Override
  public List<IsolatedMarginAccount> parseArray(JSONArray jsonArray) {

    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    List<IsolatedMarginAccount> list = new ArrayList<>();
    for (int i=0;i<jsonArray.size();i++) {
      JSONObject data = jsonArray.getJSONObject(i);
      list.add(parse(data));
    }
    return list;
  }
}
