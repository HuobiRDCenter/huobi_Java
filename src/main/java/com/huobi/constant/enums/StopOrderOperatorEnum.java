package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum StopOrderOperatorEnum {
  /**
   * GTE,greater than and equal (>=) ,LTE  less than and equal (<=)
   */
  GTE("gte","greater than and equal (>=)"),
  LTE("lte","less than and equal (<=)");

  private String operator;

  private String desc;

  public static StopOrderOperatorEnum find(String operator) {
    for (StopOrderOperatorEnum op : StopOrderOperatorEnum.values()) {
      if (op.getOperator().equals(operator)) {
        return op;
      }
    }
    return null;
  }


}
