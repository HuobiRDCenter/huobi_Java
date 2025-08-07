package com.huobi.service.huobi.parser.subuser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.wallet.DeductModeResult;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.List;

public class DeductModeResultParser implements HuobiModelParser<DeductModeResult> {
    @Override
    public DeductModeResult parse(JSONObject json) {
        return null;
    }

    @Override
    public DeductModeResult parse(JSONArray json) {
        return null;
    }

    @Override
    public List<DeductModeResult> parseArray(JSONArray jsonArray) {
        return jsonArray.toJavaList(DeductModeResult.class);
    }
}
