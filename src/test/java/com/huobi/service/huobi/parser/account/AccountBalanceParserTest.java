package com.huobi.service.huobi.parser.account;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.model.account.AccountBalance;

@RunWith(PowerMockRunner.class)
public class AccountBalanceParserTest {

  public static final String JSON_OBJECT_STRING = "{\n"
      + "\t\t\"id\": 290082,\n"
      + "\t\t\"type\": \"spot\",\n"
      + "\t\t\"state\": \"working\",\n"
      + "\t\t\"list\": [{\n"
      + "\t\t\t\"currency\": \"lun\",\n"
      + "\t\t\t\"type\": \"trade\",\n"
      + "\t\t\t\"balance\": \"0\"\n"
      + "\t\t}, {\n"
      + "\t\t\t\"currency\": \"lun\",\n"
      + "\t\t\t\"type\": \"frozen\",\n"
      + "\t\t\t\"balance\": \"0\"\n"
      + "\t\t} {\n"
      + "\t\t\t\"currency\": \"phx\",\n"
      + "\t\t\t\"type\": \"frozen\",\n"
      + "\t\t\t\"balance\": \"0\"\n"
      + "\t\t}]\n"
      + "\t}";

  public static final String JSON_ARRAY_STRING = "["+JSON_OBJECT_STRING+"]";

  private AccountBalanceParser parser;

  @Before
  public void init() {
    parser = new AccountBalanceParser();
  }

  @Test
  public void test_parse(){

    AccountBalance balance = parser.parse(JSON.parseObject(JSON_OBJECT_STRING));
    Assert.assertNotNull(balance);
    Assert.assertNotNull(balance.getList());
    Assert.assertTrue(balance.getList().size() == 3);

  }

  @Test
  public void test_parse_array(){
    List<AccountBalance> list = parser.parseArray(JSON.parseArray(JSON_ARRAY_STRING));
    Assert.assertNotNull(list);
    Assert.assertTrue(list.size() == 1);
  }

}
