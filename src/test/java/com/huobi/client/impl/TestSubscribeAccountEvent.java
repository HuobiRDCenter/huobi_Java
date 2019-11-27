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

import com.huobi.client.SubscriptionOptions;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Account;
import com.huobi.client.model.User;
import com.huobi.client.model.enums.AccountChangeType;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceMode;
import com.huobi.client.model.enums.BalanceType;
import com.huobi.client.model.event.AccountEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AccountsInfoMap.class})
@PowerMockIgnore({"javax.crypto.*"})
public class TestSubscribeAccountEvent {

  Account testAccount1 = new Account();
  Account testAccount2 = new Account();
  User testUser = new User();

  String accountEvent = "{\n"
      + "\t\"op\": \"notify\",\n"
      + "\t\"ts\": 1550556381242,\n"
      + "\t\"topic\": \"accounts\",\n"
      + "\t\"data\": {\n"
      + "\t\t\"event\": \"order.place\",\n"
      + "\t\t\"list\": [{\n"
      + "\t\t\t\"account-id\": 123,\n"
      + "\t\t\t\"currency\": \"ht\",\n"
      + "\t\t\t\"type\": \"trade\",\n"
      + "\t\t\t\"balance\": \"10.8208984536412\"\n"
      + "\t\t}]\n"
      + "\t}\n"
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
    WebsocketRequest<AccountEvent> request = requestImpl.subscribeAccountEvent(
        BalanceMode.AVAILABLE, (AccountEvent) -> {
        }, null);
    SubscriptionOptions options = new SubscriptionOptions();
    StubWebsocketConnection websocketConnection = new StubWebsocketConnection("aaa", "bbb", request,
        options);
    assertNull(request.connectionHandler);
    request.authHandler.handle(websocketConnection);
    String subscription = websocketConnection.getWebsocket().popOutputMessage();
    assertTrue(subscription.contains("accounts"));
  }

  @Test
  public void testResult() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testUser);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 123L)).thenReturn(testAccount1);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 456L)).thenReturn(testAccount2);
    WebsocketRequestImpl requestImpl = new WebsocketRequestImpl("12345");
    WebsocketRequest<AccountEvent> request = requestImpl.subscribeAccountEvent(
        BalanceMode.AVAILABLE, (AccountEvent) -> {
        }, null);
    AccountEvent event = request.jsonParser.parseJson(JsonWrapper.parseFromString(accountEvent));
    assertEquals(1550556381242L, event.getTimestamp());
    assertEquals(1, event.getData().size());
    assertEquals(AccountChangeType.NEWORDER, event.getChangeType());
    assertEquals(AccountType.SPOT, event.getData().get(0).getAccountType());
    assertEquals("ht", event.getData().get(0).getCurrency());
    assertEquals(new BigDecimal("10.8208984536412"), event.getData().get(0).getBalance());
    assertEquals(BalanceType.TRADE, event.getData().get(0).getBalanceType());
  }
}
