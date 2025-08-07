package com.huobi.service.huobi.parser.crossmargin;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.model.crossmargin.CrossMarginAccount;


@RunWith(PowerMockRunner.class)
public class CrossMarginAccountParserTest {

  public static final String JSON_STRING = "{\"id\":10639017,\"type\":\"cross-margin\",\"state\":\"working\",\"risk-rate\":\"2\",\"acct-balance-sum\":\"50.0000000012927394141852\",\"debt-balance-sum\":\"0\",\"list\":[{\"currency\":\"xrp\",\"type\":\"trade\",\"balance\":\"0.00000000479823106\"},{\"currency\":\"xrp\",\"type\":\"frozen\",\"balance\":\"0\"},{\"currency\":\"usdt\",\"type\":\"trade\",\"balance\":\"50.000000000000000001\"},{\"currency\":\"usdt\",\"type\":\"frozen\",\"balance\":\"0.000000000000000001\"},{\"currency\":\"usdt\",\"type\":\"loan\",\"balance\":\"0\"},{\"currency\":\"usdt\",\"type\":\"interest\",\"balance\":\"0\"},{\"currency\":\"btc\",\"type\":\"loan-available\",\"balance\":\"0.01343454\"},{\"currency\":\"bch\",\"type\":\"loan-available\",\"balance\":\"0.47657627\"},{\"currency\":\"xrp\",\"type\":\"loan-available\",\"balance\":\"0\"},{\"currency\":\"eth\",\"type\":\"loan-available\",\"balance\":\"0.62142679\"},{\"currency\":\"eos\",\"type\":\"loan-available\",\"balance\":\"37.000037\"},{\"currency\":\"usdt\",\"type\":\"loan-available\",\"balance\":\"100\"},{\"currency\":\"ltc\",\"type\":\"loan-available\",\"balance\":\"2.02922077\"},{\"currency\":\"btc\",\"type\":\"transfer-out-available\",\"balance\":\"-1\"},{\"currency\":\"bch\",\"type\":\"transfer-out-available\",\"balance\":\"-1\"},{\"currency\":\"xrp\",\"type\":\"transfer-out-available\",\"balance\":\"-1\"},{\"currency\":\"usdt\",\"type\":\"transfer-out-available\",\"balance\":\"-1\"},{\"currency\":\"eth\",\"type\":\"transfer-out-available\",\"balance\":\"-1\"},{\"currency\":\"eos\",\"type\":\"transfer-out-available\",\"balance\":\"-1\"},{\"currency\":\"ltc\",\"type\":\"transfer-out-available\",\"balance\":\"-1\"}]}";

  private CrossMarginAccountParser parser;

  @Before
  public void init() {
    parser = new CrossMarginAccountParser();
  }

  @Test
  public void test_parse(){

    CrossMarginAccount crossMarginAccount = parser.parse(JSON.parseObject(JSON_STRING));
    Assert.assertNotNull(crossMarginAccount);
    Assert.assertNotNull(crossMarginAccount.getBalanceList());
    Assert.assertTrue(crossMarginAccount.getBalanceList().size() == 20);
  }
}
