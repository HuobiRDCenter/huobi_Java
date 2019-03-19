package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.MatchResult;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TestGetMatchResult {

  private RestApiRequestImpl impl = null;
  private static final String data = "\n" +
      "{\n" +
      "    \"status\":\"ok\",\n" +
      "    \"data\":[\n" +
      "        {\n" +
      "            \"symbol\":\"htbtc\",\n" +
      "            \"created-at\":1550632074577,\n" +
      "            \"filled-points\":\"0\",\n" +
      "            \"source\":\"spot-api\",\n" +
      "            \"price\":\"0.00030754\",\n" +
      "            \"filled-amount\":\"1\",\n" +
      "            \"filled-fees\":\"0.00000061508\",\n" +
      "            \"match-id\":100047251154,\n" +
      "            \"order-id\":24966984923,\n" +
      "            \"id\":4191225853,\n" +
      "            \"type\":\"sell-market\"\n" +
      "        },\n" +
      "        {\n" +
      "            \"symbol\":\"htbtc\",\n" +
      "            \"created-at\":1550632074577,\n" +
      "            \"filled-points\":\"0\",\n" +
      "            \"source\":\"spot-api\",\n" +
      "            \"price\":\"0.00030754\",\n" +
      "            \"filled-amount\":\"1\",\n" +
      "            \"filled-fees\":\"0.00000061508\",\n" +
      "            \"match-id\":100047251154,\n" +
      "            \"order-id\":24966984923,\n" +
      "            \"id\":4191225853,\n" +
      "            \"type\":\"sell-market\"\n" +
      "        }\n" +
      "    ]\n" +
      "}";
  private static final String Errordata = "\n" +
      "{\n" +
      "    \"status\":\"ok\",\n" +
      "    \"data\":[\n" +
      "        {\n" +
      "            \"symbol\":\"htbtc\",\n" +
      "            \"created-at\":1550632074577,\n" +
      "            \"filled-points\":\"0\",\n" +
      "            \"source\":\"spot-api\",\n" +
      "            \"price\":\"0.00030754\",\n" +
      "            \"filled-fees\":\"0.00000061508\",\n" +
      "            \"match-id\":100047251154,\n" +
      "            \"order-id\":24966984923,\n" +
      "            \"id\":4191225853,\n" +
      "            \"type\":\"sell-market\"\n" +
      "        }\n" +
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
    RestApiRequest<List<MatchResult>> restApiRequest = impl.getMatchResults("htbtc", 24966984923L);
    String url = String.format("/v1/order/orders/%d/matchresults", 24966984923L);
    assertTrue(restApiRequest.request.url().toString().contains(url));
    assertEquals("GET", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
  }

  @Test
  public void testResult_Normal() {

    RestApiRequest<List<MatchResult>> restApiRequest =
        impl.getMatchResults("htbtc", 24966984923L);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    List<MatchResult> matchResults = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(TimeService.convertCSTInMillisecondToUTC(1550632074577L),
        matchResults.get(0).getCreatedTimestamp());
    assertEquals(4191225853L, matchResults.get(0).getId());
    assertEquals(100047251154L, matchResults.get(0).getMatchId());
    assertEquals(24966984923L, matchResults.get(0).getOrderId());
    assertEquals(new BigDecimal("1"), matchResults.get(0).getFilledAmount());
    assertEquals(new BigDecimal("0.00000061508"), matchResults.get(0).getFilledFees());
    assertEquals(new BigDecimal("0.00030754"), matchResults.get(0).getPrice());
    assertEquals("spot-api", matchResults.get(0).getSource().toString());
    assertEquals("htbtc", matchResults.get(0).getSymbol());
    assertEquals("sell-market", matchResults.get(0).getType().toString());


  }

  @Test
  public void testResult_Unexpected() {
    RestApiRequest<List<MatchResult>> restApiRequest =
        impl.getMatchResults("htbtc", 24966984923L);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    impl.getMatchResults("?", 24966984923L);

  }


}
