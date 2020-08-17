package com.huobi.client.examples;

import com.alibaba.fastjson.JSON;
import com.huobi.client.SubscriptionClient;
import com.huobi.client.model.enums.MBPLevelEnums;

public class SubscribeMarketDepthFullMBP {

    public static void main(String[] args) {

        String symbol = "htusdt";
        SubscriptionClient subscriptionClient = SubscriptionClient.create();

        subscriptionClient.subscribeMarketDepthFullMBP(symbol, MBPLevelEnums.LEVEL5, (event) -> {

            System.out.println("callback:" + JSON.toJSONString(event));
        });
    }
}
