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
import com.huobi.client.model.TradeStatistics;

import static org.junit.Assert.assertEquals;


public class TestGet24HTradeStatistics {

  private RestApiRequestImpl impl = null;
  private static final String data = "{\n" +
      "  \"status\": \"ok\",\n" +
      "  \"ch\": \"market.ethusdt.detail\",\n" +
      "  \"ts\": 1550224944129,\n" +
      "  \"tick\": {\n" +
      "    \"amount\": 224419.35108158883,\n" +
      "    \"open\": 121.84,\n" +
      "    \"close\": 121.97,\n" +
      "    \"high\": 123.42,\n" +
      "    \"id\": 100417200521,\n" +
      "    \"count\": 69299,\n" +
      "    \"low\": 120.25,\n" +
      "    \"version\": 100417200521,\n" +
      "    \"vol\": 27305221.739623416\n" +
      "  }\n" +
      "}";
  private static final String Errordata = "{\n" +
      "  \"status\": \"ok\",\n" +
      "  \"ch\": \"market.ethusdt.detail\",\n" +
      "  \"ts\": 1550224944129,\n" +
      "  \"tick\": {\n" +
      "    \"amount\": 224419.35108158883,\n" +
      "    \"open\": 121.84,\n" +
      "    \"high\": 123.42,\n" +
      "    \"id\": 100417200521,\n" +
      "    \"count\": 69299,\n" +
      "    \"low\": 120.25,\n" +
      "    \"version\": 100417200521,\n" +
      "    \"vol\": 27305221.739623416\n" +
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
    RestApiRequest<TradeStatistics> restApiRequest =
        impl.get24HTradeStatistics("btcusdt");
    Request request = restApiRequest.request;
    assertEquals("GET", request.method());
    assertEquals("btcusdt", request.url().queryParameter("symbol"));
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.get24HTradeStatistics("$$$$");
  }


  @Test
  public void testResult_Normal() {
    RestApiRequest<TradeStatistics> restApiRequest =
        impl.get24HTradeStatistics("btcusdt");
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    TradeStatistics tradeStatistics = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(new BigDecimal("224419.35108158883"), tradeStatistics.getAmount());
    assertEquals(1550224944129L, tradeStatistics.getTimestamp());
    assertEquals(new BigDecimal("121.97"), tradeStatistics.getClose());
    assertEquals(new BigDecimal("123.42"), tradeStatistics.getHigh());
    assertEquals(new BigDecimal("120.25"), tradeStatistics.getLow());
    assertEquals(new BigDecimal("121.84"), tradeStatistics.getOpen());
    assertEquals(new BigDecimal("27305221.739623416"), tradeStatistics.getVolume());
    assertEquals(69299L, tradeStatistics.getCount());
    //assertEquals(100417200521L, tradeStatistics.getId());

  }

  @Test
  public void testResult_Unexpected() {
    RestApiRequest<TradeStatistics> restApiRequest =
        impl.get24HTradeStatistics("btcusdt");
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}
