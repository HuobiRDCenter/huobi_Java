package com.huobi.service.huobi;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.MarketClient;
import com.huobi.client.req.market.CandlestickRequest;
import com.huobi.client.req.market.MarketDepthRequest;
import com.huobi.client.req.market.MarketDetailMergedRequest;
import com.huobi.client.req.market.MarketDetailRequest;
import com.huobi.client.req.market.MarketHistoryTradeRequest;
import com.huobi.client.req.market.MarketTradeRequest;
import com.huobi.client.req.market.SubCandlestickRequest;
import com.huobi.client.req.market.SubMarketBBORequest;
import com.huobi.client.req.market.SubMarketDepthRequest;
import com.huobi.client.req.market.SubMarketDetailRequest;
import com.huobi.client.req.market.SubMarketTradeRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.Options;
import com.huobi.constant.enums.CandlestickIntervalEnum;
import com.huobi.constant.enums.DepthSizeEnum;
import com.huobi.constant.enums.DepthStepEnum;
import com.huobi.model.market.Candlestick;
import com.huobi.model.market.CandlestickEvent;
import com.huobi.model.market.MarketBBOEvent;
import com.huobi.model.market.MarketDepth;
import com.huobi.model.market.MarketDepthEvent;
import com.huobi.model.market.MarketDetail;
import com.huobi.model.market.MarketDetailEvent;
import com.huobi.model.market.MarketDetailMerged;
import com.huobi.model.market.MarketTicker;
import com.huobi.model.market.MarketTrade;
import com.huobi.model.market.MarketTradeEvent;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.connection.HuobiWebSocketConnection;
import com.huobi.service.huobi.parser.market.CandlestickEventParser;
import com.huobi.service.huobi.parser.market.CandlestickParser;
import com.huobi.service.huobi.parser.market.MarketBBOEventParser;
import com.huobi.service.huobi.parser.market.MarketDepthEventParser;
import com.huobi.service.huobi.parser.market.MarketDepthParser;
import com.huobi.service.huobi.parser.market.MarketDetailEventParser;
import com.huobi.service.huobi.parser.market.MarketDetailMergedParser;
import com.huobi.service.huobi.parser.market.MarketDetailParser;
import com.huobi.service.huobi.parser.market.MarketTickerParser;
import com.huobi.service.huobi.parser.market.MarketTradeEventParser;
import com.huobi.service.huobi.parser.market.MarketTradeParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;
import com.huobi.utils.ResponseCallback;
import com.huobi.utils.SymbolUtils;

public class HuobiMarketService implements MarketClient {

  private Options options;

  private HuobiRestConnection restConnection;

  public HuobiMarketService(Options options) {
    this.options = options;
    restConnection = new HuobiRestConnection(options);
  }


  public static final String REST_CANDLESTICK_PATH = "/market/history/kline";
  public static final String REST_MARKET_DETAIL_MERGED_PATH = "/market/detail/merged";
  public static final String REST_MARKET_DETAIL_PATH = "/market/detail";
  public static final String REST_MARKET_TICKERS_PATH = "/market/tickers";
  public static final String REST_MARKET_DEPTH_PATH = "/market/depth";
  public static final String REST_MARKET_TRADE_PATH = "/market/trade";
  public static final String REST_MARKET_HISTORY_TRADE_PATH = "/market/history/trade";


  public static final String WEBSOCKET_CANDLESTICK_TOPIC = "market.$symbol$.kline.$period$";
  public static final String WEBSOCKET_MARKET_DETAIL_TOPIC = "market.$symbol.detail";
  public static final String WEBSOCKET_MARKET_DEPTH_TOPIC = "market.$symbol.depth.$type";
  public static final String WEBSOCKET_MARKET_TRADE_TOPIC = "market.$symbol.trade.detail";
  public static final String WEBSOCKET_MARKET_BBO_TOPIC = "market.$symbol.bbo";

  @Override
  public List<Candlestick> getCandlestick(CandlestickRequest request) {

    // 参数检查
    InputChecker.checker()
        .checkSymbol(request.getSymbol())
        .checkRange(request.getSize(), 1, 2000, "size")
        .shouldNotNull(request.getInterval(), "CandlestickInterval");

    // 参数构建
    UrlParamsBuilder paramBuilder = UrlParamsBuilder.build()
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("period", request.getInterval().getCode())
        .putToUrl("size", request.getSize());

    JSONObject json = restConnection.executeGet(REST_CANDLESTICK_PATH, paramBuilder);
    JSONArray data = json.getJSONArray("data");
    return new CandlestickParser().parseArray(data);
  }

