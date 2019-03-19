package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.when;

import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.LastTradeAndBestQuote;
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.Symbol;
import com.huobi.client.model.Trade;
import com.huobi.client.model.TradeStatistics;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.request.CandlestickRequest;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RestApiInvoker.class)
@PowerMockIgnore({"okhttp3.*"})
public class TestSyncRequestImpl {

  @Mock
  RestApiRequestImpl restApiRequestImpl;

  @Test
  public void testGetLatestCandlestick() {
    RestApiRequest<List<Candlestick>> restApiRequest = new RestApiRequest<>();
    when(restApiRequestImpl.getCandlestick("btcusdt", CandlestickInterval.MIN1, null, null, 10))
        .thenReturn(restApiRequest);
    List<Candlestick> result = new LinkedList<>();
    PowerMockito.mockStatic(RestApiInvoker.class);
    when(RestApiInvoker.callSync(same(restApiRequest))).thenReturn(result);
    SyncRequestImpl impl = new SyncRequestImpl(restApiRequestImpl);
    assertSame(result, impl.getLatestCandlestick("btcusdt", CandlestickInterval.MIN1, 10));
  }

  @Test
  public void testGetCandlestick() {
    RestApiRequest<List<Candlestick>> restApiRequest1 = new RestApiRequest<>();
    RestApiRequest<List<Candlestick>> restApiRequest2 = new RestApiRequest<>();
    CandlestickRequest request1 = new CandlestickRequest("abc", CandlestickInterval.DAY1, null,
        null, 10);
    CandlestickRequest request2 = new CandlestickRequest("def", CandlestickInterval.YEAR1);
    when(restApiRequestImpl.getCandlestick(
        request1.getSymbol(), request1.getInterval(), request1.getStartTime(),
        request1.getEndTime(), request1.getSize())).thenReturn(restApiRequest1);
    when(restApiRequestImpl.getCandlestick(
        request2.getSymbol(), request2.getInterval(), request2.getStartTime(),
        request2.getEndTime(), request2.getSize())).thenReturn(restApiRequest2);
    List<Candlestick> result1 = new LinkedList<>();
    List<Candlestick> result2 = new LinkedList<>();
    PowerMockito.mockStatic(RestApiInvoker.class);
    when(RestApiInvoker.callSync(same(restApiRequest1))).thenReturn(result1);
    when(RestApiInvoker.callSync(same(restApiRequest2))).thenReturn(result2);
    SyncRequestImpl impl = new SyncRequestImpl(restApiRequestImpl);
    assertSame(result1, impl.getCandlestick(request1));
    assertSame(result2, impl.getCandlestick(request2));
  }

  @Test
  public void testGetExchangeTimestamp() {
    RestApiRequest<Long> restApiRequest = new RestApiRequest<>();
    when(restApiRequestImpl.getExchangeTimestamp()).thenReturn(restApiRequest);
    PowerMockito.mockStatic(RestApiInvoker.class);
    long l = 1;
    when(RestApiInvoker.callSync(same(restApiRequest))).thenReturn(l);
    SyncRequestImpl impl = new SyncRequestImpl(restApiRequestImpl);
    assertSame(l, impl.getExchangeTimestamp());
  }

  @Test
  public void testGetPriceDepth() {
    RestApiRequest<PriceDepth> restApiRequest1 = new RestApiRequest<>();
    RestApiRequest<PriceDepth> restApiRequest2 = new RestApiRequest<>();
    when(restApiRequestImpl.getPriceDepth("abc", 11)).thenReturn(restApiRequest1);
    when(restApiRequestImpl.getPriceDepth("abc", 20)).thenReturn(restApiRequest2);
    PriceDepth result1 = new PriceDepth();
    PriceDepth result2 = new PriceDepth();
    PowerMockito.mockStatic(RestApiInvoker.class);
    when(RestApiInvoker.callSync(same(restApiRequest1))).thenReturn(result1);
    when(RestApiInvoker.callSync(same(restApiRequest2))).thenReturn(result2);
    SyncRequestImpl impl = new SyncRequestImpl(restApiRequestImpl);
    assertSame(result1, impl.getPriceDepth("abc", 11));
    assertSame(result2, impl.getPriceDepth("abc"));
  }

