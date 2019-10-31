package com.huobi.client.model.enums;


import com.huobi.client.impl.utils.EnumLookup;

public enum  StopOrderOperator {
  /**
   * GTE,greater than and equal (>=) ,LTE  less than and equal (<=)
   */
  GTE("gte","greater than and equal (>=)"),
  LTE("lte","less than and equal (<=)");

  private String operator;

  private String desc;

  private static final EnumLookup<StopOrderOperator> lookup = new EnumLookup<>(StopOrderOperator.class);

  StopOrderOperator(String operator, String desc) {
    this.operator = operator;
    this.desc = desc;
  }

  public String getOperator() {
    return operator;
  }

  public String getDesc() {
    return desc;
  }

  public static StopOrderOperator find(String operator) {
    for (StopOrderOperator op : StopOrderOperator.values()) {
      if (op.getOperator().equals(operator)) {
        return op;
      }
    }
    return null;
  }


}