  @Override
  public void subCandlestick(SubCandlestickRequest request, ResponseCallback<CandlestickEvent> callback) {

    // 检查参数
    InputChecker.checker()
        .shouldNotNull(request.getSymbol(), "symbol")
        .shouldNotNull(request.getInterval(), "interval");
    // 格式化symbol为数组
    List<String> symbolList = SymbolUtils.parseSymbols(request.getSymbol());

    // 检查数组
    InputChecker.checker()
        .checkSymbolList(symbolList);

    List<String> commandList = new ArrayList<>(symbolList.size());
    symbolList.forEach(symbol -> {

      String topic = WEBSOCKET_CANDLESTICK_TOPIC
          .replace("$symbol$", symbol)
          .replace("$period$", request.getInterval().getCode());

      JSONObject command = new JSONObject();
      command.put("sub", topic);
      command.put("id", System.nanoTime());
      commandList.add(command.toJSONString());
    });

    HuobiWebSocketConnection.createMarketConnection(options, commandList, new CandlestickEventParser(), callback, false);
  }

  @Override
  public MarketDetailMerged getMarketDetailMerged(MarketDetailMergedRequest request) {

    // 检查参数
    InputChecker.checker()
        .shouldNotNull(request.getSymbol(), "symbol");

    // 参数构建
    UrlParamsBuilder paramBuilder = UrlParamsBuilder.build()
        .putToUrl("symbol", request.getSymbol());

    JSONObject json = restConnection.executeGet(REST_MARKET_DETAIL_MERGED_PATH, paramBuilder);
    JSONObject data = json.getJSONObject("tick");
    return new MarketDetailMergedParser().parse(data);
  }

  @Override
  public MarketDetail getMarketDetail(MarketDetailRequest request) {

    // 检查参数
    InputChecker.checker()
        .shouldNotNull(request.getSymbol(), "symbol");

    // 参数构建
    UrlParamsBuilder paramBuilder = UrlParamsBuilder.build()
        .putToUrl("symbol", request.getSymbol());

    JSONObject json = restConnection.executeGet(REST_MARKET_DETAIL_PATH, paramBuilder);
    JSONObject data = json.getJSONObject("tick");
    return new MarketDetailParser().parse(data);
  }

  @Override
  public void subMarketDetail(SubMarketDetailRequest request, ResponseCallback<MarketDetailEvent> callback) {
    // 检查参数
    InputChecker.checker()
        .shouldNotNull(request.getSymbol(), "symbol");

    // 格式化symbol为数组
    List<String> symbolList = SymbolUtils.parseSymbols(request.getSymbol());

    // 检查数组
    InputChecker.checker()
        .checkSymbolList(symbolList);

    List<String> commandList = new ArrayList<>(symbolList.size());
    symbolList.forEach(symbol -> {

      String topic = WEBSOCKET_MARKET_DETAIL_TOPIC
          .replace("$symbol", symbol);

      JSONObject command = new JSONObject();
      command.put("sub", topic);
      command.put("id", System.nanoTime());
      commandList.add(command.toJSONString());
    });

    HuobiWebSocketConnection.createMarketConnection(options, commandList, new MarketDetailEventParser(), callback, false);
  }

  @Override
  public List<MarketTicker> getTickers() {

    JSONObject json = restConnection.executeGet(REST_MARKET_TICKERS_PATH, UrlParamsBuilder.build());
    JSONArray data = json.getJSONArray("data");
    return new MarketTickerParser().parseArray(data);
  }

  @Override
  public MarketDepth getMarketDepth(MarketDepthRequest request) {

    // 参数检查
    InputChecker.checker()
        .checkSymbol(request.getSymbol())
        .shouldNotNull(request.getStep(), "step");

    int size = request.getDepth() == null ? DepthSizeEnum.SIZE_20.getSize() : request.getDepth().getSize();

    // 参数构建
    UrlParamsBuilder paramBuilder = UrlParamsBuilder.build()
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("depth", size)
        .putToUrl("type", request.getStep().getStep());

    JSONObject json = restConnection.executeGet(REST_MARKET_DEPTH_PATH, paramBuilder);
    JSONObject data = json.getJSONObject("tick");
    return new MarketDepthParser().parse(data);
  }

