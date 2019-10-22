package com.huobi.service.huobi.parser.generic;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.generic.Symbol;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class SymbolParser implements HuobiModelParser<Symbol> {

  @Override
  public Symbol parse(JSONObject json) {
    return Symbol.builder()
        .symbol(json.getString("symbol"))
        .baseCurrency(json.getString("base-currency"))
        .quoteCurrency(json.getString("quote-currency"))
        .pricePrecision(json.getInteger("price-precision"))
        .amountPrecision(json.getInteger("amount-precision"))
        .symbolPartition(json.getString("symbol-partition"))
        .valuePrecision(json.getInteger("value-precision"))
        .minOrderAmt(json.getBigDecimal("min-order-amt"))
        .maxOrderAmt(json.getBigDecimal("max-order-amt"))
        .minOrderValue(json.getBigDecimal("min-order-value"))
        .leverageRatio(json.getInteger("leverage-ratio"))
        .build();
  }

  @Override
  public Symbol parse(JSONArray json) {
    return null;
  }

  @Override
  public List<Symbol> parseArray(JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    List<Symbol> list = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      list.add(parse(jsonArray.getJSONObject(i)));
    }

    return list;
  }
}
