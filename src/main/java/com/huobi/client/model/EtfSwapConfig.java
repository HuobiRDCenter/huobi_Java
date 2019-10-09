package com.huobi.client.model;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.model.enums.EtfStatus;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * The basic information of ETF creation and redemption, as well as ETF constituents, including max
 * amount of creation, min amount of creation, max amount of redemption, min amount of redemption,
 * creation fee rate, redemption fee rate, eft create/redeem status.
 */
public class EtfSwapConfig {

  private int purchaseMinAmount;
  private int purchaseMaxAmount;
  private int redemptionMinAmount;
  private int redemptionMaxAmount;
  private BigDecimal purchaseFeeRate;
  private BigDecimal redemptionFeeRate;
  private EtfStatus status;
  private List<UnitPrice> unitPriceList;

  /**
   * Get the minimum creation amounts per request.
   *
   * @return The amount.
   */
  public int getPurchaseMinAmount() {
    return purchaseMinAmount;
  }

  /**
   * Get the max creation amounts per request.
   *
   * @return The amount.
   */
  public int getPurchaseMaxAmount() {
    return purchaseMaxAmount;
  }

  /**
   * Get the minimum redemption amounts per request.
   *
   * @return The amount.
   */
  public int getRedemptionMinAmount() {
    return redemptionMinAmount;
  }

  /**
   * Get the max redemption amounts per request.
   *
   * @return The amount.
   */
  public int getRedemptionMaxAmount() {
    return redemptionMaxAmount;
  }


  /**
   * Get the creation fee rate.
   *
   * @return The rate value.
   */
  public BigDecimal getPurchaseFeeRate() {
    return purchaseFeeRate;
  }

  /**
   * Get the redemption fee rate.
   *
   * @return The rate value.
   */
  public BigDecimal getRedemptionFeeRate() {
    return redemptionFeeRate;
  }


  /**
   * Get the status of the ETF.
   *
   * @return The ETF status.
   */
  public EtfStatus getStatus() {
    return status;
  }

  /**
   * Get ETF constitution in format of amount and currency.
   *
   * @return The unit price list
   */
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


  public static RestApiJsonParser<EtfSwapConfig> getParser(){
    return (jsonWrapper -> {
      JsonWrapper data = jsonWrapper.getJsonObject("data");
      EtfSwapConfig etfSwapConfig = new EtfSwapConfig();
      etfSwapConfig.setPurchaseMaxAmount(data.getInteger("purchase_max_amount"));
      etfSwapConfig.setPurchaseMinAmount(data.getInteger("purchase_min_amount"));
      etfSwapConfig.setRedemptionMaxAmount(data.getInteger("redemption_max_amount"));
      etfSwapConfig.setRedemptionMinAmount(data.getInteger("redemption_min_amount"));
      etfSwapConfig.setPurchaseFeeRate(data.getBigDecimal("purchase_fee_rate"));
      etfSwapConfig.setRedemptionFeeRate(data.getBigDecimal("redemption_fee_rate"));
      etfSwapConfig.setStatus(EtfStatus.lookup(Integer.toString(data.getInteger("etf_status"))));
      JsonWrapperArray unitPrices = data.getJsonArray("unit_price");
      List<UnitPrice> unitPriceList = new LinkedList<>();
      unitPrices.forEach((item) -> {
        unitPriceList.add(UnitPrice.parse(item));
      });
      etfSwapConfig.setUnitPriceList(unitPriceList);
      return etfSwapConfig;
    });
  }
}