  @Override
  public void subMarketDepth(SubMarketDepthRequest request, ResponseCallback<MarketDepthEvent> callback) {
    // 检查参数
    InputChecker.checker()
        .shouldNotNull(request.getSymbol(), "symbol");

    // 格式化symbol为数组
    List<String> symbolList = SymbolUtils.parseSymbols(request.getSymbol());

    // 检查数组
    InputChecker.checker()
        .checkSymbolList(symbolList);

    String step = request.getStep() == null ? DepthStepEnum.STEP0.getStep() : request.getStep().getStep();
    List<String> commandList = new ArrayList<>(symbolList.size());
    symbolList.forEach(symbol -> {

      String topic = WEBSOCKET_MARKET_DEPTH_TOPIC
          .replace("$symbol", symbol)
          .replace("$type",step);

      JSONObject command = new JSONObject();
      command.put("sub", topic);
      command.put("id", System.nanoTime());
      commandList.add(command.toJSONString());
    });

    HuobiWebSocketConnection.createMarketConnection(options, commandList, new MarketDepthEventParser(), callback, false);
  }

  @Override
  public List<MarketTrade> getMarketTrade(MarketTradeRequest request) {
    // 参数检查
    InputChecker.checker()
        .checkSymbol(request.getSymbol());

    // 参数构建
    UrlParamsBuilder paramBuilder = UrlParamsBuilder.build()
        .putToUrl("symbol", request.getSymbol());

    JSONObject json = restConnection.executeGet(REST_MARKET_TRADE_PATH, paramBuilder);
    JSONArray data = json.getJSONObject("tick").getJSONArray("data");
    return new MarketTradeParser().parseArray(data);
  }

  @Override
  public void subMarketTrade(SubMarketTradeRequest request, ResponseCallback<MarketTradeEvent> callback) {

    // 检查参数
    InputChecker.checker()
        .shouldNotNull(request.getSymbol(), "symbol");

    // 格式化symbol为数组
    List<String> symbolList = SymbolUtils.parseSymbols(request.getSymbol());

    // 检查数组
    InputChecker.checker()
        .checkSymbolList(symbolList);

    List<String> commandList = new ArrayList<>(symbolList.size());
    symbolList.forEach(symbol -> {

      String topic = WEBSOCKET_MARKET_TRADE_TOPIC
          .replace("$symbol", symbol);

      JSONObject command = new JSONObject();
      command.put("sub", topic);
      command.put("id", System.nanoTime());
      commandList.add(command.toJSONString());
    });

    HuobiWebSocketConnection.createMarketConnection(options, commandList, new MarketTradeEventParser(), callback, false);
  }

