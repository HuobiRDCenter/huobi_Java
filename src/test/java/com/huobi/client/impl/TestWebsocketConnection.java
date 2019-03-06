package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.huobi.client.SubscriptionErrorHandler;
import com.huobi.client.SubscriptionListener;
import com.huobi.client.exception.HuobiApiException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class TestWebsocketConnection {

  private MockWebsocketConnection mockWebsocketConnection;

  private String errorSub = "{\n"
      + "  \"id\": \"id2\",\n"
      + "  \"status\": \"error\",\n"
      + "  \"err-code\": \"bad-request\",\n"
      + "  \"err-msg\": \"invalid topic market.invalidsymbol.kline.1min\",\n"
      + "  \"ts\": 1494301904959\n"
      + "}";

  private String pingForMarketEvent = "{\"ping\": 18212558000}";

  private String pingForTradingEvent = "{\n"
      + "\t\"op\": \"ping\",\n"
      + "\t\"ts\": 1494301904959\n"
      + "}";

  String authOk = "{\n"
      + "  \"op\": \"auth\",\n"
      + "  \"ts\": 1489474081631,\n"
      + "  \"err-code\": 0,\n"
      + "  \"data\": {\n"
      + "    \"user-id\": 1000\n"
      + "  }\n"
      + "}";

  @Test
  public void testOnMessage_error() {
    SubscriptionErrorHandler mockErrorHandler = mock(SubscriptionErrorHandler.class);
    SubscriptionListener<String> testListener = (value) -> {
    };
    WebsocketRequest<String> testRequest = new WebsocketRequest<>(testListener, mockErrorHandler);
    ArgumentCaptor<HuobiApiException> captor = ArgumentCaptor.forClass(HuobiApiException.class);
    MockWebsocketConnection connection = new MockWebsocketConnection("", "", testRequest);
    connection.connect();
    connection.onMessage(new MockOkHttpWebsocket(), connection.buildMockServerMessage(errorSub));
    verify(mockErrorHandler, times(1)).onError(captor.capture());
    HuobiApiException e = captor.getValue();
    assertEquals(HuobiApiException.SUBSCRIPTION_ERROR, e.getErrType());
    assertTrue(e.getMessage().contains("bad-request"));
  }

  @Test
  public void testOnMessage_pingForMarketEvent() {
    SubscriptionListener<String> listener = (string) -> {
    };
    SubscriptionErrorHandler errorHandler = (e) -> {
    };
    WebsocketRequest<String> testRequest = new WebsocketRequest<>(listener, errorHandler);
    MockWebsocketConnection connection = new MockWebsocketConnection("", "", testRequest);
    connection.connect();
    connection.mockOnMessage(pingForMarketEvent);
    assertEquals("{\"pong\":18212558000}", connection.getWebsocket().popOutputMessage());
  }

  @Test
  public void testOnMessage_pingForTradingEvent() {
    SubscriptionListener<String> listener = (string) -> {
    };
    SubscriptionErrorHandler errorHandler = (e) -> {
    };
    WebsocketRequest<String> testRequest = new WebsocketRequest<>(listener, errorHandler);
    MockWebsocketConnection connection = new MockWebsocketConnection("", "", testRequest);
    connection.connect();
    connection.mockOnMessage(pingForTradingEvent);
    assertEquals("{\"op\":\"pong\",\"ts\":1494301904959}",
        connection.getWebsocket().popOutputMessage());
  }

  @Test
  public void testOnMessage_AuthOK() {
    SubscriptionListener<String> listener = (string) -> {
    };
    SubscriptionErrorHandler errorHandler = (e) -> {
    };
    WebsocketRequest<String> testRequest = new WebsocketRequest<>(listener, errorHandler);
    testRequest.authHandler = (connection) -> {
      connection.send("test_authOK");
    };
    MockWebsocketConnection connection = new MockWebsocketConnection("123", "456", testRequest);
    connection.connect();
    assertTrue(connection.getWebsocket().popOutputMessage().contains("Signature"));
    connection.mockOnMessage(authOk);
    assertEquals("test_authOK", connection.getWebsocket().popOutputMessage());
  }
}
