package com.huobi.service.huobi.parser.account;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.crossmargin.GeneralRepayLoanResult;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class GeneralRepayLoanResultParser implements HuobiModelParser<GeneralRepayLoanResult> {
    @Override
    public GeneralRepayLoanResult parse(JSONObject json) {
        return json.toJavaObject(GeneralRepayLoanResult.class);
    }

    @Override
    public GeneralRepayLoanResult parse(JSONArray json) {
        return null;
    }

    @Override
    public List<GeneralRepayLoanResult> parseArray(JSONArray jsonArray) {
        return jsonArray.toJavaList(GeneralRepayLoanResult.class);
    }
}
