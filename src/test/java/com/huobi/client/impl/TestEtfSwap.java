package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.model.enums.EtfSwapType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestEtfSwap {
  private String data = "{\n"
      + "    \"code\": 200,\n"
      + "    \"data\": null,\n"
      + "    \"message\": null,\n"
      + "    \"success\": true\n"
      + "}";

  private RestApiRequestImpl impl = new RestApiRequestImpl("123", "456", new RequestOptions());

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testRequest_In() {
    RestApiRequest<Void> restApiRequest = impl.etfSwap("hb10", 123, EtfSwapType.ETF_SWAP_IN);
    assertEquals("POST", restApiRequest.request.method());
    assertTrue(restApiRequest.request.url().toString().contains("/etf/swap/in"));
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
    MockPostQuerier querier = new MockPostQuerier(restApiRequest.request);
    assertEquals("hb10", querier.jsonWrapper.getString("etf_name"));
    assertEquals(123, querier.jsonWrapper.getInteger("amount"));
  }

  @Test
  public void testRequest_Out() {
    RestApiRequest<Void> restApiRequest = impl.etfSwap("hb10", 345, EtfSwapType.ETF_SWAP_OUT);
    assertEquals("POST", restApiRequest.request.method());
    assertTrue(restApiRequest.request.url().toString().contains("/etf/swap/out"));
    MockPostQuerier querier = new MockPostQuerier(restApiRequest.request);
    assertEquals("hb10", querier.jsonWrapper.getString("etf_name"));
    assertEquals(345, querier.jsonWrapper.getInteger("amount"));
  }
}
