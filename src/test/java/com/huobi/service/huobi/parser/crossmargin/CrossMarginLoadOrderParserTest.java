package com.huobi.service.huobi.parser.crossmargin;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.huobi.model.crossmargin.CrossMarginLoadOrder;

public class CrossMarginLoadOrderParserTest {

  public static final String JSON_STRING = "[{\"id\":6266,\"user-id\":143564,\"account-id\":10639017,\"currency\":\"usdt\",\"loan-amount\":\"100\",\"loan-balance\":\"0\",\"interest-amount\":\"0.00416667\",\"interest-balance\":\"0\",\"created-at\":1571744150790,\"accrued-at\":1571744150903,\"state\":\"cleared\",\"filled-points\":\"0\",\"filled-ht\":\"0.001229650287737937\"}]";

  private CrossMarginLoadOrderParser parser;

  @Before
  public void init() {
    parser = new CrossMarginLoadOrderParser();
  }

  @Test
  public void test_parse(){

    List<CrossMarginLoadOrder> orderList = parser.parseArray(JSON.parseArray(JSON_STRING));
    Assert.assertNotNull(orderList);
    Assert.assertTrue(orderList.size() == 1);
  }

}
