package com.huobi.service.huobi.parser.wallet;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.model.wallet.DepositAddress;

@RunWith(PowerMockRunner.class)
public class DepositAddressParserTest {

  public static final String JSON_STRING = "[{\"currency\":\"usdt\",\"address\":\"0x138d709030b4e096044d371a27efc5c562889b9b\",\"addressTag\":\"\",\"chain\":\"usdterc20\"},{\"currency\":\"usdt\",\"address\":\"TQbU4uUzzZCjjMPCoC1RGY8oP5h7fFuPV7\",\"addressTag\":\"\",\"chain\":\"trc20usdt\"},{\"currency\":\"usdt\",\"address\":\"1EBYYtscNVTYyeq1RzneRZ8uqxiSz3xRLH\",\"addressTag\":\"\",\"chain\":\"usdt\"}]";

  private DepositAddressParser parser;

  @Before
  public void init() {
    parser = new DepositAddressParser();
  }

  @Test
  public void test_parse(){

    List<DepositAddress> addressList = parser.parseArray(JSON.parseArray(JSON_STRING));
    Assert.assertNotNull(addressList);
    Assert.assertTrue(addressList.size() == 3);
  }
}
