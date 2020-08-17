package com.huobi.client;

import java.math.BigDecimal;
import java.util.Map;

import com.huobi.client.examples.constants.Constants;
import com.huobi.client.impl.RestApiInvoker;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.request.AccountLedgerRequest;
import com.huobi.client.model.request.CandlestickRequest;
import com.huobi.client.model.request.MatchResultRequest;
import com.huobi.client.model.request.NewOrderRequest;
import com.huobi.client.model.request.OpenOrderRequest;
import com.huobi.client.model.request.OrdersRequest;

public class PerformanceTest {


  public static void main(String[] args) {

    for (int i = 0; i < 10; i++) {
      test();
      System.out.println("============finish execute " + i + "============");
    }

  }

  public static void test() {
    String symbol = "adausdt";
    String currency = "ada";
    // 开启性能测试的开关
    RestApiInvoker.enablePerformanceSwitch(true);

    RequestOptions options = new RequestOptions();
    options.setUrl("https://api.huobi.de.com");
    SyncRequestClient client = SyncRequestClient.create(Constants.API_KEY, Constants.SECRET_KEY, options);

    Long startTime = null;
    Long endTime = null;

    // Timestamp
    startTime = System.currentTimeMillis();
    client.getExchangeTimestamp();
    endTime = System.currentTimeMillis();
    printTime("timestamp", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // currencys
    startTime = System.currentTimeMillis();
    client.getCurrencies();
    endTime = System.currentTimeMillis();
    printTime("currencys", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // symbols
    startTime = System.currentTimeMillis();
    client.getSymbols();
    endTime = System.currentTimeMillis();
    printTime("symbols", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // currency info
    startTime = System.currentTimeMillis();
    client.getCurrencyInfo(currency, true);
    endTime = System.currentTimeMillis();
    printTime("currency_info", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // K线
    startTime = System.currentTimeMillis();
    client.getCandlestick(new CandlestickRequest(symbol, CandlestickInterval.MIN15, null, null, 1));
    endTime = System.currentTimeMillis();
    printTime("kline", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 深度
    startTime = System.currentTimeMillis();
    client.getPriceDepth(symbol, 5);
    endTime = System.currentTimeMillis();
    printTime("depth", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 成交
    startTime = System.currentTimeMillis();
    client.getTrade(symbol);
    endTime = System.currentTimeMillis();
    printTime("trade", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 历史成交
    startTime = System.currentTimeMillis();
    client.getHistoricalTrade(symbol, 1);
    endTime = System.currentTimeMillis();
    printTime("history_trade", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // Ticker
    startTime = System.currentTimeMillis();
    client.getBestQuote(symbol);
    endTime = System.currentTimeMillis();
    printTime("ticker", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 24h ticker
    startTime = System.currentTimeMillis();
    client.get24HTradeStatistics(symbol);
    endTime = System.currentTimeMillis();
    printTime("ticker", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 下单
    startTime = System.currentTimeMillis();
    Long orderId = client.createOrder(NewOrderRequest.spotBuyLimit(symbol, new BigDecimal("0.01"), new BigDecimal("500")));
    endTime = System.currentTimeMillis();
    printTime("place_order", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 开仓订单
    startTime = System.currentTimeMillis();
    client.getOpenOrders(new OpenOrderRequest(symbol, AccountType.SPOT, null, 1));
    endTime = System.currentTimeMillis();
    printTime("get_open_order", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 撤单
    startTime = System.currentTimeMillis();
    client.cancelOrder(symbol, orderId);
    endTime = System.currentTimeMillis();
    printTime("cancel_order", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 查询订单
    startTime = System.currentTimeMillis();
    client.getOrder(symbol, orderId);
    endTime = System.currentTimeMillis();
    printTime("get_order", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 查询历史订单
    startTime = System.currentTimeMillis();
    client.getOrders(new OrdersRequest(symbol, OrderState.CANCELED, null, null, null, null, 1, null));
    endTime = System.currentTimeMillis();
    printTime("get_orders", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 查询成交
    startTime = System.currentTimeMillis();
    client.getMatchResults(symbol, orderId);
    endTime = System.currentTimeMillis();
    printTime("get_matchresult", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 查询历史成交
    startTime = System.currentTimeMillis();
    client.getMatchResults(new MatchResultRequest(symbol, null, null, null, 1, null));
    endTime = System.currentTimeMillis();
    printTime("get_matchresults", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);

    // 查询账户财务流水
    startTime = System.currentTimeMillis();
    client.getAccountLedgers(new AccountLedgerRequest(12477272L));
    endTime = System.currentTimeMillis();
    printTime("getAccountLedgers", startTime, endTime, RestApiInvoker.EXECUTE_COST_MAP);
  }


  public static void printTime(String key, Long startTime, Long endTime, Map<String, Long> executeTimeMap) {

    Long executeStartTime = executeTimeMap.get(RestApiInvoker.START_TIME_KEY);
    Long executeEndTime = executeTimeMap.get(RestApiInvoker.END_TIME_KEY);
    Long totalCost = endTime - startTime;
    Long executeCost = executeEndTime - executeStartTime;
    Long innerCost = totalCost - executeCost;

    System.out.println("| " + key + " |" + totalCost + " | " + executeCost + "|" + innerCost + " |");

  }

}
