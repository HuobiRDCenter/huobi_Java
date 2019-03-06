package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.EtfSwapConfig;
import com.huobi.client.model.enums.EtfStatus;
import java.math.BigDecimal;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TestGetEtfSwapConfig {

  private String data = "{\n"
      + "  \"code\": 200,\n"
      + "  \"data\": {\n"
      + "    \"etf_name\": \"hb10\",\n"
      + "    \"etf_status\": 1,\n"
      + "    \"purchase_fee_rate\": 0.0010,\n"
      + "    \"purchase_max_amount\": 5000000,\n"
      + "    \"purchase_min_amount\": 1000,\n"
      + "    \"redemption_fee_rate\": 0.0020,\n"
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

  private RestApiRequestImpl impl = new RestApiRequestImpl("", "", new RequestOptions());

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testRequest() {
    RestApiRequest<EtfSwapConfig> restApiRequest = impl.getEtfSwapConfig("hb10");
    assertEquals("GET", restApiRequest.request.method());
    assertTrue(restApiRequest.request.url().toString().contains("/etf/swap/config"));
    assertEquals("hb10", restApiRequest.request.url().queryParameter("etf_name"));
  }

  @Test
  public void testResult() {
    RestApiRequest<EtfSwapConfig> restApiRequest = impl.getEtfSwapConfig("hb10");
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    EtfSwapConfig etfSwapConfig = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(1000, etfSwapConfig.getPurchaseMinAmount());
    assertEquals(5000000, etfSwapConfig.getPurchaseMaxAmount());
    assertEquals(1001, etfSwapConfig.getRedemptionMinAmount());
    assertEquals(5000001, etfSwapConfig.getRedemptionMaxAmount());
    assertEquals(new BigDecimal("0.001"), etfSwapConfig.getPurchaseFeeRate());
    assertEquals(new BigDecimal("0.002"), etfSwapConfig.getRedemptionFeeRate());
    assertEquals(EtfStatus.NORMAL, etfSwapConfig.getStatus());
    assertEquals(3, etfSwapConfig.getUnitPriceList().size());
    assertEquals(new BigDecimal("0.018467942983843364"),
        etfSwapConfig.getUnitPriceList().get(1).getAmount());
    assertEquals("eos", etfSwapConfig.getUnitPriceList().get(1).getCurrency());
  }

  @Test
  public void testResult_error() {
    RestApiRequest<EtfSwapConfig> restApiRequest = impl.getEtfSwapConfig("hb10");
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(dataError);
    thrown.expect(HuobiApiException.class);
    EtfSwapConfig etfSwapConfig = restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}
