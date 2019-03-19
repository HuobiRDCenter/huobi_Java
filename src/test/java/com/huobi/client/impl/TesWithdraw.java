package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.request.WithdrawRequest;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TesWithdraw {

  private RestApiRequestImpl impl = null;
  private static final String data = "{\n" +
      "  \"status\": \"ok\",\n" +
      "  \"data\": 700\n" +
      "}";
  private static final String Errordata = "{\n" +
      "  \"status\": \"ok\",\n" +
      "}";


  private WithdrawRequest withdrawRequest;
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
    withdrawRequest = new WithdrawRequest("0xde709f2102306220921060314715629080e2fb77",
        new BigDecimal("0.05"), "eth", new BigDecimal("1.00"), "aaa");
  }

  @Test
  public void test() {
    RestApiRequest<Long> restApiRequest = impl.withdraw(withdrawRequest);
    assertTrue(restApiRequest.request.url().toString().contains("/v1/dw/withdraw/api/create"));
    assertEquals("POST", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
    MockPostQuerier querier = new MockPostQuerier(restApiRequest.request);
    assertEquals("0xde709f2102306220921060314715629080e2fb77",
        querier.jsonWrapper.getString("address"));
    assertEquals("eth", querier.jsonWrapper.getString("currency"));
    assertEquals("0.05", querier.jsonWrapper.getString("amount"));
    assertEquals("1.00", querier.jsonWrapper.getString("fee"));
    assertEquals("aaa", querier.jsonWrapper.getString("addr-tag"));
  }

  @Test
  public void testResult_Normal() {
    RestApiRequest<Long> restApiRequest =
        impl.withdraw(withdrawRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    long id = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(700L, id);
  }

  @Test
  public void testResult_Unexpected() {

    RestApiRequest<Long> restApiRequest =
        impl.withdraw(withdrawRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }

  @Test
  public void testInput() {
    WithdrawRequest withdrawRequest1 = new WithdrawRequest(null, new BigDecimal("0.05"), "btc");
    WithdrawRequest withdrawRequest2 = new WithdrawRequest("123", null, "btc");
    WithdrawRequest withdrawRequest3 = new WithdrawRequest("123", new BigDecimal("0.05"), null);

    thrown.expect(HuobiApiException.class);
    impl.withdraw(withdrawRequest1);
    impl.withdraw(withdrawRequest2);
    impl.withdraw(withdrawRequest3);
  }

  @Test
  public void testInvalidSymbol() {
    WithdrawRequest withdrawRequest = new WithdrawRequest(
        "0xde709f2102306220921060314715629080e2fb77", new BigDecimal("0.05"), "??");

    thrown.expect(HuobiApiException.class);
    impl.withdraw(withdrawRequest);

  }


}
