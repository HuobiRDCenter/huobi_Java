package com.huobi.service.huobi.parser.isolatedmargin;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.model.isolatedmargin.IsolatedMarginLoadOrder;

@RunWith(PowerMockRunner.class)
public class IsolatedMarginLoadOrderParserTest {

  public static final String JSON_STRING = "[{\"currency\":\"usdt\",\"symbol\":\"xrpusdt\",\"loan-amount\":\"100.000000000000000000\",\"interest-amount\":\"0.004200000000000000\",\"updated-at\":1571908613806,\"user-id\":143564,\"paid-coin\":\"0.000000000000000000\",\"interest-balance\":\"0.000000000000000000\",\"created-at\":1571908608528,\"account-id\":10639045,\"accrued-at\":1571908608528,\"deduct-amount\":\"0.001327391675357921\",\"paid-point\":\"0.000000000000000000\",\"interest-rate\":\"0.000042000000000000\",\"deduct-rate\":\"1\",\"loan-balance\":\"0.000000000000000000\",\"deduct-currency\":\"ht\",\"id\":4233646,\"state\":\"cleared\"}]";

  private IsolatedMarginLoadOrderParser parser;

  @Before
  public void init() {
    parser = new IsolatedMarginLoadOrderParser();
  }

  @Test
  public void test_parse(){

    List<IsolatedMarginLoadOrder> orderList = parser.parseArray(JSON.parseArray(JSON_STRING));
    Assert.assertNotNull(orderList);
    Assert.assertTrue(orderList.size() == 1);
  }
}