  @Override
  public List<MarketTrade> getMarketHistoryTrade(MarketHistoryTradeRequest request) {
    // 参数检查
    InputChecker.checker()
        .checkSymbol(request.getSymbol());

    int size = request.getSize() == null ? 2000 : request.getSize();

    // 参数构建
    UrlParamsBuilder paramBuilder = UrlParamsBuilder.build()
        .putToUrl("symbol", request.getSymbol())
        .putToUrl("size", size);

    JSONObject json = restConnection.executeGet(REST_MARKET_HISTORY_TRADE_PATH, paramBuilder);
    JSONArray jsonArray = json.getJSONArray("data");
    if (jsonArray == null || jsonArray.size() <= 0) {
      return new ArrayList<>();
    }

    // 解析数据
    List<MarketTrade> resList = new ArrayList<>();
    MarketTradeParser parser = new MarketTradeParser();
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject data = jsonArray.getJSONObject(i);
      JSONArray dataArray = data.getJSONArray("data");
      List<MarketTrade> dataList = parser.parseArray(dataArray);
      if (dataList != null && dataList.size() > 0) {
        resList.addAll(dataList);
      }
    }
    return resList;
  }

  @Override
  public void subMarketBBO(SubMarketBBORequest request,ResponseCallback<MarketBBOEvent> callback) {

    // 检查参数
    InputChecker.checker()
        .shouldNotNull(request.getSymbol(), "symbol");

    // 格式化symbol为数组
    List<String> symbolList = SymbolUtils.parseSymbols(request.getSymbol());

    // 检查数组
    InputChecker.checker()
        .checkSymbolList(symbolList);

    List<String> commandList = new ArrayList<>(symbolList.size());
    symbolList.forEach(symbol -> {

      String topic = WEBSOCKET_MARKET_BBO_TOPIC
          .replace("$symbol", symbol);

      JSONObject command = new JSONObject();
      command.put("sub", topic);
      command.put("id", System.nanoTime());
      commandList.add(command.toJSONString());
    });

    HuobiWebSocketConnection.createMarketConnection(options, commandList, new MarketBBOEventParser(), callback, false);

  }


  public static void main(String[] args) {

    HuobiMarketService marketService = new HuobiMarketService(new HuobiOptions());

    String symbol = "btcusdt";

//    List<Candlestick> list = marketService.getCandlestick(CandlestickRequest.builder()
//        .symbol(symbol)
//        .interval(CandlestickIntervalEnum.MIN15)
//        .size(10)
//        .build());
//
//    list.forEach(candlestick -> {
//      System.out.println(candlestick.toString());
//    });
//
//    MarketDetailMerged marketDetailMerged = marketService.getMarketDetailMerged(MarketDetailMergedRequest.builder().symbol(symbol).build());
//    System.out.println(marketDetailMerged.toString());
//
//    List<MarketTicker> tickerList = marketService.getTickers();
//    tickerList.forEach(marketTicker -> {
//      System.out.println(marketTicker.toString());
//    });
//
//    MarketDepth marketDepth = marketService.getMarketDepth(MarketDepthRequest.builder()
//        .symbol(symbol)
//        .depth(DepthSizeEnum.SIZE_5)
//        .step(DepthStepEnum.STEP0)
//        .build());
//
//    System.out.println(marketDepth.toString());
//
//    marketDepth.getBids().forEach(priceLevel -> {
//      System.out.println("bid: "+priceLevel);
//    });
//    System.out.println("----------------------------");
//    marketDepth.getAsks().forEach(priceLevel -> {
//      System.out.println("asks: "+priceLevel);
//    });
//
//    List<MarketTrade> marketTradeList = marketService.getMarketTrade(MarketTradeRequest.builder().symbol(symbol).build());
//    marketTradeList.forEach(marketTrade -> {
//      System.out.println(marketTrade.toString());
//    });
//
//    List<MarketTrade> marketHistoryTradeList = marketService.getMarketHistoryTrade(MarketHistoryTradeRequest.builder().symbol(symbol).build());
//    marketHistoryTradeList.forEach(marketTrade -> {
//      System.out.println(marketTrade.toString());
//    });
//
//    MarketDetail marketDetail = marketService.getMarketDetail(MarketDetailRequest.builder().symbol(symbol).build());
//    System.out.println(marketDetail.toString());

//    marketService.subCandlestick(SubCandlestickRequest.builder()
//        .symbol(symbol)
//        .interval(CandlestickIntervalEnum.MIN15)
//        .build(),(candlestick)->{
//
//      System.out.println(candlestick.toString());
//    });
//
//    marketService.subMarketDetail(SubMarketDetailRequest.builder().symbol(symbol).build(),(marketDetailEvent)->{
//      System.out.println(marketDetailEvent.toString());
//    });
//
//
//    marketService.subMarketDepth(SubMarketDepthRequest.builder().symbol(symbol).build(),(marketDetailEvent)->{
//      System.out.println(marketDetailEvent.toString());
//    });
//
//
//    marketService.subMarketTrade(SubMarketTradeRequest.builder().symbol(symbol).build(), (marketTradeEvent)->{
//
//      System.out.println("ch:"+marketTradeEvent.getCh());
//      System.out.println("ts:"+marketTradeEvent.getTs());
//
//      marketTradeEvent.getList().forEach(marketTrade -> {
//        System.out.println(marketTrade.toString());
//      });
//
//      System.out.println("-----------------------------");
//    });
//
//    marketService.subMarketBBO(SubMarketBBORequest.builder().symbol(symbol).build(),(marketBBOEvent)->{
//      System.out.println(marketBBOEvent.toString());
//    });
  }

}
