package com.huobi.client;

import com.alibaba.fastjson.JSONArray;
import com.huobi.client.req.copyTradingTrade.*;

public interface CopyTradingTradeClient {

    JSONArray queryTraderInstruments(String instType);

    JSONArray queryTraderStatistics(String instType);

    JSONArray queryTraderProfitSharingHistory(TraderProfitSharingHistoryParam param);

    JSONArray queryTraderProfitSharingHistorySummary(String instType);

    JSONArray queryTraderUnrealizedProfitSharingSummary(TraderUnrealizedProfitSharingSummaryParam param);

    JSONArray queryTraderFollowers(TraderFollowersParam param);

    JSONArray deleteTraderFollower(TraderFollowerDeleteParam param);

    JSONArray traderTransfer(TraderTransferParam param);

    JSONArray traderFollowerSettings(TraderFollowerSettingsParam param);

    JSONArray traderConfig(String instType);

    JSONArray traderApikey(TraderApikeyParam param);
}
