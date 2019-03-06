package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.request.TransferRequest;
import java.math.BigDecimal;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestTransfer {

  private RestApiRequestImpl impl = null;
  private String data = "{\n"
      + "  \"status\": \"ok\",\n"
      + "  \"data\": 1000\n"
      + "}";

  private String dataError = "{\n"
      + "  \"status\": \"ok\"\n"
      + "}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
  }

  @Test
  public void testRequest_transfer_in() {
    TransferRequest req = new TransferRequest(
        "btcusdt", AccountType.SPOT, AccountType.MARGIN, "btc", new BigDecimal("1.1"));
    RestApiRequest<Long> restApiRequest = impl.transfer(req);
    Request request = restApiRequest.request;
    assertTrue(request.url().toString().contains("/v1/dw/transfer-in/margin"));
    assertEquals("POST", request.method());
    MockPostQuerier querier = new MockPostQuerier(request);
    assertEquals("btcusdt", querier.jsonWrapper.getString("symbol"));
    assertEquals("btc", querier.jsonWrapper.getString("currency"));
    assertEquals(new BigDecimal("1.1"), querier.jsonWrapper.getBigDecimal("amount"));
  }

  @Test
  public void testRequest_transfer_out() {
    TransferRequest req = new TransferRequest(
        "btcusdt", AccountType.MARGIN, AccountType.SPOT, "ht", new BigDecimal("2.1"));
    RestApiRequest<Long> restApiRequest = impl.transfer(req);
    Request request = restApiRequest.request;
    assertTrue(request.url().toString().contains("/v1/dw/transfer-out/margin"));
    MockPostQuerier querier = new MockPostQuerier(request);
    assertEquals("btcusdt", querier.jsonWrapper.getString("symbol"));
    assertEquals("ht", querier.jsonWrapper.getString("currency"));
    assertEquals(new BigDecimal("2.1"), querier.jsonWrapper.getBigDecimal("amount"));
  }

  @Test
  public void testRequest_errorAccount() {
    TransferRequest req = new TransferRequest(
        "btcusdt", AccountType.OTC, AccountType.MARGIN, "btc", new BigDecimal("1.1"));
    TransferRequest request = new TransferRequest(
        "btcusdt", null, null, "btc", new BigDecimal("1.1"));
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("incorrect transfer type");
    impl.transfer(req);
    impl.transfer(request);
  }

  @Test
  public void testResult_normal() {
    TransferRequest req = new TransferRequest(
        "btcusdt", AccountType.SPOT, AccountType.MARGIN, "btc", new BigDecimal("1.1"));
    RestApiRequest<Long> request = impl.transfer(req);
    Long result = request.jsonParser.parseJson(JsonWrapper.parseFromString(data));
    assertEquals(1000L, result.longValue());
  }

  @Test
  public void testResult_error() {
    TransferRequest req = new TransferRequest(
        "btcusdt", AccountType.SPOT, AccountType.MARGIN, "btc", new BigDecimal("1.1"));
    RestApiRequest<Long> request = impl.transfer(req);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    request.jsonParser.parseJson(JsonWrapper.parseFromString(dataError));
  }

  @Test
  public void testInvalidSymbol() {
    TransferRequest req = new TransferRequest(
        "?", AccountType.SPOT, AccountType.MARGIN, "btc", new BigDecimal("1.1"));
    thrown.expect(HuobiApiException.class);
    impl.transfer(req);
  }

  @Test
  public void testInvalidCurrency() {
    TransferRequest req = new TransferRequest(
        "htbtc", AccountType.SPOT, AccountType.MARGIN, "?", new BigDecimal("1.1"));
    thrown.expect(HuobiApiException.class);
    impl.transfer(req);
  }
}
