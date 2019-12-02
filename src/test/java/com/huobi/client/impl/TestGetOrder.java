package com.huobi.client.impl;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Account;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AccountsInfoMap.class})
@PowerMockIgnore({"javax.crypto.*"})
public class TestGetOrder {

  private RestApiRequestImpl impl = null;
  Account testAccount = new Account();

  private static final String data = "{\n" +
      "    \"status\":\"ok\",\n" +
      "    \"data\":{\n" +
      "        \"id\":24962048654,\n" +
      "        \"symbol\":\"htbtc\",\n" +
      "        \"account-id\":123,\n" +
      "        \"amount\":\"1.000000000000000000\",\n" +
      "        \"price\":\"1.00000123000000000000\",\n" +
      "        \"created-at\":1550626936504,\n" +
      "        \"type\":\"sell-limit\",\n" +
      "        \"field-amount\":\"0.08888\",\n" +
      "        \"field-cash-amount\":\"0.204\",\n" +
      "        \"field-fees\":\"0.0345\",\n" +
      "        \"finished-at\":1550626936798,\n" +
      "        \"source\":\"api\",\n" +
      "        \"state\":\"canceled\",\n" +
      "        \"canceled-at\":1550626936722\n" +
      "    }\n" +
      "}";
  private static final String Errordata = "{\n" +
      "    \"status\":\"ok\",\n" +
      "    \"data\":{\n" +
      "        \"symbol\":\"htbtc\",\n" +
      "        \"account-id\":5628009,\n" +
      "        \"amount\":\"1.000000000000000000\",\n" +
      "        \"price\":\"1.000000000000000000\",\n" +
      "        \"created-at\":1550626936504,\n" +
      "        \"type\":\"sell-limit\",\n" +
      "        \"field-cash-amount\":\"0.0\",\n" +
      "        \"field-fees\":\"0.0\",\n" +
      "        \"finished-at\":1550626936798,\n" +
      "        \"source\":\"api\",\n" +
      "        \"state\":\"canceled\",\n" +
      "        \"canceled-at\":1550626936722\n" +
      "    }\n" +
      "}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
    testAccount.setType(AccountType.SPOT);
    testAccount.setId(123L);
  }

  @Test
  public void test() {
    RestApiRequest<Order> restApiRequest = impl.getOrder("htbtc", 12345L);
    String url = String.format("/v1/order/orders/%d", 12345L);
    assertTrue(restApiRequest.request.url().toString().contains(url));
    assertEquals("GET", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
  }

  @Test
  public void testResult_Normal() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 123L)).thenReturn(testAccount);
    RestApiRequest<Order> restApiRequest =
        impl.getOrder("htbtc", 24962048654L);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    Order order = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(1550626936504L, order.getCreatedTimestamp());
    assertEquals(AccountType.SPOT, order.getAccountType());
    assertEquals(1550626936722L, order.getCanceledTimestamp());
    assertEquals(1550626936798L, order.getFinishedTimestamp());
    assertEquals(24962048654L, order.getOrderId());
    assertEquals(new BigDecimal("0.08888"), order.getFilledAmount());
    assertEquals(new BigDecimal("0.204"), order.getFilledCashAmount());
    assertEquals(new BigDecimal("0.0345"), order.getFilledFees());
    assertEquals(new BigDecimal("1.00000123"), order.getPrice());
    assertEquals("htbtc", order.getSymbol());
    assertEquals(new BigDecimal("1"), order.getAmount());
    assertEquals(OrderSource.API, order.getSource());
    assertEquals(OrderState.CANCELED, order.getState());
    assertEquals(OrderType.SELL_LIMIT, order.getType());

  }

  @Test
  public void testResult_Unexpected() {

    RestApiRequest<Order> restApiRequest =
        impl.getOrder("htbtc", 24962048654L);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.getOrder("$$$$", 100L);

  }


}
