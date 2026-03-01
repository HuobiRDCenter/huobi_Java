package com.huobi.service.huobi.parser.generic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.generic.Currency;

import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class CurrencyParser implements HuobiModelParser<Currency> {
    @Override
    public Currency parse(JSONObject json) {
        return json.toJavaObject(Currency.class);
    }

    @Override
    public Currency parse(JSONArray json) {
        return null;
    }

    @Override
    public List<Currency> parseArray(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.size() <= 0) {
            return new ArrayList<>();
        }

        List<Currency> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(parse(jsonArray.getJSONObject(i)));
        }
        return list;
    }
}
