package com.huobi.client.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.event.TradeStatisticsEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestSubTradeStatistics {

  private String data = "{\n" +
      "    \"ch\":\"market.btcusdt.detail\",\n" +
      "    \"ts\":1550740513421,\n" +
      "    \"tick\":{\n" +
      "        \"amount\":29147.328607142535,\n" +
      "        \"open\":3.0342E-4,\n" +
      "        \"close\":3947.03,\n" +
      "        \"high\":4015,\n" +
      "        \"id\":100359274519,\n" +
      "        \"count\":204966,\n" +
      "        \"low\":3903.5,\n" +
      "        \"version\":100359274519,\n" +
      "        \"vol\":115320213.26007387\n" +
      "    }\n" +
      "}";
  private String data_Error = "{\n" +
      "    \"ch\":\"market.btcusdt.detail\",\n" +
      "    \"tick\":{\n" +
      "        \"amount\":29147.328607142535,\n" +
      "        \"open\":3936.77,\n" +
      "        \"close\":3947.03,\n" +
      "        \"high\":4015,\n" +
      "        \"id\":100359274519,\n" +
      "        \"count\":204966,\n" +
      "        \"low\":3903.5,\n" +
      "        \"version\":100359274519,\n" +
      "        \"vol\":115320213.26007387\n" +
      "    }\n" +
      "}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testRequest() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("12345");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<TradeStatisticsEvent> request = requestImpl.subscribe24HTradeStatisticsEvent(
        symbols, (tradeEvent) -> {
        }, null);
    StubWebsocketConnection websocketConnection = new StubWebsocketConnection(request);
    request.connectionHandler.handle(websocketConnection);
    String subscription = websocketConnection.getWebsocket().popOutputMessage();
    assertTrue(subscription.contains("btcusdt.detail"));
  }

  @Test
  public void testReceive_Normal() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("12345");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<TradeStatisticsEvent> request = requestImpl.subscribe24HTradeStatisticsEvent(
        symbols, (tradeEvent) -> {
        }, null);
    TradeStatisticsEvent event = request.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals("btcusdt", event.getSymbol());
    assertEquals(1550740513421L, event.getTimeStamp());
    //assertEquals(100359274519L, event.getData().getId());
    assertEquals(204966L, event.getData().getCount());
    assertEquals(new BigDecimal("115320213.26007387"), event.getData().getVolume());
    assertEquals(new BigDecimal("0.00030342"), event.getData().getOpen());
    assertEquals(new BigDecimal("3903.5"), event.getData().getLow());
    assertEquals(new BigDecimal("4015"), event.getData().getHigh());
    assertEquals(new BigDecimal("3947.03"), event.getData().getClose());
    assertEquals(new BigDecimal("29147.328607142535"), event.getData().getAmount());

  }

  @Test
  public void testReceive_Error() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("12345");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<TradeStatisticsEvent> request = requestImpl.subscribe24HTradeStatisticsEvent(
        symbols, (tradeEvent) -> {
        }, null);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    TradeStatisticsEvent event = request.jsonParser
        .parseJson(JsonWrapper.parseFromString(data_Error));
  }
}

