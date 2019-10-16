package com.huobi.client;

import java.util.List;

import com.huobi.client.req.CandlestickRequest;
import com.huobi.client.req.SubCandlestickRequest;
import com.huobi.constant.Options;
import com.huobi.constant.enums.ExchangeEnum;
import com.huobi.exception.SDKException;
import com.huobi.model.market.Candlestick;
import com.huobi.model.market.CandlestickEvent;
import com.huobi.service.huobi.HuobiMarketService;
import com.huobi.utils.ResponseCallback;

public interface MarketClient {

  List<Candlestick> getCandlestick(CandlestickRequest request);

  void subCandlestick(SubCandlestickRequest request, ResponseCallback<CandlestickEvent> callback);

  void getMarketDetailMarged();

  void getMarketDetail();

  void subscribeMarketDetail();

  void getTickers();

  void getMarketDepth();

  void subscribeMarketDepth();

  void getMarketTrade();

  void subscribeMarketTrade();

  void getMarketHistoryTrade();

  void subscribeMarketBBO();


  static MarketClient create(Options options){

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiMarketService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }


}
