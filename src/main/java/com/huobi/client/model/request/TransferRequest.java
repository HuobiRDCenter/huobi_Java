package com.huobi.client.model.request;

import com.huobi.client.model.enums.AccountType;

import java.math.BigDecimal;

/**
 * The request of transfer(in or out)
 */
public class TransferRequest {

  /**
   * The request of transfer(in or out)
   *
   * @param symbol   The symbol, like "btcusdt". (mandatory)
   * @param from     The type, transfer from which account. (mandatory)
   * @param to       The type, transfer to which account. (mandatory)
   * @param currency The currency of transfer. (mandatory)
   * @param amount   The amount of transfer. (mandatory)
   */
  public TransferRequest(
      String symbol,
      AccountType from,
      AccountType to,
      String currency,
      BigDecimal amount) {
    this.symbol = symbol;
    this.from = from;
    this.to = to;
    this.currency = currency;
    this.amount = amount;
  }

  public final String symbol;

  public final AccountType from;

  public final AccountType to;

  public final String currency;

  public final BigDecimal amount;
}
