package com.huobi.service.huobi.parser.wallet;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.model.wallet.WithdrawQuota;

@RunWith(PowerMockRunner.class)
public class WithdrawQuotaParserTest {

  public static final String JSON_STRING = "{\"currency\":\"usdt\",\"chains\":[{\"chain\":\"trc20usdt\",\"maxWithdrawAmt\":\"100000.00000000\",\"withdrawQuotaPerDay\":\"100000.00000000\",\"remainWithdrawQuotaPerDay\":\"100000.000000000000000000\",\"withdrawQuotaPerYear\":\"100000000.00000000\",\"remainWithdrawQuotaPerYear\":\"100000000.000000000000000000\",\"withdrawQuotaTotal\":\"1000000000.00000000\",\"remainWithdrawQuotaTotal\":\"1000000000.000000000000000000\"},{\"chain\":\"usdt\",\"maxWithdrawAmt\":\"600000.00000000\",\"withdrawQuotaPerDay\":\"600000.00000000\",\"remainWithdrawQuotaPerDay\":\"600000.000000000000000000\",\"withdrawQuotaPerYear\":\"100000000.00000000\",\"remainWithdrawQuotaPerYear\":\"100000000.000000000000000000\",\"withdrawQuotaTotal\":\"1000000000.00000000\",\"remainWithdrawQuotaTotal\":\"1000000000.000000000000000000\"},{\"chain\":\"usdterc20\",\"maxWithdrawAmt\":\"1000000.00000000\",\"withdrawQuotaPerDay\":\"1000000.00000000\",\"remainWithdrawQuotaPerDay\":\"1000000.000000000000000000\",\"withdrawQuotaPerYear\":\"10000000000.00000000\",\"remainWithdrawQuotaPerYear\":\"9999999990.000000000000000000\",\"withdrawQuotaTotal\":\"1000000000000.00000000\",\"remainWithdrawQuotaTotal\":\"999999999990.000000000000000000\"}]}";

  private WithdrawQuotaParser parser;

  @Before
  public void init() {
    parser = new WithdrawQuotaParser();
  }

  @Test
  public void test_parse(){

    WithdrawQuota withdrawQuota = parser.parse(JSON.parseObject(JSON_STRING));
    Assert.assertNotNull(withdrawQuota);
    Assert.assertNotNull(withdrawQuota.getChains().size());
    Assert.assertTrue(withdrawQuota.getChains().size() == 3 );
  }
}
