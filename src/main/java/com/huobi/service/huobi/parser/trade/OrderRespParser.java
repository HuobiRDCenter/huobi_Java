package com.huobi.service.huobi.parser.trade;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.trade.OrderResp;
import com.huobi.service.huobi.parser.HuobiModelParser;

import java.util.List;

public class OrderRespParser implements HuobiModelParser<OrderResp> {
    @Override
    public OrderResp parse(JSONObject json) {
        return json.toJavaObject(OrderResp.class);
    }

    @Override
    public OrderResp parse(JSONArray json) {
        return null;
    }

    @Override
    public List<OrderResp> parseArray(JSONArray jsonArray) {
        return null;
    }
}
