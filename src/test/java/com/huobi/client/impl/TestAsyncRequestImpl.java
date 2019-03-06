package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.huobi.client.AsyncResult;
import com.huobi.client.ResponseCallback;
import com.huobi.client.impl.utils.SucceededAsyncResult;
import com.huobi.client.model.ExchangeInfo;
import com.huobi.client.model.Symbol;
import com.huobi.client.model.Trade;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RestApiInvoker.class})
@PowerMockIgnore({"okhttp3.*"})
@SuppressWarnings("unchecked")
public class TestAsyncRequestImpl {

  @Mock
  RestApiRequestImpl restApiRequestImpl;


  @Test
  public void testGetLastTrade() throws Exception {
    mockStatic(RestApiInvoker.class);
    ArgumentCaptor<RestApiRequest> captor1 = ArgumentCaptor.forClass(RestApiRequest.class);
    ArgumentCaptor<ResponseCallback> captor2 = ArgumentCaptor.forClass(ResponseCallback.class);
    PowerMockito.doNothing().when(
        RestApiInvoker.class, "callASync", captor1.capture(), captor2.capture());
    ResponseCallback<AsyncResult<Trade>> mockCallback = mock(ResponseCallback.class);
    RestApiRequest<List<Trade>> restApiRequest = new RestApiRequest<>();
    when(restApiRequestImpl.getHistoricalTrade("asd", null, 1)).thenReturn(restApiRequest);
    AsyncRequestImpl impl = new AsyncRequestImpl(restApiRequestImpl);
    impl.getLastTrade("asd", mockCallback);

    assertSame(restApiRequest, captor1.getValue());
    Trade trade = new Trade();
    trade.setPrice(new BigDecimal("1.1"));
    trade.setAmount(new BigDecimal("2.2"));
    List<Trade> tradeList = new LinkedList<>();
    tradeList.add(trade);
    SucceededAsyncResult<List<Trade>> result = new SucceededAsyncResult<>(tradeList);
    captor2.getValue().onResponse(result);

    ArgumentCaptor<AsyncResult> resultCaptor = ArgumentCaptor.forClass(AsyncResult.class);
    verify(mockCallback, times(1)).onResponse(resultCaptor.capture());
    assertTrue(resultCaptor.getValue().succeeded());
    Trade obj = (Trade) resultCaptor.getValue().getData();
    assertEquals(new BigDecimal("1.1"), obj.getPrice());
    assertEquals(new BigDecimal("2.2"), obj.getAmount());
  }

  @Test
  public void testExchangeInfo() throws Exception {
    mockStatic(RestApiInvoker.class);
    ArgumentCaptor<RestApiRequest> captor1 = ArgumentCaptor.forClass(RestApiRequest.class);
    ArgumentCaptor<ResponseCallback> captor2 = ArgumentCaptor.forClass(ResponseCallback.class);
    PowerMockito.doNothing().when(
        RestApiInvoker.class, "callASync", captor1.capture(), captor2.capture());

    ResponseCallback<AsyncResult<ExchangeInfo>> mockCallback = mock(ResponseCallback.class);
    RestApiRequest<List<Symbol>> restApiRequest1 = new RestApiRequest<>();
    when(restApiRequestImpl.getSymbols()).thenReturn(restApiRequest1);
    RestApiRequest<List<String>> restApiRequest2 = new RestApiRequest<>();
    when(restApiRequestImpl.getCurrencies()).thenReturn(restApiRequest2);
    List<String> currency = new LinkedList<>();
    currency.add("123");
    when(RestApiInvoker.callSync(restApiRequest2)).thenReturn(currency);
    AsyncRequestImpl impl = new AsyncRequestImpl(restApiRequestImpl);
    impl.getExchangeInfo(mockCallback);

    assertSame(restApiRequest1, captor1.getValue());
    List<Symbol> symbols = new LinkedList<>();
    Symbol symbol = new Symbol();
    symbol.setBaseCurrency("btc");
    symbol.setQuoteCurrency("usdt");
    symbols.add(symbol);
    SucceededAsyncResult<List<Symbol>> result = new SucceededAsyncResult<>(symbols);
    captor2.getValue().onResponse(result);

    ArgumentCaptor<AsyncResult> resultCaptor = ArgumentCaptor.forClass(AsyncResult.class);
    verify(mockCallback, times(1)).onResponse(resultCaptor.capture());
    assertTrue(resultCaptor.getValue().succeeded());
    ExchangeInfo obj = (ExchangeInfo) resultCaptor.getValue().getData();
    assertEquals(1, obj.getSymbolList().size());
    assertEquals(1, obj.getCurrencies().size());
    assertEquals("btc", obj.getSymbolList().get(0).getBaseCurrency());
    assertEquals("123", obj.getCurrencies().get(0));
  }


}
