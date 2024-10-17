package com.huobi.service.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.UserInfo;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.List;

public class UserInfoParser implements HuobiModelParser<UserInfo> {

    @Override
    public UserInfo parse(JSONObject json) {
        return json.toJavaObject(UserInfo.class);
    }

    @Override
    public UserInfo parse(JSONArray json) {
        return null;
    }

    @Override
    public List<UserInfo> parseArray(JSONArray jsonArray) {
        return null;
    }
}
