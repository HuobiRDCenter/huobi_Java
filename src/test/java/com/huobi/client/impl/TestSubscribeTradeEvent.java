package com.huobi.client.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.enums.TradeDirection;
import com.huobi.client.model.event.TradeEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSubscribeTradeEvent {

  private String data = "{\n"
      + "\t\"ch\": \"market.btcusdt.trade.detail\",\n"
      + "\t\"ts\": 1550558574702,\n"
      + "\t\"tick\": {\n"
      + "\t\t\"id\": 100335442624,\n"
      + "\t\t\"ts\": 1550558574684,\n"
      + "\t\t\"data\": [{\n"
      + "\t\t\t\"amount\": 0.001000000000000000,\n"
      + "\t\t\t\"ts\": 1550558574684,\n"
      + "\t\t\t\"id\": 10033544262424890651900,\n"
      + "\t\t\t\"price\": 3892.360000000000000000,\n"
      + "\t\t\t\"direction\": \"sell\"\n"
      + "\t\t}, {\n"
      + "\t\t\t\"amount\": 0.051200000000000000,\n"
      + "\t\t\t\"ts\": 1550558574684,\n"
      + "\t\t\t\"id\": 10033544262424890651183,\n"
      + "\t\t\t\"price\": 3892.350000000000000000,\n"
      + "\t\t\t\"direction\": \"buy\"\n"
      + "\t\t}]\n"
      + "\t}\n"
      + "}";

  private String data_Error = "{\n"
      + "\t\"ch\": \"market.btcusdt.trade.detail\",\n"
      + "\t\"ts\": 1550558574702,\n"
      + "\t\"tick\": {\n"
      + "\t\t\"id\": 100335442624,\n"
      + "\t\t\"ts\": 1550558574684,\n"
      + "\t\t\"data\": [{\n"
      + "\t\t\t\"amount\": 0.001000000000000000,\n"
      + "\t\t\t\"direction\": \"sell\"\n"
      + "\t\t}, {\n"
      + "\t\t\t\"amount\": 0.051200000000000000,\n"
      + "\t\t\t\"direction\": \"buy\"\n"
      + "\t\t}]\n"
      + "\t}\n"
      + "}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testRequest() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<TradeEvent> request = requestImpl.subscribeTradeEvent(
        symbols, (tradeEvent) -> {
        }, null);
    StubWebsocketConnection websocketConnection = new StubWebsocketConnection(request);
    request.connectionHandler.handle(websocketConnection);
    String subscription = websocketConnection.getWebsocket().popOutputMessage();
    assertTrue(subscription.contains("trade.detail"));
  }

  @Test
  public void testReceive_Normal() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<TradeEvent> request = requestImpl.subscribeTradeEvent(
        symbols, (tradeEvent) -> {
        }, null);
    TradeEvent event = request.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals("btcusdt", event.getSymbol());
    assertEquals(2, event.getTradeList().size());
    assertEquals(1550558574702L, event.getTimestamp());
    assertEquals(new BigDecimal("0.001"), event.getTradeList().get(0).getAmount());
    assertEquals(1550558574684L, event.getTradeList().get(0).getTimestamp());
    assertEquals("10033544262424890651900", event.getTradeList().get(0).getTradeId());
    assertEquals(new BigDecimal("3892.36"), event.getTradeList().get(0).getPrice());
    assertEquals(TradeDirection.SELL, event.getTradeList().get(0).getDirection());
    assertEquals(new BigDecimal("0.0512"), event.getTradeList().get(1).getAmount());
    assertEquals(1550558574684L, event.getTradeList().get(1).getTimestamp());
    assertEquals("10033544262424890651183", event.getTradeList().get(1).getTradeId());
    assertEquals(new BigDecimal("3892.35"), event.getTradeList().get(1).getPrice());
    assertEquals(TradeDirection.BUY, event.getTradeList().get(1).getDirection());
  }

  @Test
  public void testReceive_Error() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<TradeEvent> request = requestImpl.subscribeTradeEvent(
        symbols, (tradeEvent) -> {
        }, null);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    request.jsonParser.parseJson(JsonWrapper.parseFromString(data_Error));
  }
}
