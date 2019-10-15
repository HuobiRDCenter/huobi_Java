package com.huobi.service.huobi;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.MarketClient;
import com.huobi.client.model.Symbol;
import com.huobi.client.req.CandlestickRequest;
import com.huobi.client.req.SubCandlestickRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.Options;
import com.huobi.constant.enums.CandlestickIntervalEnum;
import com.huobi.model.Candlestick;
import com.huobi.model.CandlestickEvent;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.connection.HuobiWebSocketConnection;
import com.huobi.service.huobi.parser.CandlestickEventParser;
import com.huobi.service.huobi.parser.CandlestickParser;
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


  public static final String REST_CANDLESTICK_URL = "/market/history/kline";

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

    JSONObject json = restConnection.executeGet(REST_CANDLESTICK_URL, paramBuilder);
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
      JSONObject command = new JSONObject();
      command.put("sub","market."+symbol+".kline."+request.getInterval().getCode());
      command.put("id", System.nanoTime());
      commandList.add(command.toJSONString());
    });

    HuobiWebSocketConnection.createMarketConnection(options, commandList, new CandlestickEventParser(), callback, false);
  }

  @Override
  public void getMarketDetailMarged() {

  }

  @Override
  public void getMarketDetail() {

  }

  @Override
  public void subscribeMarketDetail() {

  }

  @Override
  public void getTickers() {

  }

  @Override
  public void getMarketDepth() {

  }

  @Override
  public void subscribeMarketDepth() {

  }

  @Override
  public void getMarketTrade() {

  }

  @Override
  public void subscribeMarketTrade() {

  }

  @Override
  public void getMarketHistoryTrade() {

  }

  @Override
  public void subscribeMarketBBO() {

  }


  public static void main(String[] args) {

    HuobiMarketService marketService = new HuobiMarketService(new HuobiOptions());


    marketService.subCandlestick(SubCandlestickRequest.builder()
        .symbol("btcusdt")
        .interval(CandlestickIntervalEnum.MIN15)
        .build(),(candlestick)->{

      System.out.println(candlestick.toString());
    });






//    List<Candlestick> list = marketService.getCandlestick(CandlestickRequest.builder()
//        .symbol("btcusdt")
//        .interval(CandlestickIntervalEnum.MIN15)
//        .size(10)
//        .build());
//
//    list.forEach(candlestick -> {
//      System.out.println(candlestick.toString());
//    });
  }

}
