package com.huobi.service.huobi.parser.generic;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.model.generic.CurrencyChain;

@RunWith(PowerMockRunner.class)
public class CurrencyChainParserTest {

  public static final String JSON_STRING = "[{\"currency\":\"usdt\",\"chains\":[{\"chain\":\"trc20usdt\",\"numOfConfirmations\":1,\"numOfFastConfirmations\":1,\"depositStatus\":\"allowed\",\"minDepositAmt\":\"1\",\"withdrawStatus\":\"allowed\",\"minWithdrawAmt\":\"2\",\"withdrawPrecision\":4,\"maxWithdrawAmt\":\"100000.00000000\",\"withdrawQuotaPerDay\":\"100000.00000000\",\"withdrawQuotaPerYear\":\"100000000.00000000\",\"withdrawQuotaTotal\":\"1000000000.00000000\",\"withdrawFeeType\":\"fixed\",\"transactFeeWithdraw\":\"0.00000000\"},{\"chain\":\"usdt\",\"numOfConfirmations\":2,\"numOfFastConfirmations\":1,\"depositStatus\":\"allowed\",\"minDepositAmt\":\"100\",\"withdrawStatus\":\"allowed\",\"minWithdrawAmt\":\"200\",\"withdrawPrecision\":4,\"maxWithdrawAmt\":\"600000.00000000\",\"withdrawQuotaPerDay\":\"600000.00000000\",\"withdrawQuotaPerYear\":\"100000000.00000000\",\"withdrawQuotaTotal\":\"1000000000.00000000\",\"withdrawFeeType\":\"fixed\",\"transactFeeWithdraw\":\"5.00000000\"},{\"chain\":\"usdterc20\",\"numOfConfirmations\":12,\"numOfFastConfirmations\":12,\"depositStatus\":\"allowed\",\"minDepositAmt\":\"1\",\"withdrawStatus\":\"allowed\",\"minWithdrawAmt\":\"2\",\"withdrawPrecision\":6,\"maxWithdrawAmt\":\"1000000.00000000\",\"withdrawQuotaPerDay\":\"1000000.00000000\",\"withdrawQuotaPerYear\":\"10000000000.00000000\",\"withdrawQuotaTotal\":\"1000000000000.00000000\",\"withdrawFeeType\":\"fixed\",\"transactFeeWithdraw\":\"1.00000000\"}],\"instStatus\":\"normal\"}]";

  private CurrencyChainParser parser;

  @Before
  public void init() {
    parser = new CurrencyChainParser();
  }

  @Test
  public void test_parse(){

    List<CurrencyChain> currencyChainList = parser.parseArray(JSON.parseArray(JSON_STRING));
    Assert.assertNotNull(currencyChainList);
    Assert.assertTrue(currencyChainList.size() == 1);
    Assert.assertNotNull(currencyChainList.get(0).getChains());
    Assert.assertTrue(currencyChainList.get(0).getChains().size() == 3);
  }
}
