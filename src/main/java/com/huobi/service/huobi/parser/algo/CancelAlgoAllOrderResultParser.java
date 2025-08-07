package com.huobi.service.huobi.parser.algo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.algo.CancelAlgoAllOrderResult;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.List;

public class CancelAlgoAllOrderResultParser implements HuobiModelParser<CancelAlgoAllOrderResult> {
    @Override
    public CancelAlgoAllOrderResult parse(JSONObject json) {
        return json.toJavaObject(CancelAlgoAllOrderResult.class);
    }

    @Override
    public CancelAlgoAllOrderResult parse(JSONArray json) {
        return null;
    }

    @Override
    public List<CancelAlgoAllOrderResult> parseArray(JSONArray jsonArray) {
        return null;
    }
}
