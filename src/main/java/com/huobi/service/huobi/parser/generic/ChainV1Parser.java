package com.huobi.service.huobi.parser.generic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.generic.ChainV1;
import com.huobi.model.generic.Currency;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class ChainV1Parser implements HuobiModelParser<ChainV1> {
    @Override
    public ChainV1 parse(JSONObject json) {
        return json.toJavaObject(ChainV1.class);
    }

    @Override
    public ChainV1 parse(JSONArray json) {
        return null;
    }

    @Override
    public List<ChainV1> parseArray(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.size() <= 0) {
            return new ArrayList<>();
        }

        List<ChainV1> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(parse(jsonArray.getJSONObject(i)));
        }
        return list;
    }
}
