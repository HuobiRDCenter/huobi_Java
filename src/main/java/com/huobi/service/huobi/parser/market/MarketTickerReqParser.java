package com.huobi.service.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.market.MarketTicker;
import com.huobi.model.market.MarketTickerReq;
import com.huobi.service.huobi.parser.HuobiModelParser;
import com.huobi.service.huobi.utils.DataUtils;

import java.util.List;

public class MarketTickerReqParser implements HuobiModelParser<MarketTickerReq> {
    @Override
    public MarketTickerReq parse(JSONObject json) {
        String dataKey = DataUtils.getDataKey(json);

        JSONObject data = json.getJSONObject(dataKey);
        MarketTicker marketTicker = new MarketTickerParser().parse(data);
        return MarketTickerReq.builder()
                .ch(json.getString("rep"))
                .ts(json.getLong("ts"))
                .ticker(marketTicker)
                .build();
    }

    @Override
    public MarketTickerReq parse(JSONArray json) {
        return null;
    }

    @Override
    public List<MarketTickerReq> parseArray(JSONArray jsonArray) {
        return null;
    }
}
