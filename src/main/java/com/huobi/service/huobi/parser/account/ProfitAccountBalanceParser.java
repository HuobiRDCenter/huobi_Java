package com.huobi.service.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.account.Balance;
import com.huobi.model.account.ProfitAccountBalance;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class ProfitAccountBalanceParser implements HuobiModelParser<ProfitAccountBalance> {
    @Override
    public ProfitAccountBalance parse(JSONObject json) {
        ProfitAccountBalance profitAccountBalance = json.toJavaObject(ProfitAccountBalance.class);
        return profitAccountBalance;
    }

    @Override
    public ProfitAccountBalance parse(JSONArray json) {
        return null;
    }

    @Override
    public List<ProfitAccountBalance> parseArray(JSONArray jsonArray) {
        List<ProfitAccountBalance> profitAccountBalanceList = new ArrayList<>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            profitAccountBalanceList.add(parse(jsonObject));
        }
        return profitAccountBalanceList;
    }
}
