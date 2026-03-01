package com.huobi.service.huobi.parser.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.account.OverviewInfo;
import com.huobi.model.account.UserInfo;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.List;

public class OverviewInfoParser  implements HuobiModelParser<OverviewInfo> {
    @Override
    public OverviewInfo parse(JSONObject json) {
        return json.toJavaObject(OverviewInfo.class);
    }

    @Override
    public OverviewInfo parse(JSONArray json) {
        return null;
    }

    @Override
    public List<OverviewInfo> parseArray(JSONArray jsonArray) {
        return null;
    }
}
