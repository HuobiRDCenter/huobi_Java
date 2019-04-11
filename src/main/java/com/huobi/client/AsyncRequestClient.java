package com.huobi.client;

import com.huobi.client.impl.HuobiApiInternalFactory;
import com.huobi.client.model.Account;
import com.huobi.client.model.Balance;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.CompleteSubAccountInfo;
import com.huobi.client.model.Deposit;
import com.huobi.client.model.EtfSwapConfig;
import com.huobi.client.model.EtfSwapHistory;
import com.huobi.client.model.ExchangeInfo;
import com.huobi.client.model.LastTradeAndBestQuote;
import com.huobi.client.model.Loan;
import com.huobi.client.model.MarginBalanceDetail;
import com.huobi.client.model.MatchResult;
import com.huobi.client.model.Order;
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.enums.EtfSwapType;
import com.huobi.client.model.request.TransferMasterRequest;
import com.huobi.client.model.Trade;
import com.huobi.client.model.TradeStatistics;
import com.huobi.client.model.Withdraw;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.request.CancelOpenOrderRequest;
import com.huobi.client.model.request.CandlestickRequest;
import com.huobi.client.model.request.HistoricalOrdersRequest;
import com.huobi.client.model.request.LoanOrderRequest;
import com.huobi.client.model.request.MatchResultRequest;
import com.huobi.client.model.request.NewOrderRequest;
import com.huobi.client.model.request.OpenOrderRequest;
import com.huobi.client.model.request.TransferRequest;
import com.huobi.client.model.request.WithdrawRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Asynchronous request interface, invoking Huobi RestAPI via asynchronous method. All methods in
 * this interface will return immediately, do not wait the server's response. So you must implement
 * the ResponseCallback interface yourself. As long as the server response received, the onResponse
 * callback method will be called..
 */
public interface AsyncRequestClient {

  /**
   * Get the latest candlestick/kline for the specified symbol.
   *
   * @param symbol The symbol, like "btcusdt". To query hb10, put "hb10" at here. (mandatory)
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc. (mandatory)
   * @param size The maximum number of candlestick/kline requested. Range [1 - 2000] (mandatory)
   * @param callback The callback you should implemented.
   */
  void getLatestCandlestick(
      String symbol,
      CandlestickInterval interval,
      int size,
      ResponseCallback<AsyncResult<List<Candlestick>>> callback);

  /**
   * Get the candlestick/kline for the specified symbol. The data number is 150 as default.
   *
   * @param request The request for getting candlestick/kline data, see {@link CandlestickRequest}
   * @param callback The callback you should implemented.
   */
  void getCandlestick(
      CandlestickRequest request, ResponseCallback<AsyncResult<List<Candlestick>>> callback);

  /**
   * Get the timestamp from Huobi server. The timestamp is the Unix timestamp in millisecond.<br>
   * The count shows how many milliseconds passed from Jan 1st 1970, 00:00:00.000 at UTC.<br>
   * <br> e.g. 1546300800000 is Thu, 1st Jan 2019 00:00:00.000 UTC.
   *
   * @param callback The callback you should implemented.
   */
  void getExchangeTimestamp(ResponseCallback<AsyncResult<Long>> callback);

  /**
   * Get the Market Depth of a symbol.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param size The maximum number of Market Depth requested. range [1 - 150] (mandatory)
   * @param callback The callback you should implemented.
   */
  void getPriceDepth(String symbol, int size, ResponseCallback<AsyncResult<PriceDepth>> callback);

  /**
   * Get 20 levels of Market Depth of a symbol.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param callback The callback you should implemented.
   */
  void getPriceDepth(String symbol, ResponseCallback<AsyncResult<PriceDepth>> callback);

  /**
   * Get the last trade with their price, volume and direction.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param callback The callback you should implemented.
   */
  void getLastTrade(String symbol, ResponseCallback<AsyncResult<Trade>> callback);

