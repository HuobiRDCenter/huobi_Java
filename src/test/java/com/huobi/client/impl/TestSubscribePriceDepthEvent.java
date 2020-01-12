package com.huobi.client.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.event.PriceDepthEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSubscribePriceDepthEvent {

  private String data = "{\n"
      + "\t\"ch\": \"market.btcusdt.depth.step0\",\n"
      + "\t\"ts\": 1550558788054,\n"
      + "\t\"tick\": {\n"
      + "\t\t\"bids\": [\n"
      + "\t\t\t[3891.940000000000000000, 0.025700000000000000],\n"
      + "\t\t\t[3891.610000000000000000, 0.710000000000000000],\n"
      + "\t\t\t[3891.500000000000000000, 0.001000000000000000]\n"
      + "\t\t],\n"
      + "\t\t\"asks\": [\n"
      + "\t\t\t[3891.950000000000000000, 0.028300000000000000],\n"
      + "\t\t\t[3891.990000000000000000, 1.103500000000000000]\n"
      + "\t\t],\n"
      + "\t\t\"ts\": 1550558788026,\n"
      + "\t\t\"version\": 100335470482\n"
      + "\t}\n"
      + "}";

  private String data_error = "{\n"
      + "\t\"ch\": \"market.btcusdt.depth.step0\",\n"
      + "\t\"ts\": 1550558788054,\n"
      + "\t\"tick\": {\n"
      + "\t\t\"asks\": [\n"
      + "\t\t\t[3891.950000000000000000, 0.028300000000000000],\n"
      + "\t\t\t[3891.990000000000000000, 1.103500000000000000]\n"
      + "\t\t],\n"
      + "\t\t\"ts\": 1550558788026,\n"
      + "\t\t\"version\": 100335470482\n"
      + "\t}\n"
      + "}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testRequest() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<PriceDepthEvent> request = requestImpl.subscribePriceDepthEvent(
        symbols, (priceDepthEvent) -> {
        }, null);
    StubWebsocketConnection websocketConnection = new StubWebsocketConnection(request);
    request.connectionHandler.handle(websocketConnection);
    String subscription = websocketConnection.getWebsocket().popOutputMessage();
    assertTrue(subscription.contains("depth.step0"));
  }

  @Test
  public void testReceive_Normal() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<PriceDepthEvent> request = requestImpl.subscribePriceDepthEvent(
        symbols, (priceDepthEvent) -> {
        }, null);
    PriceDepthEvent event = request.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals("btcusdt", event.getSymbol());
    assertEquals(1550558788054L, event.getTimestamp());
    assertEquals(3, event.getData().getBids().size());
    assertEquals(2, event.getData().getAsks().size());
    assertEquals(new BigDecimal("3891.94"), event.getData().getBids().get(0).getPrice());
    assertEquals(new BigDecimal("0.0257"), event.getData().getBids().get(0).getAmount());
    assertEquals(new BigDecimal("3891.95"), event.getData().getAsks().get(0).getPrice());
    assertEquals(new BigDecimal("0.0283"), event.getData().getAsks().get(0).getAmount());
  }

  @Test
  public void testReceive_Error() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<PriceDepthEvent> request = requestImpl.subscribePriceDepthEvent(
        symbols, (priceDepthEvent) -> {
        }, null);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    request.jsonParser.parseJson(JsonWrapper.parseFromString(data_error));
  }
}
