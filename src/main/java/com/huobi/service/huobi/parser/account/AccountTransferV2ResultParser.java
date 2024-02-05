package com.huobi.service.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.account.AccountTransferResult;
import com.huobi.model.account.AccountTransferV2Result;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.List;

public class AccountTransferV2ResultParser implements HuobiModelParser<AccountTransferV2Result> {
    @Override
    public AccountTransferV2Result parse(JSONObject json) {
        return AccountTransferV2Result.builder()
                .success(json.getString("success"))
                .data(json.getLong("data"))
                .code(json.getLong("code"))
                .message(json.getString("message"))
                .build();
    }

    @Override
    public AccountTransferV2Result parse(JSONArray json) {
        return null;
    }

    @Override
    public List<AccountTransferV2Result> parseArray(JSONArray jsonArray) {
        return null;
    }
}
