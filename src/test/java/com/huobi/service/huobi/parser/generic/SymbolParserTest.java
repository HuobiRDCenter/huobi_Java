package com.huobi.service.huobi.parser.generic;

import java.util.List;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.model.generic.Symbol;

@RunWith(PowerMockRunner.class)
public class SymbolParserTest {

  public static final String JSON_STRING = "[{\n"
      + "\t\"base-currency\": \"topc\",\n"
      + "\t\"quote-currency\": \"btc\",\n"
      + "\t\"price-precision\": 10,\n"
      + "\t\"amount-precision\": 2,\n"
      + "\t\"symbol-partition\": \"innovation\",\n"
      + "\t\"symbol\": \"topcbtc\",\n"
      + "\t\"state\": \"online\",\n"
      + "\t\"value-precision\": 8,\n"
      + "\t\"min-order-amt\": 1,\n"
      + "\t\"max-order-amt\": 10000000,\n"
      + "\t\"min-order-value\": 0.0001,\n"
      + "\t\"limit-order-max-order-amt\": 0.0001\n"
      + "}]";

  private SymbolParser parser;

  @Before
  public void init() {
    parser = new SymbolParser();
  }

  @Test
  public void test_parse(){

    List<Symbol> symbolList = parser.parseArray(JSON.parseArray(JSON_STRING));
    Assert.assertNotNull(symbolList);
    Assert.assertTrue(symbolList.size() == 1);
  }

}