  @Test
  public void testGetLastTradeAndBestQuote() {
    RestApiRequest<BestQuote> restApiRequest1 = new RestApiRequest<>();
    RestApiRequest<List<Trade>> restApiRequest2 = new RestApiRequest<>();
    when(restApiRequestImpl.getBestQuote("abc")).thenReturn(restApiRequest1);
    when(restApiRequestImpl.getHistoricalTrade("abc", null, 1)).thenReturn(restApiRequest2);
    BestQuote result1 = new BestQuote();
    result1.setAskAmount(new BigDecimal("1.1"));
    result1.setAskPrice(new BigDecimal("2.1"));
    result1.setBidAmount(new BigDecimal("3.1"));
    result1.setBidPrice(new BigDecimal("3.1"));

    List<Trade> result2 = new LinkedList<>();
    Trade testTrade1 = new Trade();
    testTrade1.setPrice(new BigDecimal("4.1"));
    testTrade1.setAmount(new BigDecimal("4.2"));
    Trade testTrade2 = new Trade();
    testTrade2.setPrice(new BigDecimal("5.1"));
    testTrade2.setAmount(new BigDecimal("5.2"));
    result2.add(testTrade1);
    result2.add(testTrade2);
    PowerMockito.mockStatic(RestApiInvoker.class);
    when(RestApiInvoker.callSync(same(restApiRequest1))).thenReturn(result1);
    when(RestApiInvoker.callSync(same(restApiRequest2))).thenReturn(result2);
    SyncRequestImpl impl = new SyncRequestImpl(restApiRequestImpl);
    LastTradeAndBestQuote result = impl.getLastTradeAndBestQuote("abc");
    assertEquals(result1.getAskAmount(), result.getAskAmount());
    assertEquals(result1.getAskPrice(), result.getAskPrice());
    assertEquals(result1.getBidAmount(), result.getBidAmount());
    assertEquals(result1.getBidPrice(), result.getBidPrice());
    assertEquals(testTrade2.getAmount(), result.getLastTradeAmount());
    assertEquals(testTrade2.getPrice(), result.getLastTradePrice());
  }

  @Test
  public void testGetLastTrade() {
    RestApiRequest<List<Trade>> restApiRequest = new RestApiRequest<>();
    when(restApiRequestImpl.getHistoricalTrade("abc", null, 1)).thenReturn(restApiRequest);
    Trade trade = new Trade();
    trade.setPrice(new BigDecimal("1.1"));
    trade.setAmount(new BigDecimal("2.2"));
    List<Trade> result = new LinkedList<>();
    result.add(trade);
    PowerMockito.mockStatic(RestApiInvoker.class);
    when(RestApiInvoker.callSync(same(restApiRequest))).thenReturn(result);
    SyncRequestImpl impl = new SyncRequestImpl(restApiRequestImpl);
    assertEquals(trade.getPrice(), impl.getLastTrade("abc").getPrice());
    assertEquals(trade.getAmount(), impl.getLastTrade("abc").getAmount());
  }

  @Test
  public void testGetHistoricalTrade() {
    RestApiRequest<List<Trade>> restApiRequest = new RestApiRequest<>();
    when(restApiRequestImpl.getHistoricalTrade("abc", null, 10)).thenReturn(restApiRequest);
    List<Trade> result = new LinkedList<>();
    PowerMockito.mockStatic(RestApiInvoker.class);
    when(RestApiInvoker.callSync(same(restApiRequest))).thenReturn(result);
    SyncRequestImpl impl = new SyncRequestImpl(restApiRequestImpl);
    assertSame(result, impl.getHistoricalTrade("abc", 10));
  }

  @Test
  public void testGet24HTradeStatistics() {
    RestApiRequest<TradeStatistics> restApiRequest = new RestApiRequest<>();
    when(restApiRequestImpl.get24HTradeStatistics("abc")).thenReturn(restApiRequest);
    TradeStatistics result = new TradeStatistics();
    PowerMockito.mockStatic(RestApiInvoker.class);
    when(RestApiInvoker.callSync(same(restApiRequest))).thenReturn(result);
    SyncRequestImpl impl = new SyncRequestImpl(restApiRequestImpl);
    assertSame(result, impl.get24HTradeStatistics("abc"));
  }

  @Test
  public void testGetExchangeInfo() {
    RestApiRequest<List<Symbol>> restApiRequest1 = new RestApiRequest<>();
    RestApiRequest<List<String>> restApiRequest2 = new RestApiRequest<>();
    when(restApiRequestImpl.getSymbols()).thenReturn(restApiRequest1);
    when(restApiRequestImpl.getCurrencies()).thenReturn(restApiRequest2);
    List<Symbol> result1 = new LinkedList<>();
    Symbol symbol = new Symbol();
    symbol.setBaseCurrency("btc");
    symbol.setQuoteCurrency("usdt");
    result1.add(symbol);
    List<String> result2 = new LinkedList<>();
    result2.add("asd");
    result2.add("qwe");
    PowerMockito.mockStatic(RestApiInvoker.class);
    when(RestApiInvoker.callSync(same(restApiRequest1))).thenReturn(result1);
    when(RestApiInvoker.callSync(same(restApiRequest2))).thenReturn(result2);
    SyncRequestImpl impl = new SyncRequestImpl(restApiRequestImpl);
    assertEquals(1, impl.getExchangeInfo().getSymbolList().size());
    assertEquals(2, impl.getExchangeInfo().getCurrencies().size());
    assertEquals("btc", impl.getExchangeInfo().getSymbolList().get(0).getBaseCurrency());
    assertEquals("usdt", impl.getExchangeInfo().getSymbolList().get(0).getQuoteCurrency());
    assertEquals("asd", impl.getExchangeInfo().getCurrencies().get(0));
    assertEquals("qwe", impl.getExchangeInfo().getCurrencies().get(1));
  }
}
