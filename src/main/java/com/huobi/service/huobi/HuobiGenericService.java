package com.huobi.service.huobi;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.GenericClient;
import com.huobi.client.req.generic.ChainRequest;
import com.huobi.client.req.generic.CurrencyChainsRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.Options;
import com.huobi.model.generic.*;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.parser.generic.*;
import com.huobi.service.huobi.signature.UrlParamsBuilder;

public class HuobiGenericService implements GenericClient {

  public static final String GET_SYSTEM_STATUS_URL = "https://status.huobigroup.com/api/v2/summary.json";//获取当前系统状态
  public static final String GET_MARKET_STATUS_PATH = "/v2/market-status";//获取当前市场状态
  public static final String GET_CURRENCY_CHAINS_PATH = "/v2/reference/currencies";//APIv2币链参考信息
  public static final String GET_TIMESTAMP = "/v1/common/timestamp";//获取当前系统时间戳
  public static final String GET_SYMBOLS_PATH_V2 = "/v2/settings/common/symbols";//获取所有交易对(V2)
  public static final String GET_CURRENCY_PATH_V2 = "/v2/settings/common/currencies";//获取所有币种(V2)
  public static final String GET_CURRENCY_PATH_V1 = "/v1/settings/common/currencys";//获取币种配置
  public static final String GET_SYMBOLS_PATH_V1 = "/v1/settings/common/symbols";//获取交易对配置
  public static final String GET_MARKET_SYMBOLS_PATH = "/v1/settings/common/market-symbols";//获取市场交易对配置
  public static final String GET_CHAIN_PATH = "/v1/settings/common/chains";//查询链信息

  private Options options;

  private HuobiRestConnection restConnection;

  public HuobiGenericService(Options options) {
    this.options = options;
    restConnection = new HuobiRestConnection(options);
  }

  @Override
  public String getSystemStatus() {
    String response = restConnection.executeGetString(GET_SYSTEM_STATUS_URL,UrlParamsBuilder.build());
    return response;
  }

  @Override
  public MarketStatus getMarketStatus() {
    JSONObject jsonObject = restConnection.executeGet(GET_MARKET_STATUS_PATH, UrlParamsBuilder.build());
    JSONObject data = jsonObject.getJSONObject("data");
    return new MarketStatusParser().parse(data);
  }

  @Override
  public List<CurrencyChain> getCurrencyChains(CurrencyChainsRequest request) {

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("currency",request.getCurrency())
        .putToUrl("authorizedUser",request.isAuthorizedUser()+"");

    JSONObject jsonObject = restConnection.executeGet(GET_CURRENCY_CHAINS_PATH,builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new CurrencyChainParser().parseArray(data);
  }

  @Override
  public Long getTimestamp() {
    JSONObject jsonObject = restConnection.executeGet(GET_TIMESTAMP, UrlParamsBuilder.build());
    return jsonObject.getLong("data");
  }

  @Override
  public List<SymbolV2> getSymbolsV2(Long ts) {
    UrlParamsBuilder builder = UrlParamsBuilder.build()
            .putToUrl("ts", ts);
    JSONObject jsonObject = restConnection.executeGet(GET_SYMBOLS_PATH_V2, builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new SymbolV2Parser().parseArray(data).subList(0, Math.toIntExact(ts));
  }

  @Override
  public List<Currency> getCurrencyV1(Long ts) {
    UrlParamsBuilder builder = UrlParamsBuilder.build()
            .putToUrl("ts", ts);
    JSONObject jsonObject = restConnection.executeGet(GET_CURRENCY_PATH_V1, builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new CurrencyParser().parseArray(data);
  }

  @Override
  public List<CurrencyV2> getCurrencyV2(Long ts) {
    UrlParamsBuilder builder = UrlParamsBuilder.build()
            .putToUrl("ts", ts);
    JSONObject jsonObject = restConnection.executeGet(GET_CURRENCY_PATH_V2, builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new CurrencyV2Parser().parseArray(data);
  }

  @Override
  public List<SymbolV1> getSymbolsV1(Long ts) {
    UrlParamsBuilder builder = UrlParamsBuilder.build()
            .putToUrl("ts", ts);
    JSONObject jsonObject = restConnection.executeGet(GET_SYMBOLS_PATH_V1, builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new SymbolV1Parser().parseArray(data);
  }

  @Override
  public List<MarketSymbol> getMarketSymbol(String symbols, Long ts) {
    UrlParamsBuilder builder = UrlParamsBuilder.build()
            .putToUrl("symbols", symbols)
            .putToUrl("ts", ts);
    JSONObject jsonObject = restConnection.executeGet(GET_MARKET_SYMBOLS_PATH, builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new MarketSymbolParser().parseArray(data);
  }

  @Override
  public List<ChainV1> getChain(ChainRequest request) {
    UrlParamsBuilder builder = UrlParamsBuilder.build()
            .putToUrl("show-desc", request.getShowDesc())
            .putToUrl("currency", request.getCurrency())
            .putToUrl("ts", request.getTs());
    JSONObject jsonObject = restConnection.executeGet(GET_CHAIN_PATH, builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new ChainV1Parser().parseArray(data);
  }


}
