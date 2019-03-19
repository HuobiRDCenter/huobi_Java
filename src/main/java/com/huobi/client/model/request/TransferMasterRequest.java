package com.huobi.client.model.request;

import com.huobi.client.model.enums.TransferMasterType;


import java.math.BigDecimal;

/**
 * The request of transfer asset between parent and sub account.
 */
public class TransferMasterRequest {

  /**
   * The request of transfer asset between parent and sub account.
   *
   * @param subUid   The target sub account uid to transfer to or from. (mandatory)
   * @param currency The crypto currency to transfer. (mandatory)
   * @param amount   The amount of asset to transfer. (mandatory)
   * @param type     The type of transfer, see {@link TransferMasterType} (mandatory)
   */
  public TransferMasterRequest(
      Long subUid,
      String currency,
      BigDecimal amount,
      TransferMasterType type) {
    this.subUid = subUid;
    this.currency = currency;
    this.amount = amount;
    this.type = type;
  }

  private final Long subUid;

  private final TransferMasterType type;

  private final String currency;

  private final BigDecimal amount;

  public Long getSubUid() {
    return subUid;
  }

  public TransferMasterType getType() {
    return type;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
