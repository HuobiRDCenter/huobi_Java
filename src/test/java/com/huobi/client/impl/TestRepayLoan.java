package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import java.math.BigDecimal;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestRepayLoan {

  private RestApiRequestImpl impl = null;
  private String data = "{\n"
      + "  \"status\": \"ok\",\n"
      + "  \"data\": 1000\n"
      + "}";

  private String dataError = "{\n"
      + "  \"status\": \"ok\"\n"
      + "}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
  }

  @Test
  public void testRequest_normal() {
    RestApiRequest<Long> restApiRequest = impl.repayLoan(12345L, new BigDecimal("1.1"));
    Request request = restApiRequest.request;
    assertTrue(request.url().toString().contains("/v1/margin/orders/12345/repay"));
    assertEquals("POST", request.method());
    MockPostQuerier querier = new MockPostQuerier(request);
    assertEquals(new BigDecimal("1.1"), querier.jsonWrapper.getBigDecimal("amount"));
  }

  @Test
  public void testResult_error() {
    RestApiRequest<Long> request = impl.repayLoan(12345L, new BigDecimal("1.1"));
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    request.jsonParser.parseJson(JsonWrapper.parseFromString(dataError));
  }
}