  /**
   * Get the most recent trades with their price, volume and direction.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param size The  number of historical trade requested, range [1 - 2000] (mandatory)
   * @param callback The callback you should implemented.
   */
  void getHistoricalTrade(String symbol, int size,
      ResponseCallback<AsyncResult<List<Trade>>> callback);

  /**
   * Get trade statistics in 24 hours.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param callback The callback you should implemented.
   */
  void get24HTradeStatistics(String symbol,
      ResponseCallback<AsyncResult<TradeStatistics>> callback);

  /**
   * Get all the trading assets and currencies supported in huobi. The information of trading
   * instrument, including base currency, quote precision, etc.
   *
   * @param callback The callback you should implemented.
   */
  void getExchangeInfo(ResponseCallback<AsyncResult<ExchangeInfo>> callback);

  /**
   * Get the best bid and ask.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param callback The callback you should implemented.
   */
  void getBestQuote(String symbol, ResponseCallback<AsyncResult<BestQuote>> callback);

  /**
   * Get the withdraw records of an account
   *
   * @param currency The currency, like "btc". (mandatory)
   * @param fromId The beginning withdraw record id. (mandatory)
   * @param size The size of record. (mandatory)
   * @param callback The callback you should implemented.
   */
  void getWithdrawHistory(
      String currency, long fromId, int size,
      ResponseCallback<AsyncResult<List<Withdraw>>> callback);

  /**
   * Get the deposit records of an account
   *
   * @param currency The currency, like "btc". (mandatory)
   * @param fromId The beginning deposit record id. (mandatory)
   * @param size The size of record. (mandatory)
   * @param callback The callback you should implemented.
   */
  void getDepositHistory(
      String currency,
      long fromId,
      int size,
      ResponseCallback<AsyncResult<List<Deposit>>> callback);

  /**
   * Transfer asset from specified account to another account.
   *
   * @param transferRequest The symbol, like "btcusdt"
   * @param callback The callback you should implemented.
   */
  void transfer(TransferRequest transferRequest, ResponseCallback<AsyncResult<Long>> callback);

  /**
   * Submit a request to borrow with margin account
   *
   * @param symbol The trading symbol to borrow margin, e.g. "btcusdt", "bccbtc". (mandatory)
   * @param currency The currency to borrow,like "btc". (mandatory)
   * @param amount The amount of currency to borrow. (mandatory)
   * @param callback The callback you should implemented.
   */
  void applyLoan(
      String symbol, String currency, BigDecimal amount,
      ResponseCallback<AsyncResult<Long>> callback);

  /**
   * Repay margin loan with you asset in your margin account.
   *
   * @param loadId The previously returned order id when loan order was created. (mandatory)
   * @param amount The amount of currency to repay. (mandatory)
   * @param callback The callback you should implemented.
   */
  void repayLoan(long loadId, BigDecimal amount, ResponseCallback<AsyncResult<Long>> callback);

  /**
   * Get the margin loan records
   *
   * @param loanOrderRequest The information of order request, including symbol, start-date,
   * end-date etc, see {@link LoanOrderRequest}
   * @param callback The callback you should implemented.
   */
  void getLoanHistory(
      LoanOrderRequest loanOrderRequest, ResponseCallback<AsyncResult<List<Loan>>> callback);

  /**
   * Get last trade, best bid and best ask of a symbol.
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param callback The callback you should implemented.
   */
  void getLastTradeAndBestQuote(
      String symbol, ResponseCallback<AsyncResult<LastTradeAndBestQuote>> callback);

  /**
   * Get the balance of a margin account.
   *
   * @param callback The callback you should implemented.
   */
  void getAccountBalance(ResponseCallback<AsyncResult<List<Account>>> callback);

  /**
   * Get the account of the specified  type
   *
   * @param accountType The specified account  type
   * @param callback The callback you should implemented.
   */
  void getAccountBalance(AccountType accountType, ResponseCallback<AsyncResult<Account>> callback);

