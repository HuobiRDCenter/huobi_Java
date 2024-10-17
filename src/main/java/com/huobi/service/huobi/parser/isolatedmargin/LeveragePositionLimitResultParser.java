package com.huobi.service.huobi.parser.isolatedmargin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.isolatedmargin.LeveragePositionLimitResult;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class LeveragePositionLimitResultParser implements HuobiModelParser<LeveragePositionLimitResult> {
    @Override
    public LeveragePositionLimitResult parse(JSONObject json) {
        return json.toJavaObject(LeveragePositionLimitResult.class);
    }

    @Override
    public LeveragePositionLimitResult parse(JSONArray json) {
        return null;
    }

    @Override
    public List<LeveragePositionLimitResult> parseArray(JSONArray jsonArray) {

        if (jsonArray == null || jsonArray.size() <= 0) {
            return new ArrayList<>();
        }

        return jsonArray.toJavaList(LeveragePositionLimitResult.class);
    }
}
