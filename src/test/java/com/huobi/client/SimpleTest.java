package com.huobi.client;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.huobi.client.examples.constants.Constants;
import com.huobi.client.model.enums.MBPLevelEnums;

public class SimpleTest {
	
	@Test
	public void testSubscribeFullMarketMBPEvent() {
		String symbol = "btcusdt";
		SubscriptionOptions options = new SubscriptionOptions();
		SubscriptionClient client = SubscriptionClient.create(Constants.API_KEY, Constants.SECRET_KEY, options);
		client.subscribeFullMarketDepthMBP(symbol, MBPLevelEnums.LEVEL10, event -> {
			System.out.println(event.getAsks());
		});
		try {
			TimeUnit.SECONDS.sleep(10l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSystemStatus() {
		SyncRequestClient client = SyncRequestClient.create(Constants.API_KEY, Constants.SECRET_KEY);
		System.out.println(client.getSystemStatus());
	}
}
