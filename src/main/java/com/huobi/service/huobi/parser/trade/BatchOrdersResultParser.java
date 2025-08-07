package com.huobi.service.huobi.parser.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.trade.BatchOrdersResult;
import com.huobi.model.trade.FeeRate;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class BatchOrdersResultParser implements HuobiModelParser<BatchOrdersResult> {
    @Override
    public BatchOrdersResult parse(JSONObject json) {
        return json.toJavaObject(BatchOrdersResult.class);
    }

    @Override
    public BatchOrdersResult parse(JSONArray json) {
        return null;
    }

    @Override
    public List<BatchOrdersResult> parseArray(JSONArray jsonArray) {

        if (jsonArray == null || jsonArray.size() <= 0) {
            return new ArrayList<>();
        }

        return jsonArray.toJavaList(BatchOrdersResult.class);
    }
}
