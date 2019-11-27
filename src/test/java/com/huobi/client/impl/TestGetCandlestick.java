package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.enums.CandlestickInterval;
import java.math.BigDecimal;
import java.util.List;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestGetCandlestick {

  private RestApiRequestImpl impl = null;

  private static final String data = "{\n"
      + "  \"status\": \"ok\",\n"
      + "  \"ch\": \"market.btcusdt.kline.1day\",\n"
      + "  \"ts\": 1550197964381,\n"
      + "  \"data\": [\n"
      + "    {\n"
      + "      \"id\": 1550160000,\n"
      + "      \"open\": 3600.770000000000000000,\n"
      + "      \"close\": 3602.380000000000000000,\n"
      + "      \"low\": 3575.000000000000000000,\n"
      + "      \"high\": 3612.190000000000000000,\n"
      + "      \"amount\": 4562.766744240224615720,\n"
      + "      \"vol\": 16424799.084153959200406053550000000000000000,\n"
      + "      \"count\": 28891\n"
      + "    },\n"
      + "    {\n"
      + "      \"id\": 1550073600,\n"
      + "      \"open\": 3594.850000000000000000,\n"
      + "      \"close\": 3600.790000000000000000,\n"
      + "      \"low\": 3570.000000000000000000,\n"
      + "      \"high\": 3624.300000000000000000,\n"
      + "      \"amount\": 14514.049885396099061006,\n"
      + "      \"vol\": 52311364.004324447892964650980000000000000000,\n"
      + "      \"count\": 99003\n"
      + "    }\n"
      + "  ]\n"
      + "}";

  private static final String unexpectedData = "{\n"
      + "  \"status\": \"ok\",\n"
      + "  \"ch\": \"market.btcusdt.kline.1day\",\n"
      + "  \"ts\": 1550197964381,\n"
      + "  \"data\": [\n"
      + "    {\n"
      + "      \"id\": 1550160000,\n"
      + "      \"open\": 3600.770000000000000000,\n"
      + "      \"high\": 3612.190000000000000000,\n"
      + "      \"amount\": 4562.766744240224615720,\n"
      + "      \"vol\": 16424799.084153959200406053550000000000000000,\n"
      + "      \"count\": 28891\n"
      + "    },\n"
      + "    {\n"
      + "      \"id\": 1550073600,\n"
      + "      \"open\": 3594.850000000000000000,\n"
      + "      \"high\": 3624.300000000000000000,\n"
      + "      \"amount\": 14514.049885396099061006,\n"
      + "      \"vol\": 52311364.004324447892964650980000000000000000,\n"
      + "      \"count\": 99003\n"
      + "    }\n"
      + "  ]\n"
      + "}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("", "", new RequestOptions());
  }

  private void testPeriodAndNullSize(CandlestickInterval interval, String period) {
    RestApiRequest<List<Candlestick>> restApiRequest =
        impl.getCandlestick("btcusdt", interval, null, null, null);
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
    impl.getCandlestick("btcusdt", null, null, null, null);
  }

  @Test
  public void testUnexpectedSize_lessMin() {
    thrown.expect(HuobiApiException.class);
    impl.getCandlestick("btcusdt", CandlestickInterval.MIN1, null, null, -1);
  }

  @Test
  public void testUnexpectedSize_greaterMax() {
    thrown.expect(HuobiApiException.class);
    impl.getCandlestick("btcusdt", CandlestickInterval.MIN1, null, null, 2001);
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.getCandlestick("$$$$", CandlestickInterval.MIN1, null, null, null);
  }

  @Test
  public void testNormalCase() {
    RestApiRequest<List<Candlestick>> restApiRequest =
        impl.getCandlestick("btcusdt", CandlestickInterval.YEAR1, null, null, 100);
    Request request = restApiRequest.request;
    assertEquals(request.method(), "GET");
    assertTrue(request.url().toString().contains("/market/history/kline"));
    assertEquals("btcusdt", request.url().queryParameter("symbol"));
    assertEquals("1year", request.url().queryParameter("period"));
    assertEquals("100", request.url().queryParameter("size"));
  }

  @Test
  public void testResult_Normal() {
    RestApiRequest<List<Candlestick>> restApiRequest =
        impl.getCandlestick("btcusdt", CandlestickInterval.YEAR1, null, null, 100);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    List<Candlestick> candlestickList = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(2, candlestickList.size());
    assertEquals(1550160000L, candlestickList.get(0).getTimestamp());
    assertEquals(new BigDecimal("3612.19"), candlestickList.get(0).getHigh());
    assertEquals(new BigDecimal("3575"), candlestickList.get(0).getLow());
    assertEquals(new BigDecimal("3600.77"), candlestickList.get(0).getOpen());
    assertEquals(new BigDecimal("3602.38"), candlestickList.get(0).getClose());
    assertEquals(new BigDecimal("4562.76674424022461572"), candlestickList.get(0).getAmount());
    assertEquals(new BigDecimal("16424799.08415395920040605355"),
        candlestickList.get(0).getVolume());
    assertEquals(28891L, candlestickList.get(0).getCount());

    assertEquals(1550160000L, candlestickList.get(0).getTimestamp());
    assertEquals(new BigDecimal("3624.3"), candlestickList.get(1).getHigh());
    assertEquals(new BigDecimal("3570"), candlestickList.get(1).getLow());
    assertEquals(new BigDecimal("3594.85"), candlestickList.get(1).getOpen());
    assertEquals(new BigDecimal("3600.79"), candlestickList.get(1).getClose());
    assertEquals(new BigDecimal("14514.049885396099061006"), candlestickList.get(1).getAmount());
    assertEquals(new BigDecimal("52311364.00432444789296465098"),
        candlestickList.get(1).getVolume());
    assertEquals(99003L, candlestickList.get(1).getCount());
  }

  @Test
  public void testResult_Unexpected() {
    RestApiRequest<List<Candlestick>> restApiRequest =
        impl.getCandlestick("btcusdt", CandlestickInterval.YEAR1, null, null, 100);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(unexpectedData);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}
