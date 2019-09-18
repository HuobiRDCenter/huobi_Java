package com.huobi.client.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.huobi.client.impl.utils.EnumLookup;

@AllArgsConstructor
@Getter
public enum  StopOrderOperator {
  /**
   * GTE,greater than and equal (>=) ,LTE  less than and equal (<=)
   */
  GTE("gte","greater than and equal (>=)"),
  LTE("lte","less than and equal (<=)");

  private String operator;

  private String desc;

  private static final EnumLookup<StopOrderOperator> lookup = new EnumLookup<>(StopOrderOperator.class);

  public static StopOrderOperator find(String operator) {
    for (StopOrderOperator op : StopOrderOperator.values()) {
      if (op.getOperator().equals(operator)) {
        return op;
      }
    }
    return null;
  }


}
