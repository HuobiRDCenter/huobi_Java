package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.MatchResult;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.MatchResultRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestGetMatchResults {

  private RestApiRequestImpl impl = null;
  private static final String data = "{\n" +
      "    \"status\":\"ok\",\n" +
      "    \"data\":[\n" +
      "        {\n" +
      "            \"symbol\":\"htbtc\",\n" +
      "            \"created-at\":1550642185237,\n" +
      "            \"price\":\"0.00030503\",\n" +
      "            \"source\":\"spot-api\",\n" +
      "            \"filled-points\":\"0\",\n" +
      "            \"filled-fees\":\"0.00000061006\",\n" +
      "            \"order-id\":24976625673,\n" +
      "            \"filled-amount\":\"1\",\n" +
      "            \"match-id\":100047439757,\n" +
      "            \"id\":4192759683,\n" +
      "            \"type\":\"sell-market\"\n" +
      "        },\n" +
      "        {\n" +
      "            \"symbol\":\"htbtc\",\n" +
      "            \"created-at\":1550632074577,\n" +
      "            \"price\":\"0.00030754\",\n" +
      "            \"source\":\"spot-api\",\n" +
      "            \"filled-points\":\"0.77\",\n" +
      "            \"filled-fees\":\"0.00000061508\",\n" +
      "            \"order-id\":24966984923,\n" +
      "            \"filled-amount\":\"1.89\",\n" +
      "            \"match-id\":100047251154,\n" +
      "            \"id\":4191225853,\n" +
      "            \"type\":\"sell-market\"\n" +
      "        }\n" +
      "    ]\n" +
      "}\n" +
      "\n";
  private static final String Errordata = "{\n" +
      "    \"status\":\"ok\",\n" +
      "    \"data\":[\n" +
      "        {\n" +
      "            \"symbol\":\"htbtc\",\n" +
      "            \"price\":\"0.00030503\",\n" +
      "            \"source\":\"spot-api\",\n" +
      "            \"filled-points\":\"0\",\n" +
      "            \"filled-fees\":\"0.00000061006\",\n" +
      "            \"order-id\":24976625673,\n" +
      "            \"filled-amount\":\"1\",\n" +
      "            \"match-id\":100047439757,\n" +
      "            \"id\":4192759683,\n" +
      "            \"type\":\"sell-market\"\n" +
      "        },\n" +
      "        {\n" +
      "            \"symbol\":\"htbtc\",\n" +
      "            \"created-at\":1550632074577,\n" +
      "            \"price\":\"0.00030754\",\n" +
      "            \"source\":\"spot-api\",\n" +
      "            \"filled-points\":\"0\",\n" +
      "            \"filled-fees\":\"0.00000061508\",\n" +
      "            \"order-id\":24966984923,\n" +
      "            \"filled-amount\":\"1\",\n" +
      "            \"match-id\":100047251154,\n" +
      "            \"id\":4191225853,\n" +
      "            \"type\":\"sell-market\"\n" +
      "        }\n" +
      "    ]\n" +
      "}\n" +
      "\n";

  private MatchResultRequest matchResultRequest;
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
    matchResultRequest = new MatchResultRequest("htbtc");
  }

  @Test
  public void test() {
    RestApiRequest<List<MatchResult>> restApiRequest = impl.getMatchResults(matchResultRequest);
    assertTrue(restApiRequest.request.url().toString().contains("/v1/order/matchresults"));
    assertEquals("GET", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
    assertEquals("htbtc", restApiRequest.request.url().queryParameter("symbol"));
    assertNull(restApiRequest.request.url().queryParameter("types"));
    assertNull(restApiRequest.request.url().queryParameter("start-date"));
    assertNull(restApiRequest.request.url().queryParameter("end-date"));


  }

  @Test
  public void testAnotherRequest() {
    Date Startdate = new Date();
    Date Enddate = new Date();
    MatchResultRequest matchResultRequest = new MatchResultRequest("btcht",
        OrderType.SELL_LIMIT, Startdate, Enddate, 50, "hh");
    RestApiRequest<List<MatchResult>> restApiRequest = impl.getMatchResults(matchResultRequest);

    assertTrue(restApiRequest.request.url().toString().contains("/v1/order/matchresults"));
    assertEquals("GET", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
    assertEquals("btcht", restApiRequest.request.url().queryParameter("symbol"));
    assertEquals(OrderType.SELL_LIMIT.toString(),
        restApiRequest.request.url().queryParameter("types"));

    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    assertEquals(dateFormatter.format(Startdate),
        restApiRequest.request.url().queryParameter("start-date"));
    assertEquals(dateFormatter.format(Enddate),
        restApiRequest.request.url().queryParameter("end-date"));

    assertEquals("hh", restApiRequest.request.url().queryParameter("from"));
    assertEquals("50", restApiRequest.request.url().queryParameter("size"));
  }


  @Test
  public void testResult_Normal() {
    RestApiRequest<List<MatchResult>> restApiRequest =
        impl.getMatchResults(matchResultRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    List<MatchResult> matchResults = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(TimeService.convertCSTInMillisecondToUTC(1550632074577L),
        matchResults.get(1).getCreatedTimestamp());
    assertEquals(4191225853L, matchResults.get(1).getId());
    assertEquals(100047251154L, matchResults.get(1).getMatchId());
    assertEquals(24966984923L, matchResults.get(1).getOrderId());
    assertEquals(new BigDecimal("1.89"), matchResults.get(1).getFilledAmount());
    assertEquals(new BigDecimal("0.00000061508"), matchResults.get(1).getFilledFees());
    assertEquals(new BigDecimal("0.00030754"), matchResults.get(1).getPrice());
    assertEquals("spot-api", matchResults.get(1).getSource().toString());
    assertEquals("htbtc", matchResults.get(1).getSymbol());
    assertEquals("sell-market", matchResults.get(1).getType().toString());
  }

  @Test
  public void testResult_Unexpected() {

    RestApiRequest<List<MatchResult>> restApiRequest =
        impl.getMatchResults(matchResultRequest);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(Errordata);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }

  @Test
  public void testInvalidSymbol() {

    MatchResultRequest matchResultRequest = new MatchResultRequest("?");
    thrown.expect(HuobiApiException.class);
    impl.getMatchResults(matchResultRequest);

  }


}
