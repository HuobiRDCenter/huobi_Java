package com.huobi.client.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.Account;
import com.huobi.client.model.Order;
import com.huobi.client.model.User;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderSide;
import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.OpenOrderRequest;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AccountsInfoMap.class})
@PowerMockIgnore({"javax.crypto.*"})
public class TestGetOpenOrders {

  private RestApiRequestImpl impl = null;
  Account testAccount1 = new Account();
  Account testAccount2 = new Account();
  User testUser = new User();
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private String data = "{\n"
      + "\t\"status\": \"ok\",\n"
      + "\t\"data\": [{\n"
      + "\t\t\t\"created-at\": 1550628731111,\n"
      + "\t\t\t\"symbol\": \"htbtc\",\n"
      + "\t\t\t\"source\": \"api\",\n"
      + "\t\t\t\"amount\": \"1.000000000000000000\",\n"
      + "\t\t\t\"account-id\": 123,\n"
      + "\t\t\t\"filled-amount\": \"0.1\",\n"
      + "\t\t\t\"filled-cash-amount\": \"0.2\",\n"
      + "\t\t\t\"filled-fees\": \"0.3\",\n"
      + "\t\t\t\"price\": \"1.100000000000000000\",\n"
      + "\t\t\t\"id\": 24963751000,\n"
      + "\t\t\t\"state\": \"submitted\",\n"
      + "\t\t\t\"type\": \"sell-limit\"\n"
      + "\t\t},\n"
      + "\t\t{\n"
      + "\t\t\t\"created-at\": 1550628730000,\n"
      + "\t\t\t\"symbol\": \"htbtc\",\n"
      + "\t\t\t\"source\": \"api\",\n"
      + "\t\t\t\"amount\": \"2.000000000000000000\",\n"
      + "\t\t\t\"account-id\": 456,\n"
      + "\t\t\t\"filled-amount\": \"2.0\",\n"
      + "\t\t\t\"filled-cash-amount\": \"2.1\",\n"
      + "\t\t\t\"filled-fees\": \"2.2\",\n"
      + "\t\t\t\"price\": \"2.100000000000000000\",\n"
      + "\t\t\t\"id\": 24963751111,\n"
      + "\t\t\t\"state\": \"submitted\",\n"
      + "\t\t\t\"type\": \"buy-limit\"\n"
      + "\t\t}\n"
      + "\t]\n"
      + "}";
  private String Errordata = "{\n"
      + "\t\"status\": \"ok\",\n"
      + "\t\"data\": [{\n"
      + "\t\t\t\"symbol\": \"htbtc\",\n"
      + "\t\t\t\"source\": \"api\",\n"
      + "\t\t\t\"amount\": \"1.000000000000000000\",\n"
      + "\t\t\t\"account-id\": 123,\n"
      + "\t\t\t\"filled-amount\": \"0.1\",\n"
      + "\t\t\t\"filled-cash-amount\": \"0.2\",\n"
      + "\t\t\t\"filled-fees\": \"0.3\",\n"
      + "\t\t\t\"price\": \"1.100000000000000000\",\n"
      + "\t\t\t\"id\": 24963751000,\n"
      + "\t\t\t\"state\": \"submitted\",\n"
      + "\t\t\t\"type\": \"sell-limit\"\n"
      + "\t\t},\n"
      + "\t\t{\n"
      + "\t\t\t\"created-at\": 1550628730000,\n"
      + "\t\t\t\"symbol\": \"htbtc\",\n"
      + "\t\t\t\"source\": \"api\",\n"
      + "\t\t\t\"amount\": \"2.000000000000000000\",\n"
      + "\t\t\t\"account-id\": 456,\n"
      + "\t\t\t\"filled-amount\": \"2.0\",\n"
      + "\t\t\t\"filled-cash-amount\": \"2.1\",\n"
      + "\t\t\t\"filled-fees\": \"2.2\",\n"
      + "\t\t\t\"price\": \"2.100000000000000000\",\n"
      + "\t\t\t\"id\": 24963751111,\n"
      + "\t\t\t\"state\": \"submitted\",\n"
      + "\t\t\t\"type\": \"buy-limit\"\n"
      + "\t\t}\n"
      + "\t]\n"
      + "}";

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
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
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testUser);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 123L)).thenReturn(testAccount1);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 456L)).thenReturn(testAccount2);
    OpenOrderRequest openOrderRequest = new OpenOrderRequest("btcusdt", AccountType.SPOT,
        OrderSide.BUY, 10);
    RestApiRequest<List<Order>> restApiRequest = impl.getOpenOrders(openOrderRequest);
    Request request = restApiRequest.request;
    assertEquals("GET", request.method());
    assertTrue(request.url().toString().contains("/v1/order/openOrders"));
    assertEquals("btcusdt", request.url().queryParameter("symbol"));
    assertEquals("10", request.url().queryParameter("size"));
    assertEquals("buy", request.url().queryParameter("side"));
  }

  @Test
  public void testRequest_defaultParameter() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testUser);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 123L)).thenReturn(testAccount1);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 456L)).thenReturn(testAccount2);
    OpenOrderRequest openOrderRequest = new OpenOrderRequest("btcusdt", AccountType.SPOT);
    RestApiRequest<List<Order>> restApiRequest = impl.getOpenOrders(openOrderRequest);
    Request request = restApiRequest.request;
    assertEquals("GET", request.method());
    assertTrue(request.url().toString().contains("/v1/order/openOrders"));
    assertEquals("btcusdt", request.url().queryParameter("symbol"));
    assertEquals("123", request.url().queryParameter("account-id"));
    assertNull(request.url().queryParameter("size"));
    assertNull(request.url().queryParameter("side"));
  }

  @Test
  public void testRequest_invalidSymbol() {
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("invalid symbol");
    OpenOrderRequest openOrderRequest = new OpenOrderRequest("$$$$", AccountType.SPOT);
    impl.getOpenOrders(openOrderRequest);
  }

  @Test
  public void testResult() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testUser);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 123L)).thenReturn(testAccount1);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 456L)).thenReturn(testAccount2);
    OpenOrderRequest openOrderRequest = new OpenOrderRequest("btcusdt", AccountType.SPOT);
    RestApiRequest<List<Order>> restApiRequest = impl.getOpenOrders(openOrderRequest);
    List<Order> orderList = restApiRequest.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals(2, orderList.size());
    assertEquals("htbtc", orderList.get(0).getSymbol());
    assertEquals(OrderSource.API, orderList.get(0).getSource());
    assertEquals(new BigDecimal("1"), orderList.get(0).getAmount());
    assertEquals(new BigDecimal("1.1"), orderList.get(0).getPrice());
    assertEquals(new BigDecimal("0.1"), orderList.get(0).getFilledAmount());
    assertEquals(new BigDecimal("0.2"), orderList.get(0).getFilledCashAmount());
    assertEquals(new BigDecimal("0.3"), orderList.get(0).getFilledFees());
    assertEquals(24963751000L, orderList.get(0).getOrderId());
    assertEquals(AccountType.SPOT, orderList.get(0).getAccountType());
    assertEquals(OrderState.SUBMITTED, orderList.get(0).getState());
    assertEquals(OrderType.SELL_LIMIT, orderList.get(0).getType());
    assertEquals(TimeService.convertCSTInMillisecondToUTC(1550628731111L),
        orderList.get(0).getCreatedTimestamp());

    assertEquals("htbtc", orderList.get(1).getSymbol());
    assertEquals(OrderSource.API, orderList.get(1).getSource());
    assertEquals(new BigDecimal("2"), orderList.get(1).getAmount());
    assertEquals(new BigDecimal("2.1"), orderList.get(1).getPrice());
    assertEquals(new BigDecimal("2"), orderList.get(1).getFilledAmount());
    assertEquals(new BigDecimal("2.1"), orderList.get(1).getFilledCashAmount());
    assertEquals(new BigDecimal("2.2"), orderList.get(1).getFilledFees());
    assertEquals(24963751111L, orderList.get(1).getOrderId());
    assertEquals(AccountType.MARGIN, orderList.get(1).getAccountType());
    assertEquals(OrderState.SUBMITTED, orderList.get(1).getState());
    assertEquals(OrderType.BUY_LIMIT, orderList.get(1).getType());
    assertEquals(TimeService.convertCSTInMillisecondToUTC(1550628730000L),
        orderList.get(1).getCreatedTimestamp());
  }

  @Test
  public void testResult_Unexpected() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testUser);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 123L)).thenReturn(testAccount1);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 456L)).thenReturn(testAccount2);
    OpenOrderRequest openOrderRequest = new OpenOrderRequest("btcusdt", AccountType.SPOT);
    RestApiRequest<List<Order>> restApiRequest =
        impl.getOpenOrders(openOrderRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}
