package com.huobi.examples;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.huobi.Constants;
import com.huobi.client.MarketClient;
import com.huobi.client.TradeClient;
import com.huobi.client.req.market.MarketDetailMergedRequest;
import com.huobi.client.req.trade.BatchCancelOpenOrdersRequest;
import com.huobi.client.req.trade.CreateOrderRequest;
import com.huobi.client.req.trade.FeeRateRequest;
import com.huobi.client.req.trade.MatchResultRequest;
import com.huobi.client.req.trade.OpenOrdersRequest;
import com.huobi.client.req.trade.OrderHistoryRequest;
import com.huobi.client.req.trade.OrdersRequest;
import com.huobi.client.req.trade.ReqOrderListRequest;
import com.huobi.client.req.trade.SubOrderUpdateRequest;
import com.huobi.client.req.trade.SubOrderUpdateV2Request;
import com.huobi.client.req.trade.SubTradeClearingRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.enums.OrderSourceEnum;
import com.huobi.constant.enums.OrderStateEnum;
import com.huobi.constant.enums.OrderTypeEnum;
import com.huobi.constant.enums.QueryDirectionEnum;
import com.huobi.constant.enums.StopOrderOperatorEnum;
import com.huobi.model.market.MarketDetailMerged;
import com.huobi.model.trade.BatchCancelOpenOrdersResult;
import com.huobi.model.trade.BatchCancelOrderResult;
import com.huobi.model.trade.FeeRate;
import com.huobi.model.trade.MatchResult;
import com.huobi.model.trade.Order;

public class TradeClientExample {

