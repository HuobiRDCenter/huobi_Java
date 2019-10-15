package com.huobi.client;

import java.util.List;

import com.huobi.client.req.CandlestickRequest;
import com.huobi.client.req.SubCandlestickRequest;
import com.huobi.model.Candlestick;
import com.huobi.model.CandlestickEvent;
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

}
