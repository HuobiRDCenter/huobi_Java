package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Account;
import com.huobi.client.model.User;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.NewOrderRequest;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
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

public class TestCreateOrder {

  private RestApiRequestImpl impl = null;
  private NewOrderRequest CorrectnewOrderRequest;
  private NewOrderRequest WrongnewOrderRequest;

  private static final String data = "{\"status\":\"ok\",\"data\":\"24876987459\"}";
  private static final String Errordata = "{\"status\":\"ok\"}";

  User testAccount = new User();
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
    CorrectnewOrderRequest = new NewOrderRequest("htbtc",
        AccountType.SPOT,
        OrderType.SELL_LIMIT,
        BigDecimal.valueOf(1.0),
        BigDecimal.valueOf(1.0));
    WrongnewOrderRequest = new NewOrderRequest("!",
        AccountType.SPOT,
        OrderType.SELL_LIMIT,
        BigDecimal.valueOf(1.0),
        BigDecimal.valueOf(1.0));
    List<Account> accountList = new LinkedList<>();
    Account test = new Account();
    test.setType(AccountType.SPOT);
    test.setId(12345L);
    accountList.add(test);
    testAccount.setAccounts(accountList);
    //map.put("123",)
  }

  @Test
  public void test() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testAccount);
    RestApiRequest<Long> restApiRequest = impl.createOrder(CorrectnewOrderRequest);
    assertTrue(restApiRequest.request.url().toString().contains("/v1/order/orders/place"));
    assertEquals("POST", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
    MockPostQuerier querier = new MockPostQuerier(restApiRequest.request);
    assertEquals("htbtc", querier.jsonWrapper.getString("symbol"));
    assertEquals(12345L, querier.jsonWrapper.getLong("account-id"));
    assertEquals(new BigDecimal("1"), querier.jsonWrapper.getBigDecimal("amount"));
    assertEquals(new BigDecimal("1"), querier.jsonWrapper.getBigDecimal("price"));
    assertEquals(OrderType.SELL_LIMIT.toString(), querier.jsonWrapper.getString("type"));
    assertEquals("api", querier.jsonWrapper.getString("source"));
  }


  @Test
  public void testResult_Normal() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testAccount);
    RestApiRequest<Long> restApiRequest =
        impl.createOrder(CorrectnewOrderRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    long id = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(24876987459L, id);

  }

  @Test
  public void testResult_Unexpected() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testAccount);
    RestApiRequest<Long> restApiRequest =
        impl.createOrder(CorrectnewOrderRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.createOrder(WrongnewOrderRequest);
  }

  @Test
  public void testNullCurrency() {
    thrown.expect(HuobiApiException.class);
    NewOrderRequest req = new NewOrderRequest("btcusdt", null, null, null, null);
    impl.createOrder(req);
  }

  @Test
  public void test_limit() {
    thrown.expect(HuobiApiException.class);
    NewOrderRequest req = new NewOrderRequest("btcusdt", AccountType.SPOT, OrderType.SELL_LIMIT,
        null, null);
    impl.createOrder(req);
  }
}
