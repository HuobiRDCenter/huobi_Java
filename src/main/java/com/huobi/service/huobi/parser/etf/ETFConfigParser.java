package com.huobi.service.huobi.parser.etf;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.constant.enums.EtfStatusEnum;
import com.huobi.model.etf.ETFConfig;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class ETFConfigParser implements HuobiModelParser<ETFConfig> {

  @Override
  public ETFConfig parse(JSONObject json) {
    return ETFConfig.builder()
        .purchaseMinAmount(json.getInteger("purchase_min_amount"))
        .purchaseMaxAmount(json.getInteger("purchase_max_amount"))
        .redemptionMinAmount(json.getInteger("redemption_min_amount"))
        .purchaseMaxAmount(json.getInteger("redemption_max_amount"))
        .purchaseFeeRate(json.getBigDecimal("purchase_fee_rate"))
        .redemptionFeeRate(json.getBigDecimal("redemption_fee_rate"))
        .status(EtfStatusEnum.find(json.getString("etf_status")))
        .unitPriceList(new ETFUnitPriceParser().parseArray(json.getJSONArray("unit_price")))
        .build();
  }

  @Override
  public ETFConfig parse(JSONArray json) {
    return null;
  }

  @Override
  public List<ETFConfig> parseArray(JSONArray jsonArray) {
    return null;
  }
}
