package com.huobi.service.huobi.parser.generic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.generic.Symbol;
import com.huobi.model.generic.SymbolV2;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class SymbolV2Parser implements HuobiModelParser<SymbolV2> {

    @Override
    public SymbolV2 parse(JSONObject json) {
        return json.toJavaObject(SymbolV2.class);
    }

    @Override
    public SymbolV2 parse(JSONArray json) {
        return null;
    }

    @Override
    public List<SymbolV2> parseArray(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.size() <= 0) {
            return new ArrayList<>();
        }

        List<SymbolV2> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(parse(jsonArray.getJSONObject(i)));
        }
        return list;
    }
}
