package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.model.BatchCancelResultV1;
import com.huobi.client.model.request.BatchCancelRequest;

import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TestCancelOrder {

  private RestApiRequestImpl impl = null;
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void Initialize() {
    impl = new RestApiRequestImpl("12345", "67890", new RequestOptions());
  }

  @Test
  public void testCancelOder() {
    RestApiRequest<Void> restApiRequest = impl.cancelOrder("htbtc", 12345L);
    String url = String.format("/v1/order/orders/%d/submitcancel", 12345L);
    assertTrue(restApiRequest.request.url().toString().contains(url));
    assertEquals("POST", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));

  }

  @Test
  public void testCancelOrders() {
    List<Long> longList = new LinkedList<>();
    longList.add(12443L);
    longList.add(2344L);

    BatchCancelRequest request = new BatchCancelRequest();
    request.setSymbol("htbtc");
    request.setOrderIds(longList);


    RestApiRequest<BatchCancelResultV1> restApiRequest = impl.cancelOrders(request);

    assertTrue(restApiRequest.request.url().toString().contains("/v1/order/orders/batchcancel"));
    assertEquals("POST", restApiRequest.request.method());
    assertNotNull(restApiRequest.request.url().queryParameter("Signature"));
    MockPostQuerier querier = new MockPostQuerier(restApiRequest.request);
    assertEquals("12443", querier.jsonWrapper.getJsonArray("order-ids").getStringAt(0));
    assertEquals("2344", querier.jsonWrapper.getJsonArray("order-ids").getStringAt(1));
  }

  @Test
  public void testInvalidSymbolInCancelOrder() {
    thrown.expect(HuobiApiException.class);
    impl.cancelOrder("$$$$", 100L);

  }

  @Test
  public void testInvalidSymbolInCancelOrders() {
    thrown.expect(HuobiApiException.class);
    List<Long> longList = new LinkedList<>();
    longList.add(12443L);
    longList.add(2344L);

    BatchCancelRequest request = new BatchCancelRequest();
    request.setSymbol("?");
    request.setOrderIds(longList);

    impl.cancelOrders(request);

  }

  @Test
  public void testNullOrderIdList() {
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("both orderIds and clientOrderIds not null");
    BatchCancelRequest request = new BatchCancelRequest();
    request.setSymbol("htbtc");
    impl.cancelOrders(request);
  }

  @Test
  public void testHugeOrderIdList() {
    thrown.expect(HuobiApiException.class);
    List<Long> longList = new LinkedList<>();
    for (int i = 0; i <= 50; i++) {
      longList.add((long) i);
    }
    thrown.expectMessage("is out of bound");

    BatchCancelRequest request = new BatchCancelRequest();
    request.setSymbol("btcusdt");
    request.setOrderIds(longList);
    impl.cancelOrders(request);
  }

  @Test
  public void testEmptyOrderIdList() {
    thrown.expect(HuobiApiException.class);
    List<Long> longList = new LinkedList<>();
    thrown.expectMessage("both orderIds and clientOrderIds not null");

    BatchCancelRequest request = new BatchCancelRequest();
    request.setSymbol("btcusdt");
    request.setOrderIds(longList);
    impl.cancelOrders(request);
  }
}
