package com.huobi.client.model;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.model.enums.EtfSwapType;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * The past creation and redemption.
 */
public class EtfSwapHistory {

  private long createdTimestamp;
  private String currency;
  private BigDecimal amount;
  private EtfSwapType type;
  private int status;
  private BigDecimal rate;
  private BigDecimal fee;
  private BigDecimal pointCardAmount;
  private List<UnitPrice> usedCurrencyList;
  private List<UnitPrice> obtainCurrencyList;

  /**
   * Get the UNIX formatted timestamp in UTC of the operation.
   *
   * @return The timestamp.
   */
  public long getCreatedTimestamp() {
    return createdTimestamp;
  }

  /**
   * Get the ETF name.
   *
   * @return ETF name.
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * Get creation or redemption amount.
   *
   * @return The amount.
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Get the swap type. Creation or redemption.
   *
   * @return The swap type.
   */
  public EtfSwapType getType() {
    return type;
  }

  /**
   * Get the operation result
   *
   * @return The status.
   */
  public int getStatus() {
    return status;
  }

  /**
   * Get the fee rate.
   *
   * @return The rate.
   */
  public BigDecimal getRate() {
    return rate;
  }

  /**
   * Get the actual fee amount
   *
   * @return The amount.
   */
  public BigDecimal getFee() {
    return fee;
  }

  /**
   * Get discount from point card.
   *
   * @return The amount.
   */
  public BigDecimal getPointCardAmount() {
    return pointCardAmount;
  }

  /**
   * For creation this is the list and amount of underlying assets used for ETF creation. For
   * redemption this is the amount of ETF used for redemption.
   *
   * @return The list.
   */
  public List<UnitPrice> getUsedCurrencyList() {
    return usedCurrencyList;
  }

  /**
   * For creation this is the amount for ETF created. For redemption this is the list and amount of
   * underlying assets obtained.
   *
   * @return The list.
   */
  public List<UnitPrice> getObtainCurrencyList() {
    return obtainCurrencyList;
  }

  public void setCreatedTimestamp(long createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setType(EtfSwapType type) {
    this.type = type;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  public void setPointCardAmount(BigDecimal pointCardAmount) {
    this.pointCardAmount = pointCardAmount;
  }

  public void setUsedCurrencyList(List<UnitPrice> usedCurrencyList) {
    this.usedCurrencyList = usedCurrencyList;
  }

  public void setObtainCurrencyList(List<UnitPrice> obtainCurrencyList) {
    this.obtainCurrencyList = obtainCurrencyList;
  }

  public static RestApiJsonParser<List<EtfSwapHistory>> getListParser(){
    return (jsonWrapper -> {
      List<EtfSwapHistory> etfSwapHistoryList = new LinkedList<>();
      JsonWrapperArray data = jsonWrapper.getJsonArray("data");
      data.forEach((dataItem) -> {
        EtfSwapHistory etfSwapHistory = new EtfSwapHistory();
        etfSwapHistory.setCreatedTimestamp(dataItem.getLong("gmt_created"));
        etfSwapHistory.setCurrency(dataItem.getString("currency"));
        etfSwapHistory.setAmount(dataItem.getBigDecimal("amount"));
        etfSwapHistory.setType(EtfSwapType.lookup(dataItem.getString("type")));
        etfSwapHistory.setStatus(dataItem.getInteger("status"));
        JsonWrapper detail = dataItem.getJsonObject("detail");
        etfSwapHistory.setRate(detail.getBigDecimal("rate"));
        etfSwapHistory.setFee(detail.getBigDecimal("fee"));
        etfSwapHistory.setPointCardAmount(detail.getBigDecimal("point_card_amount"));
        JsonWrapperArray usedCurrencyArray = detail.getJsonArray("used_currency_list");
        List<UnitPrice> usedCurrencyList = new LinkedList<>();
        usedCurrencyArray.forEach((currency) -> {
          usedCurrencyList.add(UnitPrice.parse(currency));
        });
        etfSwapHistory.setUsedCurrencyList(usedCurrencyList);
        JsonWrapperArray obtainCurrencyArray = detail.getJsonArray("obtain_currency_list");
        List<UnitPrice> obtainCurrencyList = new LinkedList<>();
        obtainCurrencyArray.forEach((currency) -> {
          obtainCurrencyList.add(UnitPrice.parse(currency));
        });
        etfSwapHistory.setObtainCurrencyList(obtainCurrencyList);
        etfSwapHistoryList.add(etfSwapHistory);
      });
      return etfSwapHistoryList;
    });
  }
}
