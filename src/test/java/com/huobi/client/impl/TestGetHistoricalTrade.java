package com.huobi.client.impl;

import java.math.BigDecimal;
import java.util.List;

import okhttp3.Request;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Trade;
import com.huobi.client.model.enums.TradeDirection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class TestGetHistoricalTrade {

  private RestApiRequestImpl impl = null;

  private static final String data = "{\n" +
      "  \"status\": \"ok\",\n" +
      "  \"ch\": \"market.ethusdt.trade.detail\",\n" +
      "  \"ts\": 1550222502992,\n" +
      "  \"data\": [\n" +
      "    {\n" +
      "      \"id\": 100416958491,\n" +
      "      \"ts\": 1550222502562,\n" +
      "      \"data\": [\n" +
      "        {\n" +
      "          \"amount\": 0.007100000000000000,\n" +
      "          \"ts\": 1550222502562,\n" +
      "          \"id\": 10041695849124569905216,\n" +
      "          \"price\": 122.180000000000000000,\n" +
      "          \"direction\": \"sell\"\n" +
      "        }\n" +
      "      ]\n" +
      "    },\n" +
      "    {\n" +
      "      \"id\": 100416958394,\n" +
      "      \"ts\": 1550222501237,\n" +
      "      \"data\": [\n" +
      "        {\n" +
      "          \"amount\": 0.489300000000000000,\n" +
      "          \"ts\": 1550222501237,\n" +
      "          \"id\": 10041695839424569907865,\n" +
      "          \"price\": 122.160000000000000000,\n" +
      "          \"direction\": \"sell\"\n" +
      "        }\n" +
      "        {\n" +
      "          \"amount\": 0.735400000000000000,\n" +
      "          \"ts\": 1551233842487,\n" +
      "          \"id\": 10041773949425560687111,\n" +
      "          \"price\": 3804.000000000000000000,\n" +
      "          \"direction\": \"buy\"\n" +
      "        }" +
      "      ]\n" +
      "    }\n" +
      "  ]\n" +
      "}";

  private static final String Errordata = "{\n" +
      "  \"status\": \"ok\",\n" +
      "  \"ch\": \"market.ethusdt.trade.detail\",\n" +
      "  \"ts\": 1550222024467,\n" +
      "  \"data\": [\n" +
      "    {\n" +
      "      \"id\": 100416909371,\n" +
      "      \"ts\": 1550222019519,\n" +
      "      \"data\": [\n" +
      "        {\n" +
      "          \"ts\": 1550222019519,\n" +
      "          \"id\": 10041690937124569519358,\n" +
      "          \"price\": 122.320000000000000000,\n" +
      "          \"direction\": \"buy\"\n" +
      "        }\n" +
      "      ]\n" +
      "    }\n" +
      "  ]\n" +
      "}";
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("", "", new RequestOptions());
  }

  @Test
  public void testNormalCase() {
    RestApiRequest<List<Trade>> restApiRequest =
        impl.getHistoricalTrade("btcusdt", "12345", null);
    Request request = restApiRequest.request;
    assertEquals("GET", request.method());
    assertTrue(request.url().url().toString().contains("/market/history/trade"));
    assertEquals("btcusdt", request.url().queryParameter("symbol"));
    assertNull(request.url().queryParameter("size"));
  }


  @Test
  public void testUnexpectedSize_lessMin() {
    thrown.expect(HuobiApiException.class);
    impl.getHistoricalTrade("btcusdt", "hh", -1);
  }

  @Test
  public void testUnexpectedSize_greaterMax() {
    thrown.expect(HuobiApiException.class);
    impl.getHistoricalTrade("btcusdt", "hh", 2001);
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.getHistoricalTrade("$$$$", "hh", 100);
  }


  @Test
  public void testResult_Normal() {
    RestApiRequest<List<Trade>> restApiRequest =
        impl.getHistoricalTrade("btcusdt", "hh", 2);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    List<Trade> tradeList = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(3, tradeList.size());
    assertEquals(new BigDecimal("122.18"), tradeList.get(0).getPrice());
    assertEquals(new BigDecimal("0.0071"), tradeList.get(0).getAmount());
    assertEquals(1550222502562L, tradeList.get(0).getTimestamp());
    assertEquals("10041695849124569905216", tradeList.get(0).getTradeId());
    assertEquals(TradeDirection.SELL, tradeList.get(0).getDirection());

  }

  @Test
  public void testResult_Unexpected() {
    RestApiRequest<List<Trade>> restApiRequest =
        impl.getHistoricalTrade("btcusdt", "hh", 2);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }


}
