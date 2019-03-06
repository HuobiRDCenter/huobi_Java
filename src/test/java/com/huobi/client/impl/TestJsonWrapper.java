package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import java.math.BigDecimal;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestJsonWrapper {

  private static final String sampleJson1 = "{\n"
      + "\t\"int\": 123,\n"
      + "\t\"long\": 1234567890123456789,\n"
      + "\t\"string\": \"test string\",\n"
      + "\t\"float\": 1.1234567890123456789,\n"
      + "\t\"null\": null,\n"
      + "\t\"T\": true,\n"
      + "\t\"F\": false\n"
      + "}";

  private static final String sampleJson2 = "{\n"
      + "\t\"anotherObject\": {\n"
      + "\t\t\"int\": 456,\n"
      + "\t\t\"long\": 1234567890123456789,\n"
      + "\t\t\"string\": \"test string2\",\n"
      + "\t\t\"float\": 1.1234567890123456789,\n"
      + "\t\t\"null\": null,\n"
      + "\t\t\"T\": true,\n"
      + "\t\t\"F\": false\n"
      + "\t},\n"
      + "\t\"int\": 123,\n"
      + "\t\"long\": 1234567890123456789,\n"
      + "\t\"string\": \"test string\",\n"
      + "\t\"float\": 1.1234567890123456789,\n"
      + "\t\"null\": null,\n"
      + "\t\"T\": true,\n"
      + "\t\"F\": false\n"
      + "}";

  private static final String sampleJson3 = "{\n"
      + "\t\"anotherObject\": {\n"
      + "\t\t\"int\": 456,\n"
      + "\t\t\"string\": \"test string2\",\n"
      + "\t\t\"arrayInObject\": [\n"
      + "\t\t\t1,\n"
      + "\t\t\t2,\n"
      + "\t\t\t3\n"
      + "\t\t]\n"
      + "\t},\n"
      + "\t\"testarray\": [\n"
      + "\t\t\"1\",\n"
      + "\t\t\"2\",\n"
      + "\t\t\"321\",\n"
      + "\t\t321,\n"
      + "\t\t654, [0.1,\n"
      + "\t\t\t0.2,\n"
      + "\t\t\t0.3\n"
      + "\t\t]\n"
      + "\t],\n"
      + "\t\"int\": 123,\n"
      + "\t\"string\": \"test string\"\n"
      + "}";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testParseInvalidJson() {
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Fail to parse json");
    JsonWrapper.parseFromString("{\"abc\":\"sdf\"");
  }

  @Test
  public void testParseSimpleJson() {
    JsonWrapper.parseFromString("{\"key\":\"value\"}");
  }

  @Test
  public void testGetValues() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson1);
    assertEquals(123, jsonWrapper.getInteger("int"));
    assertEquals(1234567890123456789L, jsonWrapper.getLong("long"));
    assertEquals("test string", jsonWrapper.getString("string"));
    assertEquals(new BigDecimal("1.1234567890123456789"), jsonWrapper.getBigDecimal("float"));
  }

  @Test
  public void testGetNonExistInt() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson1);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    jsonWrapper.getInteger("int1");
  }

  @Test
  public void testGetNonExistLong() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson1);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    jsonWrapper.getLong("long1");
  }

  @Test
  public void testGetNonExistString() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson1);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    jsonWrapper.getString("string1");
  }

  @Test
  public void testGetNonExistBigDecimal() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson1);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    jsonWrapper.getBigDecimal("float1");
  }

  @Test
  public void testGetNonExistArray() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson1);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    jsonWrapper.getJsonArray("array");
  }

  @Test
  public void testGetNonExistObject() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson1);
    thrown.expect(HuobiApiException.class);
    thrown.expectMessage("Get json item field");
    jsonWrapper.getJsonObject("object");
  }

  @Test
  public void testGetObject() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson2);
    JsonWrapper anotherObject = jsonWrapper.getJsonObject("anotherObject");
    assertNotEquals(anotherObject, null);
    assertEquals(456, anotherObject.getInteger("int"));
    assertEquals(123, jsonWrapper.getInteger("int"));
    jsonWrapper.getJsonObject("anotherObject", (item) -> {
      assertEquals(456, item.getInteger("int"));
      assertEquals("test string2", item.getString("string"));
    });
  }

  @Test
  public void testGetObjectAndGetNonExistString() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson2);
    jsonWrapper.getJsonObject("anotherObject", (item) -> {
      assertEquals(456, item.getInteger("int"));
      assertEquals("test string2", item.getString("string"));
      thrown.expect(HuobiApiException.class);
      thrown.expectMessage("Get json item field");
      item.getString("string2");
    });
  }

//  @Test
//  public void testGetString_IncorrectType() {
//    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson1);
//    thrown.expect(HuobiApiException.class);
//    jsonWrapper.getString("int");
//  }

  @Test
  public void testGetLong_IncorrectType_FromString() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson1);
    thrown.expect(HuobiApiException.class);
    jsonWrapper.getLong("string");
  }

//  @Test
//  public void testGetLong_IncorrectType_FromFloat() {
//    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson1);
//    thrown.expect(HuobiApiException.class);
//    long l = jsonWrapper.getLong("float1");
//    long l1 = jsonWrapper.getLong("int");
//    int a = 0;
//  }

  @Test
  public void testArray_getLong() {
    JsonWrapper jsonWrapper = JsonWrapper.parseFromString(sampleJson3)
        .getJsonObject("anotherObject");
    JsonWrapperArray jsonWrapperArray = jsonWrapper.getJsonArray("arrayInObject");
//    assertEquals(1, jsonWrapperArray.getLongAt(0));
//    assertEquals(2, jsonWrapperArray.getLongAt(1));
  }
}
