package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;

import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.EnumLookup;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestEnumLookup {

  enum MockEnum {
    A("a"),
    B("b");
    private final String code;

    MockEnum(String code) {
      this.code = code;
    }

    @Override
    public String toString() {
      return code;
    }

    private static final EnumLookup<MockEnum> lookup = new EnumLookup<>(MockEnum.class);

    public static MockEnum lookup(String name) {
      return lookup.lookup(name);
    }
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testToString() {
    assertEquals("a", MockEnum.A.toString());
  }

  @Test
  public void testFromString() {
    assertEquals(MockEnum.A, MockEnum.lookup("a"));
  }

  @Test
  public void testError() {
//    thrown.expect(HuobiApiException.class);
//    thrown.expectMessage("Cannot found test in Enum");
//    MockEnum.lookup("test");
  }
}
