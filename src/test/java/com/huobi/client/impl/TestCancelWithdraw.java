package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TestCancelWithdraw {

  private RestApiRequestImpl impl = null;

  private static final String data = "{\n" +
      "  \"status\": \"ok\",\n" +
      "  \"data\": 700\n" +
      "}";
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
  }

  @Test
  public void test() {
    RestApiRequest<Void> restApiRequest = impl.cancelWithdraw("htbtc", 12345L);
    String url = String.format("/v1/dw/withdraw-virtual/%d/cancel", 12345L);
    assertTrue(restApiRequest.request.url().toString().contains(url));
    assertEquals("POST", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
  }

  @Test
  public void testResult_Normal() {

    RestApiRequest<Void> restApiRequest =
        impl.cancelWithdraw("htbtc", 12345L);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    Void aVoid = restApiRequest.jsonParser.parseJson(jsonWrapper);

    assertEquals(null, aVoid);

  }


  @Test
  public void testInvalidSymbol() {

    thrown.expect(HuobiApiException.class);
    impl.cancelWithdraw("??", 1234L);

  }


}
