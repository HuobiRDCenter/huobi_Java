package com.huobi.client.impl;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;

import com.huobi.client.model.request.TransferMasterRequest;

import com.huobi.client.model.enums.TransferMasterType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestTransferMaster {

  private RestApiRequestImpl impl = null;
  private TransferMasterRequest request = new TransferMasterRequest(
      1234L, "btc", new BigDecimal("0.01"), TransferMasterType.MASTER_POINT_TRANSFER_IN);

  String data = "{\n"
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
  public void testInput() {
    RestApiRequest<Long> restApiRequest = impl.transferBetweenParentAndSub(request);

    assertTrue(restApiRequest.request.url().toString().contains("/v1/subuser/transfer"));
    assertEquals("POST", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));

  }

  @Test
  public void test() {

    RestApiRequest<Long> restApiRequest = impl.transferBetweenParentAndSub(request);
    MockPostQuerier querier = new MockPostQuerier(restApiRequest.request);
    assertEquals(1234L, querier.jsonWrapper.getLong("sub-uid"));
    assertEquals("btc", querier.jsonWrapper.getString("currency"));
    assertEquals(TransferMasterType.MASTER_POINT_TRANSFER_IN.toString(), querier.jsonWrapper.getString("type"));
    assertEquals(new BigDecimal("0.01"), querier.jsonWrapper.getBigDecimal("amount"));
  }

  @Test
  public void testInvalidSymbol() {
    thrown.expect(HuobiApiException.class);
    TransferMasterRequest Wrong = new TransferMasterRequest(
        1234L, "??", new BigDecimal("0.01"), TransferMasterType.MASTER_POINT_TRANSFER_IN);

    impl.transferBetweenParentAndSub(Wrong);

  }

  @Test
  public void testNotNUll() {
    thrown.expect(HuobiApiException.class);

    TransferMasterRequest Wrong1 = new TransferMasterRequest(
        1234L, "btc", null, TransferMasterType.MASTER_POINT_TRANSFER_IN);
    TransferMasterRequest Wrong2 = new TransferMasterRequest(
        1234L, "btc", new BigDecimal("0.01"), null);

    impl.transferBetweenParentAndSub(Wrong1);
    impl.transferBetweenParentAndSub(Wrong2);
  }


  @Test
  public void testResult_Normal() {
    RestApiRequest<Long> restApiRequest =
        impl.transferBetweenParentAndSub(request);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
    long id = restApiRequest.jsonParser.parseJson(jsonWrapper);
    assertEquals(1000L, id);
  }

  @Test
  public void testResult_Unexpected() {
    RestApiRequest<Long> restApiRequest =
        impl.transferBetweenParentAndSub(request);
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(dataError);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    restApiRequest.jsonParser.parseJson(jsonWrapper);
  }
}