package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.Symbol;
import com.huobi.client.model.TradeStatistics;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestGetTickers {
  private RestApiRequestImpl impl = null;
  private String data = "{\n"
      + "\t\"status\": \"ok\",\n"
      + "\t\"ts\": 1554793531966,\n"
      + "\t\"data\": [{\n"
      + "\t\t\t\"open\": 0.008253,\n"
      + "\t\t\t\"close\": 0.008017,\n"
      + "\t\t\t\"low\": 0.007701,\n"
      + "\t\t\t\"high\": 0.0088,\n"
      + "\t\t\t\"amount\": 115547050.74898805,\n"
      + "\t\t\t\"count\": 13611,\n"
      + "\t\t\t\"vol\": 941495.9018102519,\n"
      + "\t\t\t\"symbol\": \"socusdt\"\n"
      + "\t\t},\n"
      + "\t\t{\n"
      + "\t\t\t\"open\": 0.00006709,\n"
      + "\t\t\t\"close\": 0.00006482,\n"
      + "\t\t\t\"low\": 0.0000638,\n"
      + "\t\t\t\"high\": 0.00006772,\n"
      + "\t\t\t\"amount\": 3341375.549227308,\n"
      + "\t\t\t\"count\": 14724,\n"
      + "\t\t\t\"vol\": 221.48015265566755,\n"
      + "\t\t\t\"symbol\": \"iotabtc\"\n"
      + "\t\t},\n"
      + "\t\t{\n"
      + "\t\t\t\"open\": 0.00009418,\n"
      + "\t\t\t\"close\": 0.00009202,\n"
      + "\t\t\t\"low\": 0.00009055,\n"
      + "\t\t\t\"high\": 0.00009806,\n"
      + "\t\t\t\"amount\": 340929.4393718561,\n"
      + "\t\t\t\"count\": 7758,\n"
      + "\t\t\t\"vol\": 32.24606622910366,\n"
      + "\t\t\t\"symbol\": \"steembtc\"\n"
      + "\t\t}\n"
      + "\t]\n"
      + "}";

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("", "", new RequestOptions());
  }

  @Test
  public void test() {
    RestApiRequest<Map<String, TradeStatistics>> restApiRequest = impl.getTickers();
    Request request = restApiRequest.request;
    assertEquals("GET", request.method());
    assertTrue(request.url().toString().contains("/market/tickers"));
    Map<String, TradeStatistics> result = restApiRequest.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals(3, result.size());
    assertTrue(result.containsKey("socusdt"));
    TradeStatistics socusdt = result.get("socusdt");
    assertEquals(new BigDecimal("115547050.74898805"), socusdt.getAmount());
    assertEquals(TimeService.convertCSTInMillisecondToUTC(1554793531966L),
        socusdt.getTimestamp());
    assertEquals(new BigDecimal("0.008017"), socusdt.getClose());
    assertEquals(new BigDecimal("0.0088"), socusdt.getHigh());
    assertEquals(new BigDecimal("0.007701"), socusdt.getLow());
    assertEquals(new BigDecimal("0.008253"), socusdt.getOpen());
    assertEquals(new BigDecimal("941495.9018102519"), socusdt.getVolume());
    assertEquals(13611, socusdt.getCount());
  }
}
