package com.huobi.client.impl;

import java.math.BigDecimal;

import okhttp3.Request;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.BestQuote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestGetBestQuote {

  private RestApiRequestImpl impl = null;
  private static final String data = "{\n" +
      "  \"status\": \"ok\",\n" +
      "  \"ch\": \"market.ethusdt.detail.merged\",\n" +
      "  \"ts\": 1550223581490,\n" +
      "  \"tick\": {\n" +
      "    \"amount\": 222930.8868295491,\n" +
      "    \"open\": 122.4,\n" +
      "    \"close\": 122.24,\n" +
      "    \"high\": 123.42,\n" +
      "    \"id\": 100417063447,\n" +
      "    \"count\": 68987,\n" +
      "    \"low\": 120.25,\n" +
      "    \"version\": 100417063447,\n" +
      "    \"ask\": [\n" +
      "      122.26,\n" +
      "      0.8271\n" +
      "    ],\n" +
      "    \"vol\": 27123490.874530632,\n" +
      "    \"bid\": [\n" +
      "      122.24,\n" +
      "      2.6672\n" +
      "    ]\n" +
      "  }\n" +
      "}";
  private static final String Errordata = "{\n" +
      "  \"status\": \"ok\",\n" +
      "  \"ch\": \"market.ethusdt.detail.merged\",\n" +
      "  \"ts\": 1550223581490,\n" +
      "  \"tick\": {\n" +
      "    \"amount\": 222930.8868295491,\n" +
      "    \"open\": 122.4,\n" +
      "    \"close\": 122.24,\n" +
      "    \"high\": 123.42,\n" +
      "    \"id\": 100417063447,\n" +
      "    \"count\": 68987,\n" +
      "    \"low\": 120.25,\n" +
      "    \"version\": 100417063447,\n" +
      "    \"vol\": 27123490.874530632,\n" +
      "    \"bid\": [\n" +
      "      122.24,\n" +
      "      2.6672\n" +
      "    ]\n" +
      "  }\n" +
      "}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("", "", new RequestOptions());
  }

  @Test
  public void testNormalCase() {
    RestApiRequest<BestQuote> restApiRequest = impl.getBestQuote("btcusdt");
    Request request = restApiRequest.request;
    assertEquals(request.method(), "GET");
    assertTrue(request.url().toString().contains("/market/detail/merged"));
    assertEquals("btcusdt", request.url().queryParameter("symbol"));
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.getBestQuote("$$$$");
  }


  @Test
  public void testResult_Normal() {
    RestApiRequest<BestQuote> restApiRequest =
        impl.getBestQuote("btcusdt");
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    BestQuote bestQuote = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(1550223581490L, bestQuote.getTimestamp());
    assertEquals(new BigDecimal("122.26"), bestQuote.getAskPrice());
    assertEquals(new BigDecimal("0.8271"), bestQuote.getAskAmount());
    assertEquals(new BigDecimal("122.24"), bestQuote.getBidPrice());
    assertEquals(new BigDecimal("2.6672"), bestQuote.getBidAmount());

  }

  @Test
  public void testResult_Unexpected() {
    RestApiRequest<BestQuote> restApiRequest =
        impl.getBestQuote("btcusdt");
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}
