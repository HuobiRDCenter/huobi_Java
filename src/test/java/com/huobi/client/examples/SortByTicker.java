package com.huobi.client.examples;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.TradeStatistics;
import com.huobi.client.utils.EmailUtil;

import io.github.biezhi.ome.OhMyEmail;
import io.github.biezhi.ome.SendMailException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Slf4j
public class SortByTicker {
	static class Ticker {
		public String symbol;
		public TradeStatistics statistics;
	}

	public static void main(String[] args) {
		SyncRequestClient syncRequestClient = SyncRequestClient.create();
		Map<String, TradeStatistics> tickers1 = syncRequestClient.getTickers();

		List<Ticker> list = new LinkedList<>();
		for (Entry<String, TradeStatistics> entry : tickers1.entrySet()) {
			Ticker ticker = new Ticker();
			ticker.symbol = entry.getKey();
			ticker.statistics = entry.getValue();
			list.add(ticker);
		}
		list = list.stream().filter(item -> item.symbol.endsWith("usdt")).collect(Collectors.toList());
		Collections.sort(list, (o1, o2) -> -o1.statistics.getVolume().compareTo(o2.statistics.getVolume()));

		System.out.println("Sort by Amount");
		StringBuffer emailStrs = new StringBuffer();
		emailStrs.append(
				"<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\"><tr><th>交易对</th><th>交易数量</th><th>交易金额</th></tr>");
		for (Ticker ticker : list) {
			emailStrs.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>", ticker.symbol,
					ticker.statistics.getAmount().toPlainString(), ticker.statistics.getVolume()));
		}
		emailStrs.append("</table>");
		try {
			EmailUtil.getInstance();
			OhMyEmail.subject("成交量排行").from("行情app").to("gongran@foxmail.com")
					.html("<h1 font=red>" + emailStrs.toString() + "</h1>").send();
		} catch (SendMailException e) {
			e.printStackTrace();
			log.error("邮件发送失败！{}", e.getMessage());
		}
	}
}
