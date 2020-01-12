package com.huobi.client.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Account;
import com.huobi.client.model.User;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.event.OrderUpdateEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AccountsInfoMap.class})
@PowerMockIgnore({"javax.crypto.*"})
public class TestSubscribeOrderUpdateEvent {

  Account testAccount1 = new Account();
  Account testAccount2 = new Account();
  User testUser = new User();

  private String data = "{\n"
      + "  \"op\": \"notify\",\n"
      + "  \"topic\": \"orders.btcusdt\",\n"
      + "  \"ts\": 1522856623232,\n"
      + "  \"data\": {\n"
      + "    \"seq-id\": 94984,\n"
      + "    \"order-id\": 2039498445,\n"
      + "    \"symbol\": \"btcusdt\",\n"
      + "    \"account-id\": 123,\n"
      + "    \"order-amount\": \"5001.000000000000000000\",\n"
      + "    \"order-price\": \"1.662100000000000000\",\n"
      + "    \"created-at\": 1522858623622,\n"
      + "    \"order-type\": \"buy-limit\",\n"
      + "    \"order-source\": \"api\",\n"
      + "    \"order-state\": \"filled\",\n"
      + "    \"role\": \"taker|maker\",\n"
      + "    \"price\": \"1.662100000000000000\",\n"
      + "    \"filled-amount\": \"5000.000000000000000000\",\n"
      + "    \"unfilled-amount\": \"2.000000000000000000\",\n"
      + "    \"filled-cash-amount\": \"8301.357280000000000000\",\n"
      + "    \"filled-fees\": \"8.000000000000000000\"\n"
      + "  }\n"
      + "}";

  @Before
  public void initialize() {
    testAccount1.setType(AccountType.SPOT);
    testAccount1.setId(123L);
    testAccount2.setType(AccountType.MARGIN);
    testAccount2.setId(456L);
    List<Account> accountList = new LinkedList<>();
    accountList.add(testAccount1);
    accountList.add(testAccount2);
    testUser.setAccounts(accountList);
  }

  @Test
  public void testRequest() {
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<OrderUpdateEvent> request = requestImpl.subscribeOrderUpdateEvent(
        symbols, (orderUpdateEvent) -> {
        }, null);
    StubWebsocketConnection websocketConnection = new StubWebsocketConnection(request);
    assertNull(request.connectionHandler);
    request.authHandler.handle(websocketConnection);
    String subscription = websocketConnection.getWebsocket().popOutputMessage();
    assertTrue(subscription.contains("orders.btcusdt"));
  }

  @Test
  public void testReceive_Normal() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testUser);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 123L)).thenReturn(testAccount1);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 456L)).thenReturn(testAccount2);
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("12345");
    List<String> symbols = new LinkedList<>();
    symbols.add("btcusdt");
    WebsocketRequest<OrderUpdateEvent> request = requestImpl.subscribeOrderUpdateEvent(
        symbols, (orderUpdateEvent) -> {
        }, null);
    OrderUpdateEvent event = request.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals("btcusdt", event.getSymbol());
    assertEquals(new BigDecimal("1.6621"), event.getData().getPrice());
    assertEquals(new BigDecimal("5001"), event.getData().getAmount());
    assertEquals(new BigDecimal("5000"), event.getData().getFilledAmount());
    assertEquals("btcusdt", event.getData().getSymbol());
    assertEquals(1522856623232L, event.getTimestamp());
    assertEquals(AccountType.SPOT, event.getData().getAccountType());
    assertEquals(1522858623622L, event.getData().getCreatedTimestamp());
    assertEquals(2039498445L, event.getData().getOrderId());
    assertEquals(OrderType.BUY_LIMIT, event.getData().getType());
    assertEquals(OrderSource.API, event.getData().getSource());
    assertEquals(new BigDecimal("8301.35728"), event.getData().getFilledCashAmount());
    assertEquals(new BigDecimal("8"), event.getData().getFilledFees());
  }
}
