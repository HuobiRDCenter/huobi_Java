package com.huobi.client.model;

import com.huobi.client.model.enums.EtfStatus;
import java.math.BigDecimal;
import java.util.List;

public class EtfSwapConfig {
  private int purchaseMinAmount;
  private int purchaseMaxAmount;
  private int redemptionMinAmount;
  private int redemptionMaxAmount;
  private BigDecimal purchaseFeeRate;
  private BigDecimal redemptionFeeRate;
  private EtfStatus status;
  private List<UnitPrice> unitPriceList;

  public int getPurchaseMinAmount() {
    return purchaseMinAmount;
  }

  public int getPurchaseMaxAmount() {
    return purchaseMaxAmount;
  }

  public int getRedemptionMinAmount() {
    return redemptionMinAmount;
  }

  public int getRedemptionMaxAmount() {
    return redemptionMaxAmount;
  }

  public BigDecimal getPurchaseFeeRate() {
    return purchaseFeeRate;
  }

  public BigDecimal getRedemptionFeeRate() {
    return redemptionFeeRate;
  }

  public EtfStatus getStatus() {
    return status;
  }

  public List<UnitPrice> getUnitPriceList() {
    return unitPriceList;
  }

  public void setPurchaseMinAmount(int purchaseMinAmount) {
    this.purchaseMinAmount = purchaseMinAmount;
  }

  public void setPurchaseMaxAmount(int purchaseMaxAmount) {
    this.purchaseMaxAmount = purchaseMaxAmount;
  }

  public void setRedemptionMinAmount(int redemptionMinAmount) {
    this.redemptionMinAmount = redemptionMinAmount;
  }

  public void setRedemptionMaxAmount(int redemptionMaxAmount) {
    this.redemptionMaxAmount = redemptionMaxAmount;
  }

  public void setPurchaseFeeRate(BigDecimal purchaseFeeRate) {
    this.purchaseFeeRate = purchaseFeeRate;
  }

  public void setRedemptionFeeRate(BigDecimal redemptionFeeRate) {
    this.redemptionFeeRate = redemptionFeeRate;
  }

  public void setStatus(EtfStatus status) {
    this.status = status;
  }

  public void setUnitPriceList(List<UnitPrice> unitPriceList) {
    this.unitPriceList = unitPriceList;
  }
}
