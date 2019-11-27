package com.huobi.client.impl;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.Deposit;
import com.huobi.client.model.enums.DepositState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class TestGetDepositHistory {

  private RestApiRequestImpl impl = null;
  private static final String data = "{\n" +
      "    \n" +
      "    \"status\": \"ok\",\n" +
      "    \"data\": [\n" +
      "      {\n" +
      "        \"id\": 1171,\n" +
      "        \"type\": \"deposit\",\n" +
      "        \"currency\": \"ht\",\n" +
      "        \"tx-hash\": \"ed03094b84eafbe4bc16e7ef766ee959885ee5bcb265872baaa9c64e1cf86c2b\",\n"
      +
      "        \"amount\": 7.457467,\n" +
      "        \"address\": \"rae93V8d2mdoUQHwBDBdM4NHCMehRJAsbm\",\n" +
      "        \"address-tag\": \"100040\",\n" +
      "        \"fee\": 345,\n" +
      "        \"state\": \"confirmed\",\n" +
      "        \"created-at\": 1510912472199,\n" +
      "        \"updated-at\": 1511145876575\n" +
      "      }\n" +
      "    ]\n" +
      "}";
  private static final String Errordata = "{\n" +
      "    \n" +
      "    \"status\": \"ok\",\n" +
      "    \"data\": [\n" +
      "      {\n" +
      "        \"id\": 1171,\n" +
      "        \"type\": \"deposit\",\n" +
      "        \"currency\": \"xrp\",\n" +
      "        \"amount\": 7.457467,\n" +
      "        \"address\": \"rae93V8d2mdoUQHwBDBdM4NHCMehRJAsbm\",\n" +
      "        \"address-tag\": \"100040\",\n" +
      "        \"fee\": 0,\n" +
      "        \"state\": \"safe\",\n" +
      "        \"created-at\": 1510912472199,\n" +
      "        \"updated-at\": 1511145876575\n" +
      "      }\n" +
      "    ]\n" +
      "}";


  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
  }

  @Test
  public void test() {
    RestApiRequest<List<Deposit>> restApiRequest = impl.getDepositHistory("btc", 24966984923L, 1);
    assertTrue(restApiRequest.request.url().toString().contains("/v1/query/deposit-withdraw"));
    assertEquals("GET", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
    assertEquals("btc", restApiRequest.request.url().queryParameter("currency"));
    assertEquals("deposit", restApiRequest.request.url().queryParameter("type"));
    assertEquals("24966984923", restApiRequest.request.url().queryParameter("from"));
    assertEquals("1", restApiRequest.request.url().queryParameter("size"));
  }

  @Test
  public void testResult_Normal() {
    RestApiRequest<List<Deposit>> restApiRequest =
        impl.getDepositHistory("ht", 24966984923L, 1);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    List<Deposit> withdrawDepositList = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(new BigDecimal("345"), withdrawDepositList.get(0).getFee());
    assertEquals(1171L, withdrawDepositList.get(0).getId());
    assertEquals(1510912472199L, withdrawDepositList.get(0).getCreatedTimestamp());
    assertEquals(1511145876575L, withdrawDepositList.get(0).getUpdatedTimestamp());
    assertEquals(new BigDecimal("7.457467"), withdrawDepositList.get(0).getAmount());
    assertEquals("rae93V8d2mdoUQHwBDBdM4NHCMehRJAsbm", withdrawDepositList.get(0).getAddress());
    assertEquals("100040", withdrawDepositList.get(0).getAddressTag());
    assertEquals("ht", withdrawDepositList.get(0).getCurrency());
    assertEquals("ed03094b84eafbe4bc16e7ef766ee959885ee5bcb265872baaa9c64e1cf86c2b",
        withdrawDepositList.get(0).getTxHash());
    assertEquals(DepositState.CONFIRMED, withdrawDepositList.get(0).getDepositState());
  }

  @Test
  public void testResult_Unexpected() {

    RestApiRequest<List<Deposit>> restApiRequest =
        impl.getDepositHistory("htbtc", 24966984923L, 1);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.getWithdrawHistory("?", 24966984923L, 1);
  }


}
