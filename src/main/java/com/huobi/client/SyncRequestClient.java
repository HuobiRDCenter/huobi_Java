package com.huobi.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.huobi.client.impl.HuobiApiInternalFactory;
import com.huobi.client.model.Account;
import com.huobi.client.model.AccountHistory;
import com.huobi.client.model.Balance;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.BatchCancelResultV1;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.CompleteSubAccountInfo;
import com.huobi.client.model.CreateOrderResult;
import com.huobi.client.model.CrossMarginAccount;
import com.huobi.client.model.CrossMarginLoanOrder;
import com.huobi.client.model.Currency;
import com.huobi.client.model.Deposit;
import com.huobi.client.model.DepositAddress;
import com.huobi.client.model.EtfSwapConfig;
import com.huobi.client.model.EtfSwapHistory;
import com.huobi.client.model.ExchangeInfo;
import com.huobi.client.model.FeeRate;
import com.huobi.client.model.LastTradeAndBestQuote;
import com.huobi.client.model.Loan;
import com.huobi.client.model.MarginBalanceDetail;
import com.huobi.client.model.MatchResult;
import com.huobi.client.model.Order;
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.SubuserManagementResult;
import com.huobi.client.model.Symbol;
import com.huobi.client.model.Trade;
import com.huobi.client.model.TradeStatistics;
import com.huobi.client.model.Withdraw;
import com.huobi.client.model.WithdrawQuota;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.enums.EtfSwapType;
import com.huobi.client.model.enums.QueryDirection;
import com.huobi.client.model.request.AccountHistoryRequest;
import com.huobi.client.model.request.BatchCancelRequest;
import com.huobi.client.model.request.CancelOpenOrderRequest;
import com.huobi.client.model.request.CandlestickRequest;
import com.huobi.client.model.request.CrossMarginApplyLoanRequest;
import com.huobi.client.model.request.CrossMarginLoanOrderRequest;
import com.huobi.client.model.request.CrossMarginRepayLoanRequest;
import com.huobi.client.model.request.CrossMarginTransferRequest;
import com.huobi.client.model.request.HistoricalOrdersRequest;
import com.huobi.client.model.request.LoanOrderRequest;
import com.huobi.client.model.request.MatchResultRequest;
import com.huobi.client.model.request.NewOrderRequest;
import com.huobi.client.model.request.OpenOrderRequest;
import com.huobi.client.model.request.OrdersHistoryRequest;
import com.huobi.client.model.request.OrdersRequest;
import com.huobi.client.model.request.SubuserManagementRequest;
import com.huobi.client.model.request.TransferFuturesRequest;
import com.huobi.client.model.request.TransferMasterRequest;
import com.huobi.client.model.request.TransferRequest;
import com.huobi.client.model.request.WithdrawRequest;


/**
 * Synchronous request interface, invoking Huobi RestAPI via synchronous method.<br> All methods in
 * this interface will be blocked until the RestAPI response.
 * <p>
 * If the invoking failed or timeout, the {@link com.huobi.client.exception.HuobiApiException} will
 * be thrown.
 */
public interface SyncRequestClient {

  /**
   * Get the latest candlestick/kline for the specified symbol.
   *
   * @param symbol The symbol, like "btcusdt". To query hb10, put "hb10" at here. (mandatory)
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc. (mandatory)
   * @param size The maximum number of candlestick/kline requested. Range [1 - 2000] (mandatory)
   * @return The list of candlestick/kline data, see {@link Candlestick}
   */
  List<Candlestick> getLatestCandlestick(String symbol, CandlestickInterval interval, int size);

  /**
   * Get the candlestick/kline for the specified symbol. The data number is 150 as default.
   *
   * @param request The request for getting candlestick/kline data, see {@link CandlestickRequest}
   * @return The list of candlestick/kline data, see {@link Candlestick}
   */
  List<Candlestick> getCandlestick(CandlestickRequest request);

  /**
   * Get the timestamp from Huobi server. The timestamp is the Unix timestamp in millisecond.<br>
   * The count shows how many milliseconds passed from Jan 1st 1970, 00:00:00.000 at UTC.<br>
   * <br> e.g. 1546300800000 is Thu, 1st Jan 2019 00:00:00.000 UTC.
   *
   * @return The Unix timestamp at UTC in millisecond.
   */
  long getExchangeTimestamp();

  /**
   * Get the Market Depth of a symbol.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param size The maximum number of Market Depth requested. range [1 - 150] (mandatory)
   * @return Market Depth data, see {@link PriceDepth}
   */
  PriceDepth getPriceDepth(String symbol, int size);

