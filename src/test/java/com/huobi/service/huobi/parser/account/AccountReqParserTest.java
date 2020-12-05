package com.huobi.service.huobi.parser.account;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.huobi.model.account.AccountReq;

public class AccountReqParserTest {

  public static final String JSON_STRING = "{\"op\":\"req\",\"ts\":1571906005814,\"topic\":\"accounts.list\",\"err-code\":0,\"cid\":\"1571906003202\",\"data\":[{\"id\":10639045,\"type\":\"margin\",\"state\":\"working\",\"list\":[{\"currency\":\"xrp\",\"type\":\"trade\",\"balance\":\"0.00007464812966722\"},{\"currency\":\"usdt\",\"type\":\"trade\",\"balance\":\"0.000000000000000002\"},{\"currency\":\"usdt\",\"type\":\"frozen\",\"balance\":\"0.000000000000000001\"}]},{\"id\":10756443,\"type\":\"margin\",\"state\":\"working\",\"list\":[{\"currency\":\"usdt\",\"type\":\"trade\",\"balance\":\"1\"}]},{\"id\":10639017,\"type\":\"super-margin\",\"state\":\"working\",\"list\":[{\"currency\":\"xrp\",\"type\":\"trade\",\"balance\":\"0.00000000479823106\"},{\"currency\":\"usdt\",\"type\":\"trade\",\"balance\":\"0.000000000000000001\"},{\"currency\":\"usdt\",\"type\":\"frozen\",\"balance\":\"0.000000000000000001\"}]},{\"id\":290082,\"type\":\"spot\",\"state\":\"working\",\"list\":[{\"currency\":\"bch\",\"type\":\"trade\",\"balance\":\"0.115\"},{\"currency\":\"xrp\",\"type\":\"trade\",\"balance\":\"5.00614748\"},{\"currency\":\"usdt\",\"type\":\"trade\",\"balance\":\"121.014779785111216197\"},{\"currency\":\"usdt\",\"type\":\"frozen\",\"balance\":\"3.120900000000000049\"},{\"currency\":\"eth\",\"type\":\"trade\",\"balance\":\"0.00008\"},{\"currency\":\"eos\",\"type\":\"trade\",\"balance\":\"2.269009728376129669\"},{\"currency\":\"ltc\",\"type\":\"trade\",\"balance\":\"0.499\"},{\"currency\":\"trx\",\"type\":\"trade\",\"balance\":\"166.43607485411496\"},{\"currency\":\"ht\",\"type\":\"trade\",\"balance\":\"10.500402196122329742\"},{\"currency\":\"husd\",\"type\":\"trade\",\"balance\":\"0.000000000000000094\"}]}]}";

  private AccountReqParser parser;

  @Before
  public void init() {
    parser = new AccountReqParser();
  }

  @Test
  public void test_parse(){

    AccountReq accountReq = parser.parse(JSON.parseObject(JSON_STRING));
    Assert.assertNotNull(accountReq);
    Assert.assertNotNull(accountReq.getBalanceList());
    Assert.assertTrue(accountReq.getBalanceList().size() == 4);

  }

}
