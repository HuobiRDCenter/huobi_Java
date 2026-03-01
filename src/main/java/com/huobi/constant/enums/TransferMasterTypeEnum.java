package com.huobi.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransferMasterTypeEnum {


  MASTER_TRANSFER_IN("master-transfer-in"),
  MASTER_TRANSFER_OUT("master-transfer-out"),
  MASTER_POINT_TRANSFER_IN("master-point-transfer-in"),
  MASTER_POINT_TRANSFER_OUT("master-point-transfer-out");

  private final String code;

}