  /**
   * Make an order in huobi.
   *
   * @param newOrderRequest The request of an order ,including account-id,amount , price ,etc.
   * @param callback The callback you should implemented.
   */
  void createOrder(NewOrderRequest newOrderRequest, ResponseCallback<AsyncResult<Long>> callback);

  /**
   * Provide open orders of a symbol for an account<br> When neither account-id nor symbol defined
   * in the request, the system will return all open orders (max 500) for all symbols and all
   * accounts of the user, sorted by order ID in descending.
   *
   * @param openOrderRequest open order request
   * @param callback The callback you should implemented.
   */
  void getOpenOrders(
      OpenOrderRequest openOrderRequest, ResponseCallback<AsyncResult<List<Order>>> callback);

  /**
   * Submit cancel request for cancelling an order
   *
   * @param symbol The symbol, like "btcusdt"
   * @param orderId The order id
   * @param callback The callback you should implemented.
   */
  void cancelOrder(String symbol, long orderId, ResponseCallback<AsyncResult<Void>> callback);

  /**
   * Submit cancel request for cancelling an order
   *
   * @param symbol The symbol, like "btcusdt"
   * @param orderIds The list of order id
   * @param callback The callback you should implemented.
   */
  void cancelOrders(String symbol, List<Long> orderIds,
      ResponseCallback<AsyncResult<Void>> callback);

  /**
   * request to cancel open orders.
   *
   * @param request The request for cancel open order.
   * @param callback The callback you should implemented.
   */
  void cancelOpenOrders(
      CancelOpenOrderRequest request, ResponseCallback<AsyncResult<BatchCancelResult>> callback);

  /**
   * Get the details of an order
   *
   * @param symbol The symbol, like "btcusdt".
   * @param orderId The order id
   * @param callback The callback you should implemented.
   */
  void getOrder(String symbol, long orderId, ResponseCallback<AsyncResult<Order>> callback);

  /**
   * Get detail match results of an order
   *
   * @param symbol The symbol, like "btcusdt".
   * @param orderId order id
   * @param callback The callback you should implemented.
   */
  void getMatchResults(
      String symbol, long orderId, ResponseCallback<AsyncResult<List<MatchResult>>> callback);

  /**
   * Search for the trade records of an account
   *
   * @param matchResultRequest A specific account information, including symbols, types etc.
   * @param callback The callback you should implemented.
   */
  void getMatchResults(
      MatchResultRequest matchResultRequest,
      ResponseCallback<AsyncResult<List<MatchResult>>> callback);

  /**
   * Submit a request to withdraw some asset from an account
   *
   * @param withdrawRequest The withdraw request, including address, amount, etc
   * @param callback The callback you should implemented.
   */
  void withdraw(WithdrawRequest withdrawRequest, ResponseCallback<AsyncResult<Long>> callback);

  /**
   * Cancel an withdraw request
   *
   * @param currency The currency, like "btcusdt".
   * @param withdrawId withdraw id.
   * @param callback The callback you should implemented.
   */
  void cancelWithdraw(String currency, long withdrawId,
      ResponseCallback<AsyncResult<Void>> callback);

  /**
   * Get historical orders.
   *
   * @param req The request for getting historial orders.
   * @param callback The callback you should implemented.
   */
  void getHistoricalOrders(
      HistoricalOrdersRequest req, ResponseCallback<AsyncResult<List<Order>>> callback);

  /**
   * Transfer Asset between Parent and Sub Account
   *
   * @param req The request for transferring in master.
   * @param callback The callback you should implemented.
   */

  void transferBetweenParentAndSub(
      TransferMasterRequest req, ResponseCallback<AsyncResult<Long>> callback);

  /**
   * Get the Aggregated Balance of all Sub-accounts of the Current User
   *
   * @param callback The callback you should implemented.
   */
  void getCurrentUserAggregatedBalance(
      ResponseCallback<AsyncResult<List<Balance>>> callback);

