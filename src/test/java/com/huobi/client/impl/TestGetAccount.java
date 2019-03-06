package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Account;
import com.huobi.client.model.enums.AccountState;
import com.huobi.client.model.enums.AccountType;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TestGetAccount {

  private RestApiRequestImpl impl = null;

  private static final String data = "{\"status\":\"ok\",\"data\":[{\"id\":5628009,\"type\":\"spot\",\"subtype\":\"\",\"state\":\"working\"},{\"id\":5695557,\"type\":\"otc\",\"subtype\":\"\",\"state\":\"working\"}]}\n";
  private static final String Errordata = "{\"status\":\"ok\",\"data\":[{\"type\":\"spot\",\"subtype\":\"\",\"state\":\"working\"},{\"id\":5695557,\"type\":\"otc\",\"subtype\":\"\",\"state\":\"working\"}]}\n";
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
  }

  @Test
  public void test() {
    RestApiRequest<List<Account>> restApiRequest = impl.getAccounts();
    assertTrue(restApiRequest.request.url().toString().contains("/v1/account/accounts"));
    assertEquals("GET", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
  }


  @Test
  public void testResult_Normal() {
    RestApiRequest<List<Account>> restApiRequest =
        impl.getAccounts();
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    List<Account> accounts = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(5628009L, accounts.get(0).getId());
    assertEquals(AccountType.SPOT, accounts.get(0).getType());
    assertEquals(AccountState.WORKING, accounts.get(1).getState());

  }

  @Test
  public void testResult_Unexpected() {
    RestApiRequest<List<Account>> restApiRequest =
        impl.getAccounts();
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}
