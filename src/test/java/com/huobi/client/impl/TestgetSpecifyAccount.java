package com.huobi.client.impl;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.CompleteSubAccountInfo;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceType;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class TestgetSpecifyAccount {


  private RestApiRequestImpl impl = null;

  String data = "{\n" +
      "       \"status\": \"ok\",\n" +
      "\t\"data\": [\n" +
      "\t{\n" +
      "\t   \"id\": 9910049,\n" +
      "\t   \"type\": \"spot\",\n" +
      "\t   \"list\": [\n" +
      "             {\n" +
      "\t       \"currency\": \"btc\",\n" +
      "\t        \"type\": \"trade\",\n" +
      "\t        \"balance\": \"1.00\"\n" +
      "\t     },\n" +
      "\t     {\n" +
      "\t       \"currency\": \"eth\",\n" +
      "\t       \"type\": \"trade\",\n" +
      "\t       \"balance\": \"1934.00\"\n" +
      "\t     }\n" +
      "\t     ]\n" +
      "\t},\n" +
      "\t{\n" +
      "\t\"id\": 9910050,\n" +
      "\t\"type\": \"point\",\n" +
      "\t\"list\": []\n" +
      "\t}\n" +
      "\t]\n" +
      "}\n";

  private String dataError = "{\n" +
      "       \"status\": \"ok\",\n" +
      "\t\"data\": [\n" +
      "\t{\n" +
      "\t   \"id\": 9910049,\n" +
      "\t   \"list\": [\n" +
      "             {\n" +
      "\t       \"currency\": \"btc\",\n" +
      "\t        \"type\": \"trade\",\n" +
      "\t        \"balance\": \"1.00\"\n" +
      "\t     },\n" +
      "\t     {\n" +
      "\t       \"currency\": \"eth\",\n" +
      "\t       \"type\": \"trade\",\n" +
      "\t       \"balance\": \"1934.00\"\n" +
      "\t     }\n" +
      "\t     ]\n" +
      "\t},\n" +
      "\t{\n" +
      "\t\"id\": 9910050,\n" +
      "\t\"type\": \"point\",\n" +
      "\t\"list\": []\n" +
      "\t}\n" +
      "\t]\n" +
      "}\n";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
  }

  @Test
  public void testInput() {
    RestApiRequest<List<CompleteSubAccountInfo>> restApiRequest = impl.getSpecifyAccountBalance(12345L);
    String url = String.format("/v1/account/accounts/%d", 12345L);
    assertTrue(restApiRequest.request.url().toString().contains(url));
    assertEquals("GET", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));

  }

  @Test
  public void testResult_Normal() {
    RestApiRequest<List<CompleteSubAccountInfo>> restApiRequest = impl.getSpecifyAccountBalance(12345L);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    List<CompleteSubAccountInfo> completeSubAccountInfos = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(2, completeSubAccountInfos.size());
    assertEquals(9910049L, completeSubAccountInfos.get(0).getId());
    assertEquals(AccountType.SPOT, completeSubAccountInfos.get(0).getType());

    assertEquals(9910050L, completeSubAccountInfos.get(1).getId());
    assertEquals(AccountType.POINT, completeSubAccountInfos.get(1).getType());
    assertEquals("btc", completeSubAccountInfos.get(0).getBalanceList().get(0).getCurrency());
    assertEquals(BalanceType.TRADE, completeSubAccountInfos.get(0).getBalanceList().get(0).getType());
    assertEquals(new BigDecimal("1"), completeSubAccountInfos.get(0).getBalanceList().get(0).getBalance());
    assertEquals("eth", completeSubAccountInfos.get(0).getBalanceList().get(1).getCurrency());
    assertEquals(BalanceType.TRADE, completeSubAccountInfos.get(0).getBalanceList().get(1).getType());
    assertEquals(new BigDecimal("1934"), completeSubAccountInfos.get(0).getBalanceList().get(1).getBalance());

    assertEquals(0, completeSubAccountInfos.get(1).getBalanceList().size());
    assertEquals(2, completeSubAccountInfos.get(0).getBalanceList().size());
  }

  @Test
  public void testResult_Unexpected() {
    RestApiRequest<List<CompleteSubAccountInfo>> restApiRequest =
        impl.getSpecifyAccountBalance(12344L);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(dataError);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}