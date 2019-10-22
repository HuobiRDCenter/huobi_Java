package com.huobi.client;

import java.util.List;

import com.huobi.client.req.generic.CurrencyChainsRequest;
import com.huobi.model.generic.CurrencyChain;
import com.huobi.model.generic.Symbol;

public interface GenericClient {

  List<Symbol> getSymbols();

  List<String> getCurrencys();

  List<CurrencyChain> getCurrencyChains(CurrencyChainsRequest request);

  Long getTimestamp();

}
