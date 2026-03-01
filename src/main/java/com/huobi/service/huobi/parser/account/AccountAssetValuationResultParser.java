package com.huobi.service.huobi.parser.account;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.account.AccountAssetValuationResult;

import com.huobi.service.huobi.parser.HuobiModelParser;

public class AccountAssetValuationResultParser implements HuobiModelParser<AccountAssetValuationResult> {

  @Override
  public AccountAssetValuationResult parse(JSONObject json) {
    return json.toJavaObject(AccountAssetValuationResult.class);
  }

  @Override
  public AccountAssetValuationResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<AccountAssetValuationResult> parseArray(JSONArray jsonArray) {
    return null;
  }

}
