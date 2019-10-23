package com.huobi.service.huobi.parser.wallet;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.wallet.WithdrawQuota;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class WithdrawQuotaParser implements HuobiModelParser<WithdrawQuota> {

  @Override
  public WithdrawQuota parse(JSONObject json) {
    return json.toJavaObject(WithdrawQuota.class);
  }

  @Override
  public WithdrawQuota parse(JSONArray json) {
    return null;
  }

  @Override
  public List<WithdrawQuota> parseArray(JSONArray jsonArray) {
    return null;
  }
}
