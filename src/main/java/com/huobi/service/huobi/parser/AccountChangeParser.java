package com.huobi.service.huobi.parser;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.constant.enums.BalanceTypeEnum;
import com.huobi.model.account.AccountChange;

public class AccountChangeParser implements HuobiModelParser<AccountChange> {

  @Override
  public AccountChange parse(JSONObject json) {
    AccountChange change = json.toJavaObject(AccountChange.class);
    change.setType(BalanceTypeEnum.find(json.getString("type")));
    return change;
  }

  @Override
  public List<AccountChange> parseArray(JSONArray jsonArray) {

    List<AccountChange> changeList = new ArrayList<>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      changeList.add(parse(jsonObject));
    }
    return changeList;
  }
}
