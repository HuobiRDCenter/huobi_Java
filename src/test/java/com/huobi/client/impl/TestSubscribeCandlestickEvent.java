package com.huobi.client.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.event.CandlestickEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSubscribeCandlestickEvent {

  private static final String data = "{\n"
      + "\t\"ch\": \"market.btcusdt.kline.1min\",\n"
      + "\t\"ts\": 1550469651403,\n"
      + "\t\"tick\": {\n"
      + "\t\t\"id\": 1550469600,\n"
      + "\t\t\"open\": 3719.880000000000000000,\n"
      + "\t\t\"close\": 3719.910000000000000000,\n"
      + "\t\t\"low\": 3719.450000000000000000,\n"
      + "\t\t\"high\": 3719.990000000000000000,\n"
      + "\t\t\"amount\": 5.470327974838371482,\n"
      + "\t\t\"vol\": 20349.203451999999994465900000000000000000,\n"
      + "\t\t\"count\": 73\n"
      + "\t}\n"
      + "}";

  @Test
  public void testRequest() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<CandlestickEvent> request = requestImpl.subscribeCandlestickEvent(
        symbols, CandlestickInterval.MIN1, (candlestickEvent) -> {
        }, null);
    StubWebsocketConnection websocketConnection = new StubWebsocketConnection(request);
    request.connectionHandler.handle(websocketConnection);
    String subscription = websocketConnection.getWebsocket().popOutputMessage();
    assertTrue(subscription.contains("btcusdt.kline"));
  }

  @Test
  public void testRequest_multiSymbols() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    symbols.add("btcht");
    WebsocketRequest<CandlestickEvent> request = requestImpl.subscribeCandlestickEvent(
        symbols, CandlestickInterval.MIN1, (candlestickEvent) -> {
        }, null);
    StubWebsocketConnection websocketConnection = new StubWebsocketConnection(request);
    request.connectionHandler.handle(websocketConnection);
    assertTrue(websocketConnection.getWebsocket().popOutputMessage().contains("btcusdt.kline"));
    assertTrue(websocketConnection.getWebsocket().popOutputMessage().contains("btcht.kline"));
  }

  @Test
  public void testReceive_Normal() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<CandlestickEvent> request = requestImpl.subscribeCandlestickEvent(
        symbols, CandlestickInterval.MIN1, (candlestickEvent) -> {
        }, null);
    CandlestickEvent event = request.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals("btcusdt", event.getSymbol());
    assertEquals(1550469651403L, event.getTimestamp());
    assertEquals(CandlestickInterval.MIN1, event.getInterval());
    assertEquals(new BigDecimal("3719.88"), event.getData().getOpen());
    assertEquals(new BigDecimal("3719.91"), event.getData().getClose());
    assertEquals(new BigDecimal("3719.45"), event.getData().getLow());
    assertEquals(new BigDecimal("3719.99"), event.getData().getHigh());
    assertEquals(new BigDecimal("5.470327974838371482"), event.getData().getAmount());
    assertEquals(new BigDecimal("20349.2034519999999944659"), event.getData().getVolume());
    assertEquals(73, event.getData().getCount());
  }

  @Test
  public void testReceive_Error() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<CandlestickEvent> request = requestImpl.subscribeCandlestickEvent(
        symbols, CandlestickInterval.MIN1, (candlestickEvent) -> {
        },
        (exception -> {
          assertTrue(exception.getMessage().contains("test"));
        }));
  }
}
