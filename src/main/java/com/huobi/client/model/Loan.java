package com.huobi.client.model;

import com.huobi.client.impl.AccountsInfoMap;
import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.LoanOrderStates;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * The margin order information.
 */
public class Loan {

  private long id;
  private long userId;
  private AccountType accountType;
  private String symbol;
  private String currency;
  private BigDecimal loanAmount;
  private BigDecimal loanBalance;
  private BigDecimal interestRate;
  private BigDecimal interestAmount;
  private BigDecimal interestBalance;
  private LoanOrderStates state;
  private long createdTimestamp;
  private long accruedTimestamp;

  /**
   * Get the order id.
   *
   * @return The id.
   */
  public long getId() {
    return id;
  }

  /**
   * Get the user id.
   *
   * @return The id.
   */
  public long getUserId() {
    return userId;
  }

  /**
   * Get the account type which created the loan order.
   *
   * @return The account type, see {@link AccountType}
   */
  public AccountType getAccountType() {
    return accountType;
  }

  /**
   * Get the symbol, like "btcusdt".
   *
   * @return The symbol.
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Get the currency name.
   *
   * @return The currency.
   */
  public String getCurrency() {
    return currency;
  }

  /**
   * Get the amount of the origin loan.
   *
   * @return The amount.
   */
  public BigDecimal getLoanAmount() {
    return loanAmount;
  }

  /**
   * Get the amount of the loan left.
   *
   * @return The balance.
   */
  public BigDecimal getLoanBalance() {
    return loanBalance;
  }

  /**
   * Get the loan interest rate.
   *
   * @return The rate.
   */
  public BigDecimal getInterestRate() {
    return interestRate;
  }

  /**
   * Get the accumulated loan interest.
   *
   * @return The amount.
   */
  public BigDecimal getInterestAmount() {
    return interestAmount;
  }

  /**
   * Get the amount of loan interest left.
   *
   * @return The amount.
   */
  public BigDecimal getInterestBalance() {
    return interestBalance;
  }

  /**
   * Get the loan stats, possible values: created, accrual, cleared, invalid.
   *
   * @return The status, see {@link LoanOrderStates}
   */
  public LoanOrderStates getState() {
    return state;
  }

  /**
   * Get the UNIX formatted timestamp in UTC when the order was created.
   *
   * @return The timestamp.
   */
  public long getCreatedTimestamp() {
    return createdTimestamp;
  }

  /**
   * Get the UNIX formatted timestamp in UTC when the last accrue happened.
   *
   * @return The timestamp.
   */
  public long getAccruedTimestamp() {
    return accruedTimestamp;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setLoanAmount(BigDecimal loanAmount) {
    this.loanAmount = loanAmount;
  }

  public void setLoanBalance(BigDecimal loanBalance) {
    this.loanBalance = loanBalance;
  }

  public void setInterestRate(BigDecimal interestRate) {
    this.interestRate = interestRate;
  }

  public void setInterestAmount(BigDecimal interestAmount) {
    this.interestAmount = interestAmount;
  }

  public void setInterestBalance(BigDecimal interestBalance) {
    this.interestBalance = interestBalance;
  }

  public void setState(LoanOrderStates state) {
    this.state = state;
  }

  public void setCreatedTimestamp(long createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public void setAccruedTimestamp(long accruedTimestamp) {
    this.accruedTimestamp = accruedTimestamp;
  }


  public static RestApiJsonParser<List<Loan>> getListParser(String apiKey){
    return (jsonWrapper -> {
      List<Loan> loans = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Loan loan = new Loan();
        loan.setLoanBalance(item.getBigDecimal("loan-balance"));
        loan.setInterestBalance(item.getBigDecimal("interest-balance"));
        loan.setInterestRate(item.getBigDecimal("interest-rate"));
        loan.setLoanAmount(item.getBigDecimal("loan-amount"));
        loan.setInterestAmount(item.getBigDecimal("interest-amount"));
        loan.setSymbol(item.getString("symbol"));
        loan.setCurrency(item.getString("currency"));
        loan.setId(item.getLong("id"));
        loan.setState(LoanOrderStates.lookup(item.getString("state")));
        loan.setAccountType(
            AccountsInfoMap.getAccount(apiKey, item.getLong("account-id")).getType());
        loan.setUserId(item.getLong("user-id"));
        loan.setAccruedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("accrued-at")));
        loan.setCreatedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("created-at")));
        loans.add(loan);
      });
      return loans;
    });
  }
}
