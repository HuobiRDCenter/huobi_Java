package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  The aggregation depth type.
 */
@Getter
@AllArgsConstructor
public enum DepthSizeEnum {

  /**
   * step0,step1,step2,step3,step4,step5
   */
  SIZE_5(5),
  SIZE_10(10),
  SIZE_20(20),
  ;

  private final Integer size;

}
