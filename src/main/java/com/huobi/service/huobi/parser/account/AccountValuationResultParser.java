package com.huobi.service.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.AccountValuationResult;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.ArrayList;
import java.util.List;

public class AccountValuationResultParser implements HuobiModelParser<AccountValuationResult> {
    @Override
    public AccountValuationResult parse(JSONObject json) {
        AccountValuationResult accountValuationResult = json.toJavaObject(AccountValuationResult.class);
        accountValuationResult.setProfitAccountBalanceList(new ProfitAccountBalanceParser().parseArray(json.getJSONArray("profitAccountBalanceList")));
        return accountValuationResult;
    }

    @Override
    public AccountValuationResult parse(JSONArray json) {
        return null;
    }

    @Override
    public List<AccountValuationResult> parseArray(JSONArray jsonArray) {

        if (jsonArray == null || jsonArray.size() <= 0) {
            return new ArrayList<>();
        }

        List<AccountValuationResult> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.add(parse(jsonObject));
        }

        return list;
    }
}
