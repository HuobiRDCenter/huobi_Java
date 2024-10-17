package com.huobi.service.huobi.parser.market;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.model.market.MarketTicker;
import com.huobi.model.market.MarketTickerEvent;
import com.huobi.service.huobi.parser.HuobiModelParser;
import com.huobi.service.huobi.utils.DataUtils;

import java.util.List;

public class MarketTickerEventParser implements HuobiModelParser<MarketTickerEvent> {
    @Override
    public MarketTickerEvent parse(JSONObject json) {
        String dataKey = DataUtils.getDataKey(json);

        JSONObject data = json.getJSONObject(dataKey);
        MarketTicker marketTicker = new MarketTickerParser().parse(data);
        return MarketTickerEvent.builder()
                .ch(json.getString("ch"))
                .ts(json.getLong("ts"))
                .ticker(marketTicker)
                .build();
    }

    @Override
    public MarketTickerEvent parse(JSONArray json) {
        return null;
    }

    @Override
    public List<MarketTickerEvent> parseArray(JSONArray jsonArray) {
        return null;
    }
}
