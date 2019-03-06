package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Account;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.User;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.request.CancelOpenOrderRequest;
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

public class TestCancleOpenOrders {

  private RestApiRequestImpl impl = null;
  private CancelOpenOrderRequest cancelOpenOrderRequest = new CancelOpenOrderRequest("htbtc",
      AccountType.SPOT);
  private CancelOpenOrderRequest WrongcancelOpenOrderRequest = new CancelOpenOrderRequest("?",
      AccountType.SPOT);
  User testAccount = new User();
  private static final String data = "{\"status\":\"ok\",\"data\":{\"success-count\":5,\"failed-count\":0,\"next-id\":-1}}";
  private static final String Errordata = "{\"status\":\"ok\",\"data\":{\"failed-count\":0,\"next-id\":-1}}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
    List<Account> accountList = new LinkedList<>();
    Account test = new Account();
    test.setType(AccountType.SPOT);
    test.setId(12345L);
    accountList.add(test);
    testAccount.setAccounts(accountList);
  }

  @Test
  public void test() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testAccount);
    RestApiRequest<BatchCancelResult> restApiRequest = impl
        .cancelOpenOrders(cancelOpenOrderRequest);
    assertTrue(
        restApiRequest.request.url().toString().contains("/v1/order/orders/batchCancelOpenOrders"));
    assertEquals("POST", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
    MockPostQuerier querier = new MockPostQuerier(restApiRequest.request);
    assertEquals("htbtc", querier.jsonWrapper.getString("symbol"));
    assertEquals(12345L, querier.jsonWrapper.getLong("account-id"));

  }


  @Test
  public void testResult_Normal() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testAccount);
    RestApiRequest<BatchCancelResult> restApiRequest =
        impl.cancelOpenOrders(cancelOpenOrderRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    BatchCancelResult batchCancelResult = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(0, batchCancelResult.getFailedCount());
    assertEquals(5, batchCancelResult.getSuccessCount());
  }

  @Test
  public void testResult_Unexpected() {
    PowerMockito.mockStatic(AccountsInfoMap.class);
    PowerMockito.when(AccountsInfoMap.getUser("12345")).thenReturn(testAccount);
    RestApiRequest<BatchCancelResult> restApiRequest =
        impl.cancelOpenOrders(cancelOpenOrderRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.cancelOpenOrders(WrongcancelOpenOrderRequest);
  }

  @Test
  public void testNullAccountType() {
    thrown.expect(HuobiApiException.class);
    CancelOpenOrderRequest req = new CancelOpenOrderRequest("btcusdt", null);
    impl.cancelOpenOrders(req);
  }
}