  public static void main(String[] args) {

    String symbol = "htusdt";
    String marginSymbol = "xrpusdt";
    Long spotAccountId = 1234L;
    Long marginXrpAccountId = 678L;
    Long superMarginAccountId = 567L;

    MarketClient marketClient = MarketClient.create(new HuobiOptions());

    TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY)
        .build());

    String clientOrderId = "T" + System.nanoTime() + "_" + RandomStringUtils.randomAlphanumeric(4);

    MarketDetailMerged marketDetailMerged = marketClient.getMarketDetailMerged(MarketDetailMergedRequest.builder().symbol(symbol).build());

    BigDecimal bidPrice = marketDetailMerged.getBid().getPrice().multiply(new BigDecimal("0.95")).setScale(4, RoundingMode.DOWN);
    BigDecimal askPrice = marketDetailMerged.getAsk().getPrice().multiply(new BigDecimal("1.05")).setScale(4, RoundingMode.DOWN);

    CreateOrderRequest buyLimitRequest = CreateOrderRequest.spotBuyLimit(spotAccountId, clientOrderId, symbol, bidPrice, new BigDecimal("2"));
    Long buyLimitId = tradeService.createOrder(buyLimitRequest);
    System.out.println("create buy-limit order:" + buyLimitId);

    CreateOrderRequest sellLimitRequest = CreateOrderRequest.spotSellLimit(spotAccountId, symbol, askPrice, new BigDecimal("2"));
    Long sellLimitId = tradeService.createOrder(sellLimitRequest);
    System.out.println("create sell-limit order:" + sellLimitId);

    CreateOrderRequest buyMarketRequest = CreateOrderRequest.spotBuyMarket(spotAccountId, symbol, new BigDecimal("5"));
    Long buyMarketId = tradeService.createOrder(buyMarketRequest);
    System.out.println("create buy-market order:" + buyMarketId);

    CreateOrderRequest sellMarketRequest = CreateOrderRequest.spotSellMarket(spotAccountId, symbol, new BigDecimal("1"));
    Long sellMarketId = tradeService.createOrder(sellMarketRequest);
    System.out.println("create sell-market order:" + sellMarketId);

    String superMarginSymbol = "xrpusdt";
    CreateOrderRequest superMarginBuyLimitRequest = CreateOrderRequest
        .superMarginBuyLimit(superMarginAccountId, superMarginSymbol, new BigDecimal("0.17"), new BigDecimal("30"));
    Long superMarginBuyLimitId = tradeService.createOrder(superMarginBuyLimitRequest);
    System.out.println("create super-margin-buy-limit order:" + superMarginBuyLimitId);

    CreateOrderRequest superMarginBuyMarketRequest = CreateOrderRequest
        .superMarginBuyMarket(superMarginAccountId, superMarginSymbol, new BigDecimal("5"));
    Long superMarginBuyMarketId = tradeService.createOrder(superMarginBuyMarketRequest);
    System.out.println("create super-margin-buy-limit order:" + superMarginBuyMarketId);

    CreateOrderRequest superMarginSellLimitRequest = CreateOrderRequest
        .superMarginSellLimit(superMarginAccountId, superMarginSymbol, new BigDecimal("0.2"), new BigDecimal("30"));
    Long superMarginSellLimitId = tradeService.createOrder(superMarginSellLimitRequest);
    System.out.println("create super-margin-sell-limit order:" + superMarginSellLimitId);

    CreateOrderRequest superMarginSellMarketRequest = CreateOrderRequest
        .superMarginSellMarket(superMarginAccountId, superMarginSymbol, new BigDecimal("20"));
    Long superMarginSellMarketId = tradeService.createOrder(superMarginSellMarketRequest);
    System.out.println("create super-margin-sell-limit order:" + superMarginSellMarketId);

    CreateOrderRequest marginBuyLimitRequest = CreateOrderRequest
        .marginBuyLimit(marginXrpAccountId, marginSymbol, new BigDecimal("0.17"), new BigDecimal("30"));
    Long marginBuyLimitId = tradeService.createOrder(marginBuyLimitRequest);
    System.out.println("create margin-buy-limit order:" + marginBuyLimitId);

    CreateOrderRequest marginBuyMarketRequest = CreateOrderRequest.marginBuyMarket(marginXrpAccountId, marginSymbol, new BigDecimal("5.5"));
    Long marginBuyMarketId = tradeService.createOrder(marginBuyMarketRequest);
    System.out.println("create margin-buy-limit order:" + marginBuyMarketId);

    CreateOrderRequest marginSellLimitRequest = CreateOrderRequest
        .marginSellLimit(marginXrpAccountId, marginSymbol, new BigDecimal("0.295"), new BigDecimal("20"));
    Long marginSellLimitId = tradeService.createOrder(marginSellLimitRequest);
    System.out.println("create margin-sell-limit order:" + marginSellLimitId);

    CreateOrderRequest marginSellMarketRequest = CreateOrderRequest.marginSellMarket(marginXrpAccountId, marginSymbol, new BigDecimal("30"));
    Long marginSellMarketId = tradeService.createOrder(marginSellMarketRequest);
    System.out.println("create margin-sell-limit order:" + marginSellMarketId);

    CreateOrderRequest buyStopLossRequest = CreateOrderRequest.buyStopLoss(
        spotAccountId,
        symbol,
        bidPrice.multiply(new BigDecimal("1.5")).setScale(4, RoundingMode.DOWN),
        new BigDecimal("1"),
        bidPrice.multiply(new BigDecimal("1.5")).setScale(4, RoundingMode.DOWN),
        StopOrderOperatorEnum.GTE,
        OrderSourceEnum.API);

    Long buyStopLossId = tradeService.createOrder(buyStopLossRequest);
    System.out.println("create buy-stop-limit order:" + buyStopLossId);

    CreateOrderRequest sellStopLossRequest = CreateOrderRequest.sellStopLoss(
        spotAccountId,
        symbol,
        askPrice.multiply(new BigDecimal("0.95")).setScale(4, RoundingMode.DOWN),
        new BigDecimal("1"),
        askPrice.multiply(new BigDecimal("0.95")).setScale(4, RoundingMode.DOWN),
        StopOrderOperatorEnum.LTE,
        OrderSourceEnum.API);

    Long sellStopLossId = tradeService.createOrder(sellStopLossRequest);
    System.out.println("create sell-stop-limit order:" + sellStopLossId);

    Order clientOrder = tradeService.getOrder(clientOrderId);
    System.out.println(clientOrder.toString());

    int cancelResult = tradeService.cancelOrder(clientOrderId);
    System.out.println(cancelResult);

    Order getOrder = tradeService.getOrder(51210074624L);
    System.out.println(getOrder.toString());

    List<Order> orderList = tradeService.getOpenOrders(OpenOrdersRequest.builder()
        .accountId(spotAccountId)
        .symbol(symbol)
        .build());

    List<Long> openOrderList = new ArrayList<>();
    orderList.forEach(order -> {
      System.out.println(order.toString());
      openOrderList.add(order.getId());
      Long res = tradeService.cancelOrder(order.getId());
      System.out.println("--------cancel order res:" + res + "-----------");
    });

    BatchCancelOrderResult batchCancelOrderResult = tradeService.batchCancelOrder(openOrderList);
    System.out.println(batchCancelOrderResult);

    BatchCancelOpenOrdersResult result = tradeService.batchCancelOpenOrders(BatchCancelOpenOrdersRequest.builder()
        .accountId(spotAccountId)
        .symbol(symbol)
        .build());
    System.out.println("spot batchCancel open Orders:" + result.toString());

    BatchCancelOpenOrdersResult result1 = tradeService.batchCancelOpenOrders(BatchCancelOpenOrdersRequest.builder()
        .accountId(marginXrpAccountId)
        .symbol(marginSymbol)
        .build());
    System.out.println("margin xrp batchCancel open Orders:" + result1.toString());

    BatchCancelOpenOrdersResult result2 = tradeService.batchCancelOpenOrders(BatchCancelOpenOrdersRequest.builder()
        .accountId(superMarginAccountId)
        .symbol(marginSymbol)
        .build());
    System.out.println("super margin batchCancel open Orders:" + result2.toString());

    List<OrderStateEnum> stateList = new ArrayList<>();
    stateList.add(OrderStateEnum.CANCELED);
    stateList.add(OrderStateEnum.FILLED);

    List<OrderTypeEnum> typeList = new ArrayList<>();
    typeList.add(OrderTypeEnum.BUY_LIMIT);
    typeList.add(OrderTypeEnum.SELL_LIMIT);
    typeList.add(OrderTypeEnum.BUY_MARKET);
    typeList.add(OrderTypeEnum.SELL_MARKET);

    List<Order> ordersList = tradeService.getOrders(OrdersRequest.builder()
        .symbol(symbol)
        .states(stateList)
        .build());

    ordersList.forEach(order -> {
      System.out.println(new Date(order.getCreatedAt()) + ":" + order.toString());
    });

    List<Order> historyOrderList = tradeService.getOrdersHistory(OrderHistoryRequest.builder()
        .symbol(symbol)
        .startTime(1565107200000L)
        .direction(QueryDirectionEnum.PREV)
        .build());
    historyOrderList.forEach(order -> {
      System.out.println("History Order : " + new Date(order.getCreatedAt()) + ":" + order.toString());
    });

    List<MatchResult> matchResultList = tradeService.getMatchResult(52035072556L);
    matchResultList.forEach(matchResult -> {
      System.out.println(matchResult.toString());
    });

    List<MatchResult> matchResultList1 = tradeService.getMatchResults(MatchResultRequest.builder()
        .symbol(symbol)
        .build());

    matchResultList1.forEach(matchResult -> {
      System.out.println(new Date(matchResult.getCreatedAt()) + " All : " + matchResult.toString());
    });

    String symbols = symbol + ",eosusdt";
    List<FeeRate> feeRateList = tradeService.getFeeRate(FeeRateRequest.builder().symbols(symbols).build());
    feeRateList.forEach(feeRate -> {
      System.out.println(feeRate.toString());
    });


    tradeService.subOrderUpdateV2(SubOrderUpdateV2Request.builder().symbols("*").build(), orderUpdateV2Event -> {

      System.out.println(orderUpdateV2Event.toString());

    });

    tradeService.subTradeClearing(SubTradeClearingRequest.builder().symbols("*").build(), event -> {
      System.out.println(event.toString());
    });
  }

}