  /**
   * Get 20 levels of Market Depth of a symbol.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @return Market Depth data, see {@link PriceDepth}
   */
  PriceDepth getPriceDepth(String symbol);

  /**
   * Get the last trade with their price, volume and direction.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @return The last trade with price and amount, see {@link Trade}
   */
  Trade getLastTrade(String symbol);

  /**
   * Get the most recent trades with their price, volume and direction.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param size The number of historical trade requested, range [1 - 2000] (mandatory)
   * @return The list of trade, see {@link Trade}
   */
  List<Trade> getHistoricalTrade(String symbol, int size);

  List<Trade> getTrade(String symbol);

  /**
   * Get trade statistics in 24 hours.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @return Trade statistics, see {@link TradeStatistics}
   */
  TradeStatistics get24HTradeStatistics(String symbol);

  /**
   * Get all the trading assets and currencies supported in huobi. The information of trading
   * instrument, including base currency, quote precision, etc.
   *
   * @return The information of trading instrument and currencies, see {@link ExchangeInfo}
   */
  ExchangeInfo getExchangeInfo();

  /**
   * Get all the trading currencies supported in Huobi.
   * @return The name of trading currencies .
   */
  List<String> getCurrencies();

  /**
   * Get the trading currency information detail in huobi.
   * @param currency The name of currency
   * @param authorizedUser if user authorized
   * @return
   */
  List<Currency> getCurrencyInfo(String currency, Boolean authorizedUser);
  /**
   * Get all the trading symbol supported in Huobi.
   * @return The information of trading symbol .
   */
  List<Symbol> getSymbols();

  /**
   * Get the best bid and ask.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @return The best quote, see {@link BestQuote}
   */
  BestQuote getBestQuote(String symbol);

  /**
   * Get the withdraw records of an account.
   *
   * @param currency The currency, like "btc". (mandatory)
   * @param fromId The beginning withdraw record id. (mandatory)
   * @param size The size of record. (mandatory)
   * @return The list of withdraw records.
   */
  List<Withdraw> getWithdrawHistory(String currency, long fromId, int size);

  List<Withdraw> getWithdrawHistory(String currency, long fromId, int size, QueryDirection queryDirection);

  /**
   * Get the deposit records of an account.
   *
   * @param currency The currency, like "btc". (mandatory)
   * @param fromId The beginning deposit record id. (mandatory)
   * @param size The size of record. (mandatory)
   * @return The list of deposit records.
   */
  List<Deposit> getDepositHistory(String currency, long fromId, int size);

  /**
   *
   * @param currency currency The currency, like "btc". (mandatory)
   * @param fromId  The beginning deposit record id. (mandatory)
   * @param size  The size of record. (mandatory)
   * @param queryDirection  The direction of query
   * @return
   */
  List<Deposit> getDepositHistory(String currency, long fromId, int size, QueryDirection queryDirection);
  /**
   * Transfer asset from specified account to another account.
   *
   * @param transferRequest The symbol, like "btcusdt"
   * @return The transfer id.
   */
  Long transfer(TransferRequest transferRequest);

  /**
   *
   * @param request The request of transfer between futures and pro
   * @return The transfer id.
   */

  Long transferFutures(TransferFuturesRequest request);

  /**
   * Submit a request to borrow with margin account.
   *
   * @param symbol The trading symbol to borrow margin, e.g. "btcusdt", "bccbtc". (mandatory)
   * @param currency The currency to borrow,like "btc". (mandatory)
   * @param amount The amount of currency to borrow. (mandatory)
   * @return The margin order id.
   */
  long applyLoan(String symbol, String currency, BigDecimal amount);

  /**
   * Repay margin loan with you asset in your margin account.
   *
   * @param loadId The previously returned order id when loan order was created. (mandatory)
   * @param amount The amount of currency to repay. (mandatory)
   * @return The margin order id.
   */
  long repayLoan(long loadId, BigDecimal amount);

  /**
   * Get the margin loan records.
   *
   * @param loanOrderRequest The information of order request, including symbol, start-date,
   * end-date etc, see {@link LoanOrderRequest}
   * @return The list of the margin loan records, see {@link Loan}
   */
  List<Loan> getLoanHistory(LoanOrderRequest loanOrderRequest);


  /**
   * This endpoint transfer specific asset `from spot trading account to cross margin account` or `from cross margin account to spot trading account`.
   * @param request
   * @return
   */
  long transferCrossMargin(CrossMarginTransferRequest request);

  /**
   * This endpoint place an order to apply a margin loan.
   * @param request
   * @return
   */
  long applyCrossMarginLoan(CrossMarginApplyLoanRequest request);

