package com.huobi.service.huobi.parser.wallet;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.wallet.DepositAddress;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class DepositAddressParser implements HuobiModelParser<DepositAddress> {

  @Override
  public DepositAddress parse(JSONObject json) {
    return json.toJavaObject(DepositAddress.class);
  }

  @Override
  public DepositAddress parse(JSONArray json) {
    return null;
  }

  @Override
  public List<DepositAddress> parseArray(JSONArray jsonArray) {
    return jsonArray.toJavaList(DepositAddress.class);
  }
}
