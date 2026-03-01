package com.huobi.service.huobi.parser.account;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.huobi.model.account.Point;


public class PointParserTest {

  public static final String JSON_STRING ="{\n"
    + "        \"accountId\": \"14403739\",\n"
    + "        \"groupIds\": [\n"
    + "            {\n"
    + "                \"groupId\": 26,\n"
    + "                \"expiryDate\": 1594396800000,\n"
    + "                \"remainAmt\": \"0.3\"\n"
    + "            }\n"
    + "        ],\n"
    + "        \"acctBalance\": \"0.30000000\",\n"
    + "        \"accountStatus\": \"working\"\n"
    + "    }" ;

  private PointParser parser;

  @Before
  public void init() {
    parser = new PointParser();
  }

  @Test
  public void test_parse(){
    Point point = parser.parse(JSON.parseObject(JSON_STRING));
    Assert.assertNotNull(point);
    Assert.assertNotNull(point.getGroupIds());
    Assert.assertTrue(point.getGroupIds().size() == 1);
  }

}
