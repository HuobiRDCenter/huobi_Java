package com.huobi.service.huobi.parser.account;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.model.account.AccountChangeEvent;

@RunWith(PowerMockRunner.class)
public class AccountChangeEventParserTest {

  public static final String JSON_ARRAY_STRING = "{\"op\":\"notify\",\"ts\":1571905790451,\"topic\":\"accounts\",\"data\":{\"event\":\"order.place\",\"list\":[{\"account-id\":290082,\"currency\":\"usdt\",\"type\":\"trade\",\"balance\":\"121.014779785111216197\"}]}}";

  private AccountChangeEventParser parser;

  @Before
  public void init() {
    parser = new AccountChangeEventParser();
  }

  @Test
  public void test_parse(){

    AccountChangeEvent event = parser.parse(JSON.parseObject(JSON_ARRAY_STRING));
    Assert.assertNotNull(event);
    Assert.assertNotNull(event.getList());
    Assert.assertTrue(event.getList().size() == 1);

  }

}
