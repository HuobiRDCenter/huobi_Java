package com.huobi.client.examples;

import java.math.BigDecimal;

import com.huobi.client.SubscriptionClient;
import com.huobi.client.model.enums.TradeDirection;
import com.huobi.client.utils.TimeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubscribeTradeEvent {

	public static void main(String[] args) {
		SubscriptionClient subscriptionClient = SubscriptionClient.create();
		subscriptionClient.subscribeTradeEvent("gxcusdt", (tradeEvent) -> {
			tradeEvent.getTradeList().stream().forEach(item -> {
				log.info("{}[{}]\t{}(个){}\t价格是{}(usdt)", TimeUtils.defaultConvertTimeStamp(tradeEvent.getTimestamp()),
						item.getDirection() == TradeDirection.BUY ? "买入" : "卖出", item.getAmount(),
						tradeEvent.getSymbol(), item.getPrice());
			});
			;
		});
	}

}
