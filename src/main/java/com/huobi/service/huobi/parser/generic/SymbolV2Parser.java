package com.huobi.service.huobi.parser.generic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.generic.Person;
import com.huobi.model.generic.Symbol;
import com.huobi.model.generic.SymbolV2;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SymbolV2Parser implements HuobiModelParser<SymbolV2> {

    @Override
    public SymbolV2 parse(JSONObject json) {
        if (json.get("p1") == null)
            return null;

        SymbolV2 result = new SymbolV2();
        result.setP1(new Person(((JSONObject)((JSONArray) json.get("p1")).getFirst()).getInteger("id"),
                ((JSONObject)((JSONArray) json.get("p1")).getFirst()).getString("name"),
                ((JSONObject)((JSONArray) json.get("p1")).getFirst()).getInteger("weight")));
        result.setBc(json.getString("bc"));
        result.setLr(json.getBigDecimal("lr"));
        result.setToa(json.getLong("toa"));
        result.setFp(json.getBigDecimal("fp"));
        result.setDn(json.getString("dn"));
        result.setSc(json.getString("sc"));
        result.setQc(json.getString("qc"));
        result.setState(json.getString("state"));
        result.setWr(json.getString("wr"));
        result.setSp(json.getString("sp"));
        result.setCd(json.getBoolean("cd"));
        result.setTap(json.getBigDecimal("tap"));
        result.setBcdn(json.getString("bcdn"));
        result.setQcdn(json.getString("qcdn"));
        result.setTe(json.getBoolean("te"));
        result.setTtp(json.getBigDecimal("ttp"));
        result.setTpp(json.getBigDecimal("tpp"));
        result.setWhe(json.getBoolean("whe"));
        result.setW(json.getInteger("w"));

        return result;
    }

    @Override
    public SymbolV2 parse(JSONArray json) {
        System.out.println(json);
        return null;
    }

    public SymbolV2 parse(Object json) {
        if (json instanceof JSONArray)
            return parse((JSONArray) json);
        else
            return parse((JSONObject) json);
    }

    @Override
    public List<SymbolV2> parseArray(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.size() <= 0) {
            return new ArrayList<>();
        }

        return (List<SymbolV2>) jsonArray.stream()
                .map(this::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
