package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  The aggregation depth type.
 */
@Getter
@AllArgsConstructor
public enum DepthStepEnum {

  /**
   * step0,step1,step2,step3,step4,step5
   */
  STEP0("step0"),
  STEP1("step1"),
  STEP2("step2"),
  STEP3("step3"),
  STEP4("step4"),
  STEP5("step5"),
  ;

  private final String step;

}
