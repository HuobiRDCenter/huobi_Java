package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Symbol;
import java.util.List;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestGetSymbols {

  private RestApiRequestImpl impl = null;
  private String data = "{\n"
      + "\t\"status\": \"ok\",\n"
      + "\t\"data\": [{\n"
      + "\t\t\"base-currency\": \"btc\",\n"
      + "\t\t\"quote-currency\": \"usdt\",\n"
      + "\t\t\"price-precision\": 2,\n"
      + "\t\t\"amount-precision\": 4,\n"
      + "\t\t\"symbol-partition\": \"main\",\n"
      + "\t\t\"symbol\": \"btcusdt\"\n"
      + "\t}, {\n"
      + "\t\t\"base-currency\": \"bch\",\n"
      + "\t\t\"quote-currency\": \"usdt\",\n"
      + "\t\t\"price-precision\": 3,\n"
      + "\t\t\"amount-precision\": 5,\n"
      + "\t\t\"symbol-partition\": \"main\",\n"
      + "\t\t\"symbol\": \"bchusdt\"\n"
      + "\t}]\n"
      + "}";

  private String dataError = "{\n"
      + "\t\"status\": \"ok\",\n"
      + "\t\"data\": [{\n"
      + "\t\t\"base-currency\": \"btc\",\n"
      + "\t\t\"quote-currency\": \"usdt\",\n"
      + "\t\t\"symbol\": \"btcusdt\"\n"
      + "\t}, {\n"
      + "\t\t\"base-currency\": \"bch\",\n"
      + "\t\t\"quote-currency\": \"usdt\",\n"
      + "\t\t\"price-precision\": 3,\n"
      + "\t\t\"amount-precision\": 5,\n"
      + "\t\t\"symbol-partition\": \"main\",\n"
      + "\t\t\"symbol\": \"bchusdt\"\n"
      + "\t}]\n"
      + "}";

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("", "", new RequestOptions());
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test() {
    RestApiRequest<List<Symbol>> restApiRequest = impl.getSymbols();
    Request request = restApiRequest.request;
    assertEquals("GET", request.method());
    assertTrue(request.url().toString().contains("/v1/common/symbols"));
    List<Symbol> result = restApiRequest.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals(2, result.size());
    assertEquals("btcusdt", result.get(0).getSymbol());
    assertEquals("btc", result.get(0).getBaseCurrency());
    assertEquals("usdt", result.get(0).getQuoteCurrency());
    assertEquals(2, result.get(0).getPricePrecision());
    assertEquals(4, result.get(0).getAmountPrecision());
    assertEquals("main", result.get(0).getSymbolPartition());

    assertEquals("bchusdt", result.get(1).getSymbol());
    assertEquals("bch", result.get(1).getBaseCurrency());
    assertEquals("usdt", result.get(1).getQuoteCurrency());
    assertEquals(3, result.get(1).getPricePrecision());
    assertEquals(5, result.get(1).getAmountPrecision());
    assertEquals("main", result.get(1).getSymbolPartition());
  }

  @Test
  public void test_error() {
    RestApiRequest<List<Symbol>> restApiRequest = impl.getSymbols();
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(JsonWrapper.parseFromString(dataError));
  }
}
