package com.huobi.service.huobi.parser.generic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.generic.MarketSymbol;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class MarketSymbolParser implements HuobiModelParser<MarketSymbol> {
    @Override
    public MarketSymbol parse(JSONObject json) {
        return json.toJavaObject(MarketSymbol.class);
    }

    @Override
    public MarketSymbol parse(JSONArray json) {
        return null;
    }

    @Override
    public List<MarketSymbol> parseArray(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.size() <= 0) {
            return new ArrayList<>();
        }

        List<MarketSymbol> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(parse(jsonArray.getJSONObject(i)));
        }
        return list;
    }
}
