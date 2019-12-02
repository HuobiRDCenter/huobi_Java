package com.huobi.client.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Account;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.HistoricalOrdersRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AccountsInfoMap.class})
@PowerMockIgnore({"javax.crypto.*"})
public class TestGetHistoryOrders {

  private RestApiRequestImpl impl = null;
  Account testAccount = new Account();

  private static final String data = "{\n" +
      "    \"status\":\"ok\",\n" +
      "    \"data\":[\n" +
      "        {\n" +
      "            \"id\":24965104183,\n" +
      "            \"symbol\":\"htbtc\",\n" +
      "            \"account-id\":123,\n" +
      "            \"amount\":\"1.000000000000000000\",\n" +
      "            \"price\":\"1.000000000000000001\",\n" +
      "            \"created-at\":1550630155350,\n" +
      "            \"type\":\"sell-limit\",\n" +
      "            \"field-amount\":\"0.0888\",\n" +
      "            \"field-cash-amount\":\"0.011\",\n" +
      "            \"field-fees\":\"0.03445\",\n" +
      "            \"finished-at\":1550630155647,\n" +
      "            \"source\":\"api\",\n" +
      "            \"state\":\"canceled\",\n" +
      "            \"canceled-at\":1550630155568\n" +
      "        },\n" +
      "        {\n" +
      "            \"id\":24965089728,\n" +
      "            \"symbol\":\"htbtc\",\n" +
      "            \"account-id\":123,\n" +
      "            \"amount\":\"1.000000000000000000\",\n" +
      "            \"price\":\"1.000000000000000000\",\n" +
      "            \"created-at\":1550630140288,\n" +
      "            \"type\":\"sell-limit\",\n" +
      "            \"field-amount\":\"0.0\",\n" +
      "            \"field-cash-amount\":\"0.0\",\n" +
      "            \"field-fees\":\"0.0\",\n" +
      "            \"source\":\"api\",\n" +
      "            \"state\":\"canceled\"\n" +
      "                }\n" +
      "    ]\n" +
      "}\n" +
      "\n";
  private static final String Errordata = "{\n" +
      "    \"status\":\"ok\",\n" +
      "    \"data\":[\n" +
      "        {\n" +
      "            \"id\":24965104183,\n" +
      "            \"symbol\":\"htbtc\",\n" +
      "            \"account-id\":123,\n" +
      "            \"price\":\"1.000000000000000001\",\n" +
      "            \"created-at\":1550630155350,\n" +
      "            \"type\":\"sell-limit\",\n" +
      "            \"field-amount\":\"0.0888\",\n" +
      "            \"field-cash-amount\":\"0.011\",\n" +
      "            \"field-fees\":\"0.03445\",\n" +
      "            \"finished-at\":1550630155647,\n" +
      "            \"source\":\"api\",\n" +
      "            \"state\":\"canceled\",\n" +
      "            \"canceled-at\":1550630155568\n" +
      "        },\n" +
      "        {\n" +
      "            \"id\":24965089728,\n" +
      "            \"symbol\":\"htbtc\",\n" +
      "            \"account-id\":123,\n" +
      "            \"amount\":\"1.000000000000000000\",\n" +
      "            \"price\":\"1.000000000000000000\",\n" +
      "            \"created-at\":1550630140288,\n" +
      "            \"type\":\"sell-limit\",\n" +
      "            \"field-amount\":\"0.0\",\n" +
      "            \"field-cash-amount\":\"0.0\",\n" +
      "            \"field-fees\":\"0.0\",\n" +
      "            \"finished-at\":1550630140579,\n" +
      "            \"source\":\"api\",\n" +
      "            \"state\":\"canceled\",\n" +
      "            \"canceled-at\":1550630140512\n" +
      "                }\n" +
      "    ]\n" +
      "}\n" +
      "\n";
  private HistoricalOrdersRequest historicalOrdersRequest;
  private HistoricalOrdersRequest historicalOrdersRequest1;
  private Date Startdate = new Date();
  private Date Enddate = new Date();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
    historicalOrdersRequest = new HistoricalOrdersRequest("htbtc", OrderState.CANCELED, null, null,
        null, null, null);
    testAccount.setType(AccountType.SPOT);
    testAccount.setId(123L);
    historicalOrdersRequest1 = new HistoricalOrdersRequest("htbtc", OrderState.SUBMITTED,
        OrderType.SELL_LIMIT, Startdate, Enddate, null, 155);
  }

  @Test
  public void test() {
    RestApiRequest<List<Order>> restApiRequest = impl.getHistoricalOrders(historicalOrdersRequest);

    assertTrue(restApiRequest.request.url().toString().contains("/v1/order/orders"));
    assertEquals("GET", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
    assertEquals("htbtc", restApiRequest.request.url().queryParameter("symbol"));
    assertEquals(OrderState.CANCELED.toString(),
        restApiRequest.request.url().queryParameter("states"));
    assertNull(restApiRequest.request.url().queryParameter("start-date"));
    assertNull(restApiRequest.request.url().queryParameter("end-date"));
    assertNull(restApiRequest.request.url().queryParameter("types"));
    assertNull(restApiRequest.request.url().queryParameter("from"));
    assertNull(restApiRequest.request.url().queryParameter("size"));

  }

  @Test
  public void testAnotherRequest() {
    RestApiRequest<List<Order>> restApiRequest = impl.getHistoricalOrders(historicalOrdersRequest1);

    assertTrue(restApiRequest.request.url().toString().contains("/v1/order/orders"));
    assertEquals("GET", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
    assertEquals("htbtc", restApiRequest.request.url().queryParameter("symbol"));
    assertEquals(OrderState.SUBMITTED.toString(),
        restApiRequest.request.url().queryParameter("states"));
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    assertEquals(dateFormatter.format(Startdate),
        restApiRequest.request.url().queryParameter("start-date"));
    assertEquals(dateFormatter.format(Enddate),
        restApiRequest.request.url().queryParameter("end-date"));

    assertEquals(OrderType.SELL_LIMIT.toString(),
        restApiRequest.request.url().queryParameter("types"));
    assertNull(restApiRequest.request.url().queryParameter("from"));
    assertEquals("155", restApiRequest.request.url().queryParameter("size"));

  }

  @Test
  public void testResult_Normal() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 123L)).thenReturn(testAccount);
    RestApiRequest<List<Order>> restApiRequest =
        impl.getHistoricalOrders(historicalOrdersRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    List<Order> orders = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(24965104183L, orders.get(0).getOrderId());
    assertEquals(AccountType.SPOT, orders.get(0).getAccountType());
    assertEquals(1550630155568L, orders.get(0).getCanceledTimestamp());
    assertEquals(1550630155647L, orders.get(0).getFinishedTimestamp());
    assertEquals(1550630155350L, orders.get(0).getCreatedTimestamp());
    assertEquals(new BigDecimal("0.0888"), orders.get(0).getFilledAmount());
    assertEquals(new BigDecimal("0.011"), orders.get(0).getFilledCashAmount());
    assertEquals(new BigDecimal("0.03445"), orders.get(0).getFilledFees());
    assertEquals(new BigDecimal("1.000000000000000001"), orders.get(0).getPrice());
    assertEquals("htbtc", orders.get(0).getSymbol());
    assertEquals(new BigDecimal("1"), orders.get(0).getAmount());
    assertEquals(OrderSource.API, orders.get(0).getSource());
    assertEquals(OrderState.CANCELED, orders.get(0).getState());
    assertEquals(OrderType.SELL_LIMIT, orders.get(0).getType());
    assertEquals(0, orders.get(1).getFinishedTimestamp());
    assertEquals(0, orders.get(1).getCanceledTimestamp());

  }

  @Test
  public void testResult_Unexpected() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getAccount("12345", 123L)).thenReturn(testAccount);
    RestApiRequest<List<Order>> restApiRequest =
        impl.getHistoricalOrders(historicalOrdersRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }

  @Test
  public void testInvalidSymbol() {
    HistoricalOrdersRequest WronghistoricalOrdersRequest = new HistoricalOrdersRequest("?",
        OrderState.CANCELED);
    thrown.expect(HuobiApiException.class);
    impl.getHistoricalOrders(WronghistoricalOrdersRequest);

  }


}
