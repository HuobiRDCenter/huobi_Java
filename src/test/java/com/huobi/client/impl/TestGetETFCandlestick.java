package com.huobi.client.impl;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.CandlestickInterval;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.List;

import okhttp3.Request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class TestGetETFCandlestick {

  private RestApiRequestImpl impl = null;

  private static final String data = "{\n" +
      "  \"code\": 200,\n" +
      "  \"success\": \"True\",\n" +
      "  \"data\": [\n" +
      "{\n" +
      "    \"id\": 1499184000,\n" +
      "    \"amount\": 123.123,\n" +
      "    \"open\": 0.7794,\n" +
      "    \"close\": 0.779,\n" +
      "    \"low\": 0.769,\n" +
      "    \"high\": 0.7694,\n" +
      "    \"vol\": 456.456\n" +
      "  },\n" +
      "]\n" +
      "}\n";

  private static final String unexpectedData = "{\n" +
      "  \"code\": 200,\n" +
      "  \"success\": \"True\",\n" +
      "  \"data\": [\n" +
      "{\n" +
      "    \"amount\": 0,\n" +
      "    \"open\": 0.7794,\n" +
      "    \"close\": 0.779,\n" +
      "    \"low\": 0.769,\n" +
      "    \"high\": 0.7694,\n" +
      "    \"vol\": 0\n" +
      "  },\n" +
      "]\n" +
      "}\n";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("", "", new RequestOptions());
  }

  private void testPeriodAndNullSize(CandlestickInterval interval, String period) {
    RestApiRequest<List<Candlestick>> restApiRequest =
        impl.getETFCandlestick("hb10", interval, null);
    Request request = restApiRequest.request;

    assertEquals(period, request.url().queryParameter("period"));
    assertNull(request.url().queryParameter("size"));
  }

  @Test
  public void testPeriodAndNullSize() {
    testPeriodAndNullSize(CandlestickInterval.MIN1, "1min");
    testPeriodAndNullSize(CandlestickInterval.DAY1, "1day");
  }

  @Test
  public void testNullInterval() {
    thrown.expect(HuobiApiException.class);
    impl.getETFCandlestick("hb10", null, null);
  }

  @Test
  public void testUnexpectedSize_lessMin() {
    thrown.expect(HuobiApiException.class);
    impl.getETFCandlestick("hb10", CandlestickInterval.MIN1, -1);
  }

  @Test
  public void testUnexpectedSize_greaterMax() {
    thrown.expect(HuobiApiException.class);
    impl.getETFCandlestick("hb10", CandlestickInterval.MIN1,  2001);
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.getETFCandlestick("$$$$", CandlestickInterval.MIN1, null);
  }

  @Test
  public void testUnSupportSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.getETFCandlestick("htbtc", CandlestickInterval.MIN1, null);
  }
  @Test
  public void testNormalCase() {
    RestApiRequest<List<Candlestick>> restApiRequest =
        impl.getETFCandlestick("hb10", CandlestickInterval.YEAR1, 100);
    Request request = restApiRequest.request;
    assertEquals(request.method(), "GET");
    assertTrue(request.url().toString().contains("/quotation/market/history/kline"));
    assertEquals("hb10", request.url().queryParameter("symbol"));
    assertEquals("1year", request.url().queryParameter("period"));
    assertEquals("100", request.url().queryParameter("limit"));
  }

  @Test
  public void testResult_Normal() {
    RestApiRequest<List<Candlestick>> restApiRequest =
        impl.getETFCandlestick("hb10", CandlestickInterval.YEAR1,  100);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    List<Candlestick> candlestickList = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(1, candlestickList.size());
    assertEquals(TimeService.convertCSTInSecondToUTC(1499184000L), candlestickList.get(0).getTimestamp());
    assertEquals(new BigDecimal("0.7694"), candlestickList.get(0).getHigh());
    assertEquals(new BigDecimal("0.769"), candlestickList.get(0).getLow());
    assertEquals(new BigDecimal("0.7794"), candlestickList.get(0).getOpen());
    assertEquals(new BigDecimal("0.779"), candlestickList.get(0).getClose());
    assertEquals(new BigDecimal("123.123"), candlestickList.get(0).getAmount());
    assertEquals(new BigDecimal("456.456"), candlestickList.get(0).getVolume());
    assertEquals(0, candlestickList.get(0).getCount());

    }

  @Test
  public void testResult_Unexpected() {
    RestApiRequest<List<Candlestick>> restApiRequest =
        impl.getETFCandlestick("hb10", CandlestickInterval.YEAR1, 100);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(unexpectedData);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}
