package com.huobi.client.impl;

import okhttp3.Request;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGetExchangeTimestamp {

  private RestApiRequestImpl impl = null;
  private String data = "{\n"
      + "  \"status\": \"ok\",\n"
      + "  \"data\": 1494900087029\n"
      + "}";

  private String dataError = "{\n"
      + "  \"status\": \"ok\"\n"
      + "}";

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("", "", new RequestOptions());
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test() {
    RestApiRequest<Long> restApiRequest = impl.getExchangeTimestamp();
    Request request = restApiRequest.request;
    assertEquals("GET", request.method());
    assertTrue(request.url().toString().contains("/v1/common/timestamp"));
    Long result = restApiRequest.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals(1494900087029L, result.longValue());
  }

  @Test
  public void test_error() {
    RestApiRequest<Long> restApiRequest = impl.getExchangeTimestamp();
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(JsonWrapper.parseFromString(dataError));
  }
}
