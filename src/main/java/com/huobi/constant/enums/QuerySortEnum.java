package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuerySortEnum {
  ASC("asc"),
  DESC("desc");

  private final String sort;

}