  /**
   * Get Account Balance of a Sub-Account
   *
   * @param subId The sub account id.
   * @param callback The callback you should implemented.
   */
  void getSpecifyAccountBalance(long subId,
      ResponseCallback<AsyncResult<List<CompleteSubAccountInfo>>> callback);

  /**
   * Get the basic information of ETF creation and redemption, as well as ETF constituents,
   * including max amount of creation, min amount of creation, max amount of redemption, min amount
   * of redemption, creation fee rate, redemption fee rate, eft create/redeem status.
   *
   * @param etfSymbol The symbol, currently only support hb10. (mandatory)
   * @param callback The callback you should implemented.
   */
  void getEtfSwapConfig(String etfSymbol, ResponseCallback<AsyncResult<EtfSwapConfig>> callback);

  /**
   * Order creation or redemption of ETF.
   *
   * @param etfSymbol The symbol, currently only support hb10. (mandatory)
   * @param amount The amount to create or redemption. (mandatory)
   * @param swapType The swap type to indicate creation or redemption, see {@link EtfSwapType}
   * (mandatory)
   * @param callback The callback you should implemented.
   */
  void etfSwap(String etfSymbol, int amount, EtfSwapType swapType,
      ResponseCallback<AsyncResult<Void>> callback);

  /**
   * Get past creation and redemption.(up to 100 records)
   *
   * @param etfSymbol The symbol, currently only support hb10. (mandatory)
   * @param offset The offset of the records, set to 0 for the latest records. (mandatory)
   * @param size The number of records to return, the range is [1, 100]. (mandatory)
   * @param callback The callback you should implemented.
   */
  void getEtfSwapHistory(String etfSymbol, int offset, int size,
      ResponseCallback<AsyncResult<List<EtfSwapHistory>>> callback);

  /**
   * Get the latest candlestick/kline for the etf.
   *
   * @param etfSymbol The symbol, currently only support hb10. (mandatory)
   * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc. (mandatory)
   * @param limit The maximum number of candlestick/kline requested. Range [1 - 2000] (optional)
   * @param callback The callback you should implemented.
   */
  void getEtfCandlestick(String etfSymbol, CandlestickInterval interval,
      Integer limit, ResponseCallback<AsyncResult<List<Candlestick>>> callback);

  /**
   * Get the Balance of the Margin Loan Account
   *
   * @param symbol The symbol, like "btcusdt". (mandatory)
   * @param callback The callback you should implemented.
   */
   void getMarginBalanceDetail(
       String symbol, ResponseCallback<AsyncResult<List<MarginBalanceDetail>>> callback);

  /**
   * Get Latest Tickers for All Pairs.
   *
   * @param callback The callback you should implemented.
   */
  void getTickers(ResponseCallback<AsyncResult<Map<String, TradeStatistics>>> callback);

  /**
   * Create the asynchronous client. All interfaces defined in asynchronous client are implemented
   * by asynchronous mode.
   *
   * @return The instance of asynchronous client.
   */
  static AsyncRequestClient create() {
    return create("", "");
  }

  /**
   * Create the asynchronous client. All interfaces defined in asynchronous client are implemented
   * by asynchronous mode.
   *
   * @param apiKey The public key applied from Huobi.
   * @param secretKey The private key applied from Huobi.
   * @return The instance of asynchronous client.
   */
  static AsyncRequestClient create(String apiKey, String secretKey) {
    return HuobiApiInternalFactory.getInstance().createAsyncRequestClient(
        apiKey, secretKey, new RequestOptions());
  }

  /**
   * Create the asynchronous client. All interfaces defined in asynchronous client are implemented
   * by asynchronous mode.
   *
   * @param apiKey The public key applied from Huobi.
   * @param secretKey The private key applied from Huobi.
   * @param options The request option.
   * @return The instance of asynchronous client.
   */
  static AsyncRequestClient create(String apiKey, String secretKey, RequestOptions options) {
    return HuobiApiInternalFactory.getInstance().createAsyncRequestClient(
        apiKey, secretKey, options);
  }
}