  /**
   * This endpoint repays margin loan with you asset in your margin account.
   * @param repayLoanRequest
   */
  void repayCrossMarginLoan(CrossMarginRepayLoanRequest repayLoanRequest);

  /**
   * This endpoint returns margin orders based on a specific searching criteria.
   * @param request
   * @return
   */
  List<CrossMarginLoanOrder> getCrossMarginLoanHistory(CrossMarginLoanOrderRequest request);

  /**
   * This endpoint returns the balance of the margin loan account.
   * @return
   */
  CrossMarginAccount getCrossMarginAccount();


  /**
   * Get last trade, best bid and best ask of a symbol.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @return The data includes last trade, best bid and best ask, see {@link LastTradeAndBestQuote}
   */
  LastTradeAndBestQuote getLastTradeAndBestQuote(String symbol);

  /**
   * Get the balance of a all accounts.
   *
   * @return The information of account balance.
   */
  List<Account> getAccountBalance();

  /**
   * Get the account of the specified type
   *
   * @param accountType The specified account type. (mandatory)
   * @return The information of the account that is specified type.
   */
  Account getAccountBalance(AccountType accountType);

  Account getAccountBalance(AccountType accountType, String symbol);


  /**
   * Get account flow
   * @param request account history request
   * @return
   */
  List<AccountHistory> getAccountHistory(AccountHistoryRequest request);


  SubuserManagementResult subuserManagement(SubuserManagementRequest request);

  /**
   * Make an order in huobi.
   *
   * @param newOrderRequest The request of an order ,including account-id,amount , price ,etc.
   * @return The order id.
   */
  long createOrder(NewOrderRequest newOrderRequest);

  List<CreateOrderResult> batchCreateOrder(List<NewOrderRequest> requestList);

  /**
   * Provide open orders of a symbol for an account<br> When neither account-id nor symbol defined
   * in the request, the system will return all open orders (max 500) for all symbols and all
   * accounts of the user, sorted by order ID in descending.
   *
   * @param openOrderRequest open order request.
   * @return The orders information.
   */
  List<Order> getOpenOrders(OpenOrderRequest openOrderRequest);

  /**
   * Submit cancel request for cancelling an order.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param orderId The order id. (mandatory)
   */
  void cancelOrder(String symbol, long orderId);

  void cancelOrderByClientOrderId(String symbol, String clientOrderId);

  /**
   * Submit cancel request for cancelling multiple orders.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param orderIds The list of order id. the max size is 50. (mandatory)
   */
  BatchCancelResultV1 cancelOrders(String symbol, List<Long> orderIds);

  BatchCancelResultV1 cancelOrders(BatchCancelRequest BatchCancelRequest);

  /**
   * Request to cancel open orders.
   *
   * @param request The request for cancel open order.
   * @return Status of batch cancel result.
   */
  BatchCancelResult cancelOpenOrders(CancelOpenOrderRequest request);

  /**
   * Get the details of an order.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param orderId The order id. (mandatory)
   * @return The information of order.
   */
  Order getOrder(String symbol, long orderId);

  /**
   *
   * @param symbol  symbol The symbol, like "btcusdt". (mandatory)
   * @param clientOrderId  The client order id defined by client
   * @return
   */
  Order getOrderByClientOrderId(String symbol, String clientOrderId);

  /**
   * Get detail match results of an order.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param orderId The order id. (mandatory)
   * @return The list of match result.
   */
  List<MatchResult> getMatchResults(String symbol, long orderId);

  /**
   * Search for the trade records of an account.
   *
   * @param matchResultRequest A specific account information, including symbols, types etc.
   * @return The list of match result.
   */
  List<MatchResult> getMatchResults(MatchResultRequest matchResultRequest);

  /**
   * Get the deposit address of currency.
   * @param currency The currency, like "btc". (mandatory)
   * @return
   */
  List<DepositAddress> getDepositAddress(String currency);

  /**
   * Get the withdraw quota information.
   * @param currency The currency, like "btc". (mandatory)
   * @return
   */
  WithdrawQuota getWithdrawQuota(String currency);

  /**
   * Submit a request to withdraw some asset from an account.
   *
   * @param withdrawRequest The withdraw request, including address, amount, etc.
   * @return Withdraw id.
   */
  long withdraw(WithdrawRequest withdrawRequest);

  /**
   * Cancel an withdraw request.
   *
   * @param currency The currency, like "btc". (mandatory)
   * @param withdrawId withdraw id (mandatory)
   */
  void cancelWithdraw(String currency, long withdrawId);

