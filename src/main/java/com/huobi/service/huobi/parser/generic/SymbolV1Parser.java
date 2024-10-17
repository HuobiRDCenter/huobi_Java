package com.huobi.service.huobi.parser.generic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.generic.SymbolV1;
import com.huobi.model.generic.SymbolV2;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class SymbolV1Parser implements HuobiModelParser<SymbolV1> {
    @Override
    public SymbolV1 parse(JSONObject json) {
        return json.toJavaObject(SymbolV1.class);
    }

    @Override
    public SymbolV1 parse(JSONArray json) {
        return null;
    }

    @Override
    public List<SymbolV1> parseArray(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.size() <= 0) {
            return new ArrayList<>();
        }

        List<SymbolV1> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(parse(jsonArray.getJSONObject(i)));
        }
        return list;
    }
}
