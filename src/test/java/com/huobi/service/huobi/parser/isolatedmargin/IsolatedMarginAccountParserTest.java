package com.huobi.service.huobi.parser.isolatedmargin;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.model.isolatedmargin.IsolatedMarginAccount;

@RunWith(PowerMockRunner.class)
public class IsolatedMarginAccountParserTest {

  public static final String JSON_STRING = "[{\"id\":10756443,\"type\":\"margin\",\"state\":\"working\",\"symbol\":\"ltcusdt\",\"fl-price\":\"0\",\"fl-type\":\"safe\",\"risk-rate\":\"10\",\"list\":[{\"currency\":\"ltc\",\"type\":\"trade\",\"balance\":\"0\"},{\"currency\":\"ltc\",\"type\":\"frozen\",\"balance\":\"0\"},{\"currency\":\"ltc\",\"type\":\"loan\",\"balance\":\"0\"},{\"currency\":\"ltc\",\"type\":\"interest\",\"balance\":\"0\"},{\"currency\":\"usdt\",\"type\":\"trade\",\"balance\":\"1\"},{\"currency\":\"usdt\",\"type\":\"frozen\",\"balance\":\"0\"},{\"currency\":\"usdt\",\"type\":\"loan\",\"balance\":\"0\"},{\"currency\":\"usdt\",\"type\":\"interest\",\"balance\":\"0\"}]},{\"id\":10639045,\"type\":\"margin\",\"state\":\"working\",\"symbol\":\"xrpusdt\",\"fl-price\":\"0\",\"fl-type\":\"safe\",\"risk-rate\":\"1.5000\",\"list\":[{\"currency\":\"xrp\",\"type\":\"trade\",\"balance\":\"0.00007464812966722\"},{\"currency\":\"xrp\",\"type\":\"frozen\",\"balance\":\"0\"},{\"currency\":\"xrp\",\"type\":\"loan\",\"balance\":\"0\"},{\"currency\":\"xrp\",\"type\":\"interest\",\"balance\":\"0\"},{\"currency\":\"usdt\",\"type\":\"trade\",\"balance\":\"150.000000000000000002\"},{\"currency\":\"usdt\",\"type\":\"frozen\",\"balance\":\"0.000000000000000001\"},{\"currency\":\"usdt\",\"type\":\"loan\",\"balance\":\"-100\"},{\"currency\":\"usdt\",\"type\":\"interest\",\"balance\":\"0\"}]}]";

  private IsolatedMarginAccountParser parser;

  @Before
  public void init() {
    parser = new IsolatedMarginAccountParser();
  }

  @Test
  public void test_parse(){

    List<IsolatedMarginAccount> accountList = parser.parseArray(JSON.parseArray(JSON_STRING));
    Assert.assertNotNull(accountList);
    Assert.assertTrue(accountList.size() == 2);
  }
}
