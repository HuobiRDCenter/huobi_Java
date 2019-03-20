package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.EtfSwapConfig;
import com.huobi.client.model.EtfSwapHistory;
import com.huobi.client.model.enums.EtfStatus;
import com.huobi.client.model.enums.EtfSwapType;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestGetEtfSwapHistory {
  private String data = "{\n"
      + "  \"code\": 200,\n"
      + "  \"data\": [\n"
      + "    {\n"
      + "      \"id\": 112222,\n"
      + "      \"gmt_created\": 1528855872323,\n"
      + "      \"currency\": \"hb10\",\n"
      + "      \"amount\": 11.5,\n"
      + "      \"type\": 1,\n"
      + "      \"status\": 2,\n"
      + "      \"detail\": {\n"
      + "        \"used_currency_list\": [\n"
      + "          {\n"
      + "            \"currency\": \"btc\",\n"
      + "            \"amount\": 0.666\n"
      + "          },\n"
      + "          {\n"
      + "            \"currency\": \"eth\",\n"
      + "            \"amount\": 0.666\n"
      + "          }\n"
      + "        ],\n"
      + "        \"rate\": 0.002,\n"
      + "        \"fee\": 100.11,\n"
      + "        \"point_card_amount\":1.0,\n"
      + "        \"obtain_currency_list\": [\n"
      + "          {\n"
      + "            \"currency\": \"hb10\",\n"
      + "            \"amount\": 1000\n"
      + "          }\n"
      + "        ]\n"
      + "      }\n"
      + "    },\n"
      + "    {\n"
      + "      \"id\": 112223,\n"
      + "      \"gmt_created\": 1528855872323,\n"
      + "      \"currency\": \"hb10\",\n"
      + "      \"amount\": 11.5,\n"
      + "      \"type\": 2,\n"
      + "      \"status\": 1,\n"
      + "      \"detail\": {\n"
      + "        \"used_currency_list\": [\n"
      + "          {\n"
      + "            \"currency\": \"btc\",\n"
      + "            \"amount\": 0.666\n"
      + "          },\n"
      + "          {\n"
      + "            \"currency\": \"eth\",\n"
      + "            \"amount\": 0.666\n"
      + "          }\n"
      + "        ],\n"
      + "        \"rate\": 0.002,\n"
      + "        \"fee\": 100.11,\n"
      + "        \"point_card_amount\":1.0,\n"
      + "        \"obtain_currency_list\": [\n"
      + "          {\n"
      + "            \"currency\": \"hb10\",\n"
      + "            \"amount\": 1000\n"
      + "          }\n"
      + "        ]\n"
      + "      }\n"
      + "    }\n"
      + "  ],\n"
      + "  \"message\": null,\n"
      + "  \"success\": true\n"
      + "}";

  private String dataError = "{\n"
      + "  \"code\": 200,\n"
      + "  \"data\": {\n"
      + "    \"etf_name\": \"hb10\",\n"
      + "    \"etf_status\": 1,\n"
      + "    \"purchase_fee_rate\": 0.0010,\n"
      + "    \"redemption_max_amount\": 5000001,\n"
      + "    \"redemption_min_amount\": 1001,\n"
      + "    \"unit_price\": [\n"
      + "      {\n"
      + "        \"amount\": 0.000126955728465845,\n"
      + "        \"currency\": \"bch\"\n"
      + "      },\n"
      + "      {\n"
      + "        \"amount\": 0.018467942983843364,\n"
      + "        \"currency\": \"eos\"\n"
      + "      },\n"
      + "      {\n"
      + "        \"amount\": 0.425574290019138452,\n"
      + "        \"currency\": \"trx\"\n"
      + "      }\n"
      + "    ]\n"
      + "  },\n"
      + "  \"message\": null,\n"
      + "  \"success\": true\n"
      + "}";

  private RestApiRequestImpl impl = new RestApiRequestImpl("123", "456", new RequestOptions());

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testRequest() {
    RestApiRequest<List<EtfSwapHistory>> restApiRequest = impl.getEtfSwapHistory("hb10", 0, 10);
    assertEquals("GET", restApiRequest.request.method());
    assertTrue(restApiRequest.request.url().toString().contains("/etf/swap/list"));
    assertEquals("hb10", restApiRequest.request.url().queryParameter("etf_name"));
    assertEquals("0", restApiRequest.request.url().queryParameter("offset"));
    assertEquals("10", restApiRequest.request.url().queryParameter("limit"));
  }

  @Test
  public void testResult() {
    RestApiRequest<List<EtfSwapHistory>> restApiRequest = impl.getEtfSwapHistory("hb10", 0, 10);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    List<EtfSwapHistory> etfSwapHistoryList = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(2, etfSwapHistoryList.size());
    assertEquals(1528855872323L, etfSwapHistoryList.get(0).getCreatedTimestamp());
    assertEquals("hb10", etfSwapHistoryList.get(0).getCurrency());
    assertEquals(new BigDecimal("11.5"), etfSwapHistoryList.get(0).getAmount());
    assertEquals(EtfSwapType.ETF_SWAP_IN, etfSwapHistoryList.get(0).getType());
    assertEquals(2, etfSwapHistoryList.get(0).getUsedCurrencyList().size());
    assertEquals("btc", etfSwapHistoryList.get(0).getUsedCurrencyList().get(0).getCurrency());
    assertEquals(new BigDecimal("0.666"), etfSwapHistoryList.get(0).getUsedCurrencyList().get(0).getAmount());
    assertEquals(new BigDecimal("0.002"), etfSwapHistoryList.get(0).getRate());
    assertEquals(new BigDecimal("100.11"), etfSwapHistoryList.get(0).getFee());
    assertEquals(new BigDecimal("1"), etfSwapHistoryList.get(0).getPointCardAmount());
    assertEquals(1, etfSwapHistoryList.get(0).getObtainCurrencyList().size());
    assertEquals("hb10", etfSwapHistoryList.get(0).getObtainCurrencyList().get(0).getCurrency());
    assertEquals(new BigDecimal("1000"), etfSwapHistoryList.get(0).getObtainCurrencyList().get(0).getAmount());
    assertEquals(2, etfSwapHistoryList.get(0).getStatus());
  }

  @Test
  public void testResult_error() {
    RestApiRequest<List<EtfSwapHistory>> restApiRequest = impl.getEtfSwapHistory("hb10", 0, 1);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(dataError);
    thrown.expect(HuobiApiException.class);
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}
