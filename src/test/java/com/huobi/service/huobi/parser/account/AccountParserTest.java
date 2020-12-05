package com.huobi.service.huobi.parser.account;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.model.account.Account;

@RunWith(PowerMockRunner.class)
public class AccountParserTest {

  public static final String JSON_ARRAY_STRING = "[{\n"
      + "\t\"id\": 290082,\n"
      + "\t\"type\": \"spot\",\n"
      + "\t\"subtype\": \"\",\n"
      + "\t\"state\": \"working\"\n"
      + "}, {\n"
      + "\t\"id\": 10756443,\n"
      + "\t\"type\": \"margin\",\n"
      + "\t\"subtype\": \"ltcusdt\",\n"
      + "\t\"state\": \"working\"\n"
      + "}, {\n"
      + "\t\"id\": 10639045,\n"
      + "\t\"type\": \"margin\",\n"
      + "\t\"subtype\": \"xrpusdt\",\n"
      + "\t\"state\": \"working\"\n"
      + "}, {\n"
      + "\t\"id\": 8262647,\n"
      + "\t\"type\": \"otc\",\n"
      + "\t\"subtype\": \"\",\n"
      + "\t\"state\": \"working\"\n"
      + "}, {\n"
      + "\t\"id\": 10639017,\n"
      + "\t\"type\": \"super-margin\",\n"
      + "\t\"subtype\": \"\",\n"
      + "\t\"state\": \"working\"\n"
      + "}]";

  private AccountParser parser;

  @Before
  public void init() {
    parser = new AccountParser();
  }

  @Test
  public void test_array_parse(){

    List<Account> accountList = parser.parseArray(JSON.parseArray(JSON_ARRAY_STRING));
    Assert.assertNotNull(accountList);
    Assert.assertTrue(accountList.size() == 5);

  }

}
