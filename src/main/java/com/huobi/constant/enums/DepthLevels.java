package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  DepthLevels {

  LEVEL_5(5),
  LEVEL_10(10),
  LEVEL_20(20),
  LEVEL_150(150),
  ;

  private final int level;

}
