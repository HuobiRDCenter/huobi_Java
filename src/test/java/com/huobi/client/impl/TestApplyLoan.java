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

public class TestApplyLoan {

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
  public void testRequest() {
    RestApiRequest<Long> restApiRequest = impl.applyLoan("btcusdt", "btc", new BigDecimal("1.1"));
    Request request = restApiRequest.request;
    assertTrue(request.url().toString().contains("/v1/margin/orders"));
    assertEquals("POST", request.method());
    MockPostQuerier querier = new MockPostQuerier(request);
    assertEquals("btcusdt", querier.jsonWrapper.getString("symbol"));
    assertEquals("btc", querier.jsonWrapper.getString("currency"));
    assertEquals(new BigDecimal("1.1"), querier.jsonWrapper.getBigDecimal("amount"));
  }

  @Test
  public void testResult_normal() {
    RestApiRequest<Long> restApiRequest = impl.applyLoan("btcusdt", "btc", new BigDecimal("1.1"));
    Long result = restApiRequest.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals(1000L, result.longValue());
  }

  @Test
  public void testResult_error() {
    RestApiRequest<Long> restApiRequest = impl.applyLoan("btcusdt", "btc", new BigDecimal("1.1"));
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(JsonWrapper.parseFromString(dataError));
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.applyLoan("$$$$", "btc", new BigDecimal("1.1"));
  }

  @Test
  public void testInvalidCurrency() {
    thrown.expect(HuobiApiException.class);
    impl.applyLoan("btcusdt", "$$$$", new BigDecimal("1.1"));
  }

}
