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
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.User;

import static org.junit.Assert.assertEquals;


public class TestGetPriceDepth {

  private RestApiRequestImpl impl = null;
  private User user = new User();


  private static final String data = "{\n" +
      "  \"status\": \"ok\",\n" +
      "  \"ch\": \"market.ethusdt.depth.step0\",\n" +
      "  \"ts\": 1550218546616,\n" +
      "  \"tick\": {\n" +
      "    \"bids\": [\n" +
      "      [\n" +
      "        122.920000000000000000,\n" +
      "        2.746800000000000000\n" +
      "      ],\n" +
      "\t  [\n" +
      "        120.300000000000000000,\n" +
      "        494.745900000000000000\n" +
      "      ]\n" +
      "    ],\n" +
      "    \"asks\": [\n" +
      "      [\n" +
      "        122.940000000000000000,\n" +
      "        67.554900000000000000\n" +
      "      ],\n" +
      "\t  [\n" +
      "        124.620000000000000000,\n" +
      "        50.000000000000000000\n" +
      "      ]\n" +
      "    ],\n" +
      "    \"ts\": 1550218546020,\n" +
      "    \"version\": 100416549839\n" +
      "  }\n" +
      "}\n";

  private static final String Errordata = "{\n" +
      "  \"status\": \"error\",\n" +
      "  \"ch\": \"market.ethusdt.depth.step0\",\n" +
      "  \"ts\": 1550218546616,\n" +
      "  \"tick\": {\n" +
      "    \"bids\": [\n" +
      "      [\n" +
      "        122.920000000000000000,\n" +
      "        2.746800000000000000\n" +
      "      ],\n" +
      "      [\n" +
      "        123.260000000000000000,\n" +
      "        28.135400000000000000\n" +
      "      ]\n" +
      "],\n" +
      "    \"ts\": 1550218546020,\n" +
      "    \"version\": 100416549839\n" +
      "  }\n" +
      "}\n";
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("", "", new RequestOptions());
  }

  @Test
  public void testNormalCase() {
    RestApiRequest<PriceDepth> restApiRequest =
        impl.getPriceDepth("btcusdt", null);
    Request request = restApiRequest.request;
    assertEquals("GET", request.method());
    assertEquals("btcusdt", request.url().queryParameter("symbol"));
    assertEquals("step0", request.url().queryParameter("type"));

  }


  @Test
  public void testUnexpectedSize_lessMin() {
    thrown.expect(HuobiApiException.class);
    impl.getPriceDepth("btcusdt", -1);
  }

  @Test
  public void testUnexpectedSize_greaterMax() {
    thrown.expect(HuobiApiException.class);
    impl.getPriceDepth("btcusdt", 2001);
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.getPriceDepth("$$$$", 100);
  }

  @Test
  public void testResult_Normal() {
    RestApiRequest<PriceDepth> restApiRequest =
        impl.getPriceDepth("btcusdt", 1);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    PriceDepth priceDepth = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(1, priceDepth.getBids().size());
    assertEquals(1550218546020L, priceDepth.getTimestamp());
    assertEquals(new BigDecimal("122.92"), priceDepth.getBids().get(0).getPrice());
    assertEquals(new BigDecimal("2.7468"), priceDepth.getBids().get(0).getAmount());
    assertEquals(new BigDecimal("122.94"), priceDepth.getAsks().get(0).getPrice());
    assertEquals(new BigDecimal("67.5549"), priceDepth.getAsks().get(0).getAmount());

  }

  @Test
  public void testResult_Unexpected() {
    RestApiRequest<PriceDepth> restApiRequest =
        impl.getPriceDepth("btcusdt", 1);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}
