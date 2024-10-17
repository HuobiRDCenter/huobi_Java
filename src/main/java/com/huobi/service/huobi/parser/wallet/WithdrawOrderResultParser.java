package com.huobi.service.huobi.parser.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.wallet.WithdrawOrderResult;
import com.huobi.model.wallet.WithdrawQuota;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.List;

public class WithdrawOrderResultParser implements HuobiModelParser<WithdrawOrderResult> {
    @Override
    public WithdrawOrderResult parse(JSONObject json) {
        return json.toJavaObject(WithdrawOrderResult.class);
    }

    @Override
    public WithdrawOrderResult parse(JSONArray json) {
        return null;
    }

    @Override
    public List<WithdrawOrderResult> parseArray(JSONArray jsonArray) {
        return null;
    }
}
