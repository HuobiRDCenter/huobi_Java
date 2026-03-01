package com.huobi.service.huobi.parser.wallet;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.model.wallet.DepositWithdraw;

@RunWith(PowerMockRunner.class)
public class DepositWithdrawParserTest {

  public static final String JSON_STRING = "[{\"id\":15227226,\"type\":\"withdraw\",\"currency\":\"usdt\",\"chain\":\"usdt\",\"tx-hash\":\"\",\"amount\":205.000000000000000000,\"address\":\"3LaJ48D1sz9mT4PtLF1Fs7TVS2efMCpTew\",\"address-tag\":\"\",\"fee\":5.000000000000000000,\"state\":\"canceled\",\"created-at\":1568108189185,\"updated-at\":1568108199579}]";

  private DepositWithdrawParser parser;

  @Before
  public void init() {
    parser = new DepositWithdrawParser();
  }

  @Test
  public void test_parse(){

    List<DepositWithdraw> depositWithdrawList = parser.parseArray(JSON.parseArray(JSON_STRING));
    Assert.assertNotNull(depositWithdrawList);
    Assert.assertTrue(depositWithdrawList.size() == 1);
  }
}
