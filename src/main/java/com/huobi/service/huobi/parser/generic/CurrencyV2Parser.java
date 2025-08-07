package com.huobi.service.huobi.parser.generic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.generic.Currency;
import com.huobi.model.generic.CurrencyV2;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class CurrencyV2Parser implements HuobiModelParser<CurrencyV2> {
    @Override
    public CurrencyV2 parse(JSONObject json) {
        return json.toJavaObject(CurrencyV2.class);
    }

    @Override
    public CurrencyV2 parse(JSONArray json) {
        return null;
    }

    @Override
    public List<CurrencyV2> parseArray(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.size() <= 0) {
            return new ArrayList<>();
        }

        List<CurrencyV2> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(parse(jsonArray.getJSONObject(i)));
        }
        return list;
    }
}
