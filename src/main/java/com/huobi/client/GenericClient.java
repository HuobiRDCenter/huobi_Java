package com.huobi.client;

import java.util.List;

import com.huobi.client.req.generic.ChainRequest;
import com.huobi.client.req.generic.CurrencyChainsRequest;
import com.huobi.constant.Options;
import com.huobi.constant.enums.ExchangeEnum;
import com.huobi.exception.SDKException;
import com.huobi.model.generic.*;
import com.huobi.service.huobi.HuobiGenericService;

public interface GenericClient {

  String getSystemStatus();

  MarketStatus getMarketStatus();

  List<CurrencyChain> getCurrencyChains(CurrencyChainsRequest request);

  Long getTimestamp();

  List<SymbolV2> getSymbolsV2(Long ts);

  List<Currency> getCurrencyV1(Long ts);

  List<CurrencyV2> getCurrencyV2(Long ts);

  List<SymbolV1> getSymbolsV1(Long ts);

  List<MarketSymbol> getMarketSymbol(String symbols, Long ts);

  List<ChainV1> getChain(ChainRequest request);

  static GenericClient create(Options options) {

    if (options.getExchange().equals(ExchangeEnum.HUOBI)) {
      return new HuobiGenericService(options);
    }
    throw new SDKException(SDKException.INPUT_ERROR, "Unsupport Exchange.");
  }
}