  /**
   * Get historical orders.
   *
   * @param req The request for getting historial orders.
   * @return The order list, see {@link Order}
   */
  List<Order> getHistoricalOrders(HistoricalOrdersRequest req);

  /**
   *
   * @param req  The order request
   * @return  The order list, see {@link Order}
   */

  List<Order> getOrders(OrdersRequest req);

  /**
   *
   * @param req   The request of historical orders
   * @return  The order list, see {@link Order}
   */
  List<Order> getOrderHistory(OrdersHistoryRequest req);

  /**
   *
   * @param symbol  The symbol, like "btcusdt". (mandatory)
   * @return  The feeRate list, see {@link FeeRate}
   */
  List<FeeRate> getFeeRate(String symbol);
  /**
   * Transfer Asset between Parent and Sub Account.
   *
   * @param req The request for transferring in master.
   * @return order id.
   */
  long transferBetweenParentAndSub(TransferMasterRequest req);

  /**
   * Get the aggregated balance of all sub-accounts of the current user.
   *
   * @return The balance of all the sub-account aggregated.
   */
  List<Balance> getCurrentUserAggregatedBalance();

  /**
   * Get account balance of a sub-account.
   *
   * @param subId the specified sub account id to get balance for.
   * @return the balance of a sub-account specified by sub-account uid.
   */
  List<CompleteSubAccountInfo> getSpecifyAccountBalance(long subId);

  /**
   * Get the basic information of ETF creation and redemption, as well as ETF constituents,
   * including max amount of creation, min amount of creation, max amount of redemption, min amount
   * of redemption, creation fee rate, redemption fee rate, eft create/redeem status.
   *
   * @param etfSymbol The symbol, currently only support hb10. (mandatory)
   * @return The etf configuration information, see {@link EtfSwapConfig}
   */
  EtfSwapConfig getEtfSwapConfig(String etfSymbol);

  /**
   * Order creation or redemption of ETF.
   *
   * @param etfSymbol The symbol, currently only support hb10. (mandatory)
   * @param amount The amount to create or redemption. (mandatory)
   * @param swapType The swap type to indicate creation or redemption, see {@link EtfSwapType}
   * (mandatory)
   */
  void etfSwap(String etfSymbol, int amount, EtfSwapType swapType);

  /**
   * Get past creation and redemption.(up to 100 records)
   *
   * @param etfSymbol The symbol, currently only support hb10. (mandatory)
   * @param offset The offset of the records, set to 0 for the latest records. (mandatory)
   * @param size The number of records to return, the range is [1, 100]. (mandatory)
   * @return The swap history.
   */
  List<EtfSwapHistory> getEtfSwapHistory(String etfSymbol, int offset, int size);

  /**
   * Get the latest candlestick/kline for the etf.
   *
   * @param etfSymbol The symbol, currently only support hb10. (mandatory)
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc. (mandatory)
   * @param size The maximum number of candlestick/kline requested. Range [1 - 2000] (optional)
   * @return The list of candlestick/kline data, see {@link Candlestick}
   */
  List<Candlestick> getEtfCandlestick(String etfSymbol, CandlestickInterval interval,
      Integer size);

  /**
   * Get the Balance of the Margin Loan Account
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @return The margin loan account detail.
   */
  List<MarginBalanceDetail> getMarginBalanceDetail(String symbol);

  /**
   * Get Latest Tickers for All Pairs.
   *
   * @return The statistics of all symbols
   */
  Map<String, TradeStatistics> getTickers();

  /**
   * Create the synchronous client. All interfaces defined in synchronous client are implemented by
   * synchronous mode.
   *
   * @return The instance of synchronous client.
   */
  static SyncRequestClient create() {
    return create("", "", new RequestOptions());
  }

  /**
   * Create the synchronous client. All interfaces defined in synchronous client are implemented by
   * synchronous mode.
   *
   * @param apiKey The public key applied from Huobi.
   * @param secretKey The private key applied from Huobi.
   * @return The instance of synchronous client.
   */
  static SyncRequestClient create(String apiKey, String secretKey) {
    return HuobiApiInternalFactory.getInstance().createSyncRequestClient(
        apiKey, secretKey, new RequestOptions());
  }

  /**
   * Create the synchronous client. All interfaces defined in synchronous client are implemented by
   * synchronous mode.
   *
   * @param apiKey The public key applied from Huobi.
   * @param secretKey The private key applied from Huobi.
   * @param options The request option.
   * @return The instance of synchronous client.
   */
  static SyncRequestClient create(String apiKey, String secretKey, RequestOptions options) {
    return HuobiApiInternalFactory.getInstance().createSyncRequestClient(
        apiKey, secretKey, options);
  }
}
