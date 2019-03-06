package com.huobi.client.impl;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Balance;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestgetCurrentUserAllAccounts {


  private RestApiRequestImpl impl = null;

  String data = "{\n" +
      "  \"status\": \"ok\",\n" +
      "  \"data\": [\n" +
      "      {\n" +
      "        \"currency\": \"eos\",\n" +
      "        \"balance\": \"1954559.809500000000000000\"\n" +
      "      },\n" +
      "      {\n" +
      "        \"currency\": \"btc\",\n" +
      "        \"balance\": \"0.000000000000000000\"\n" +
      "      },\n" +
      "      {\n" +
      "        \"currency\": \"usdt\",\n" +
      "        \"balance\": \"2925209.411300000000000000\"\n" +
      "      },\n" +
      "   ]\n" +
      "}";

  private String dataError = "{\n" +
      "  \"status\": \"ok\",\n" +
      "  \"data\": [\n" +
      "      {\n" +
      "        \"currency\": \"eos\",\n" +
      "      },\n" +
      "      {\n" +
      "        \"currency\": \"btc\",\n" +
      "        \"balance\": \"0.000000000000000000\"\n" +
      "      },\n" +
      "      {\n" +
      "        \"currency\": \"usdt\",\n" +
      "        \"balance\": \"2925209.411300000000000000\"\n" +
      "      },\n" +
      "   ]\n" +
      "}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
  }

  @Test
  public void testInput() {
    RestApiRequest<List<Balance>> restApiRequest = impl.getCurrentUserAggregatedBalance();

    assertTrue(restApiRequest.request.url().toString().contains("v1/subuser/aggregate-balance"));
    assertEquals("GET", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));

  }

  @Test
  public void testResult_Normal() {
    RestApiRequest<List<Balance>> restApiRequest = impl.getCurrentUserAggregatedBalance();
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    List<Balance> subAccounts = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(3, subAccounts.size());
    assertEquals("eos", subAccounts.get(0).getCurrency());
    assertEquals("btc", subAccounts.get(1).getCurrency());
    assertEquals("usdt", subAccounts.get(2).getCurrency());
    assertEquals(new BigDecimal("1954559.8095"), subAccounts.get(0).getBalance());
    assertEquals(new BigDecimal("0"), subAccounts.get(1).getBalance());
    assertEquals(new BigDecimal("2925209.4113"), subAccounts.get(2).getBalance());
  }

  @Test
  public void testResult_Unexpected() {
    RestApiRequest<List<Balance>> restApiRequest =
        impl.getCurrentUserAggregatedBalance();
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(dataError);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}