package com.huobi.client.impl;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestErrorResponse {

  private static final String errorData = "{\n"
      + "  \"ts\": 1550209202720,\n"
      + "  \"status\": \"error\",\n"
      + "  \"err-code\": \"invalid-parameter\",\n"
      + "  \"err-msg\": \"invalid symbol\"\n"
      + "}";

  private static final String errorData_unexpected = "{\n"
      + "  \"ts\": 1550209202720,\n"
      + "  \"status\": \"abc\",\n"
      + "  \"err-code\": \"invalid-parameter\",\n"
      + "  \"err-msg\": \"invalid symbol\"\n"
      + "}";

  private static final String errorData_noStatus = "{\n"
      + "  \"ts\": 1550209202720,\n"
      + "  \"err-code\": \"invalid-parameter\",\n"
      + "  \"err-msg\": \"invalid symbol\"\n"
      + "}";

  private static final String error_etf = "{\n"
      + "  \"code\": 504,\n"
      + "  \"data\": null,\n"
      + "  \"message\": \"缺少参数:etf_name\",\n"
      + "  \"success\": false\n"
      + "}";

  private RestApiRequestImpl impl = null;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("", "", new RequestOptions());
  }

  @Test
  public void testErrorResponse() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(errorData);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("invalid symbol");
    RestApiInvoker.checkResponse(jsonWrapper);
  }

  @Test
  public void testErrorResponse_unexpectedResponse() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(errorData_unexpected);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Response is not expected");
    RestApiInvoker.checkResponse(jsonWrapper);
  }

  @Test
  public void testErrorResponse_noStatusResponse() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(errorData_noStatus);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Status cannot be found in response");
    RestApiInvoker.checkResponse(jsonWrapper);
  }

  @Test
  public void testErrorResponse_etf() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(error_etf);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("etf_name");
    RestApiInvoker.checkResponse(jsonWrapper);
  }
}
