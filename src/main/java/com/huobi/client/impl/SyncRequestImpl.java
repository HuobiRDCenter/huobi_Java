package com.huobi.client.impl;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.model.Account;
import com.huobi.client.model.AccountHistory;
import com.huobi.client.model.AccountLedger;
import com.huobi.client.model.AccountTransferResult;
import com.huobi.client.model.Balance;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.BatchCancelResultV1;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.CompleteSubAccountInfo;
import com.huobi.client.model.CreateOrderResult;
import com.huobi.client.model.CrossMarginAccount;
import com.huobi.client.model.CrossMarginLoanInfo;
import com.huobi.client.model.CrossMarginLoanOrder;
import com.huobi.client.model.Currency;
import com.huobi.client.model.Deposit;
import com.huobi.client.model.DepositAddress;
import com.huobi.client.model.EtfSwapConfig;
import com.huobi.client.model.EtfSwapHistory;
import com.huobi.client.model.ExchangeInfo;
import com.huobi.client.model.FeeRate;
import com.huobi.client.model.GetApiKeyListResult;
import com.huobi.client.model.GetSubUserAccountListResult;
import com.huobi.client.model.GetSubUserListRequest;
import com.huobi.client.model.GetSubUserListResult;
import com.huobi.client.model.GetWithdrawAddressResult;
import com.huobi.client.model.LastTradeAndBestQuote;
import com.huobi.client.model.Loan;
import com.huobi.client.model.MarginBalanceDetail;
import com.huobi.client.model.MarginLoanInfo;
import com.huobi.client.model.MatchResult;
import com.huobi.client.model.Order;
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.SubUserApiKeyGenerationResult;
import com.huobi.client.model.SubUserApiKeyModificationResult;
import com.huobi.client.model.SubUserCreationResult;
import com.huobi.client.model.SubUserState;
import com.huobi.client.model.SubUserTradableMarketResult;
import com.huobi.client.model.SubUserTransferabilityResult;
import com.huobi.client.model.SubuserManagementResult;
import com.huobi.client.model.Symbol;
import com.huobi.client.model.Trade;
import com.huobi.client.model.TradeStatistics;
import com.huobi.client.model.TransactFeeRate;
import com.huobi.client.model.Withdraw;
import com.huobi.client.model.WithdrawQuota;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.enums.QueryDirection;
import com.huobi.client.model.request.AccountHistoryRequest;
import com.huobi.client.model.request.AccountLedgerRequest;
import com.huobi.client.model.request.AccountTransferRequest;
import com.huobi.client.model.request.BatchCancelRequest;
import com.huobi.client.model.request.CancelOpenOrderRequest;
import com.huobi.client.model.request.CandlestickRequest;
import com.huobi.client.model.request.CrossMarginAccountRequest;
import com.huobi.client.model.enums.EtfSwapType;
import com.huobi.client.model.request.CrossMarginApplyLoanRequest;
import com.huobi.client.model.request.CrossMarginLoanOrderRequest;
import com.huobi.client.model.request.CrossMarginRepayLoanRequest;
import com.huobi.client.model.request.CrossMarginTransferRequest;
import com.huobi.client.model.request.GetApiKeyListRequest;
import com.huobi.client.model.request.GetSubUserAccountListRequest;
import com.huobi.client.model.request.GetWithdrawAddressRequest;
import com.huobi.client.model.request.HistoricalOrdersRequest;
import com.huobi.client.model.request.LoanOrderRequest;
import com.huobi.client.model.request.MatchResultRequest;
import com.huobi.client.model.request.NewOrderRequest;
import com.huobi.client.model.request.OpenOrderRequest;
import com.huobi.client.model.request.OrdersHistoryRequest;
import com.huobi.client.model.request.OrdersRequest;
import com.huobi.client.model.request.SubUserApiKeyDeletionRequest;
import com.huobi.client.model.request.SubUserApiKeyGenerationRequest;
import com.huobi.client.model.request.SubUserApiKeyModificationRequest;
import com.huobi.client.model.request.SubUserCreationRequest;
import com.huobi.client.model.request.SubUserDepositHistoryRequest;
import com.huobi.client.model.request.SubUserTradableMarketRequest;
import com.huobi.client.model.request.SubUserTransferabilityRequest;
import com.huobi.client.model.request.SubuserManagementRequest;
import com.huobi.client.model.request.TransferFuturesRequest;
import com.huobi.client.model.request.TransferMasterRequest;
import com.huobi.client.model.request.TransferRequest;
import com.huobi.client.model.request.WithdrawRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class SyncRequestImpl implements SyncRequestClient {

  private final RestApiRequestImpl requestImpl;

  SyncRequestImpl(RestApiRequestImpl requestImpl) {
    this.requestImpl = requestImpl;
  }

  @Override
  public List<Candlestick> getLatestCandlestick(
      String symbol, CandlestickInterval interval, int size) {
    return RestApiInvoker.callSync(requestImpl.getCandlestick(symbol, interval, null, null, size));
  }

  @Override
  public List<Candlestick> getCandlestick(CandlestickRequest request) {
    return RestApiInvoker.callSync(requestImpl.getCandlestick(
        request.getSymbol(), request.getInterval(), request.getStartTime(),
        request.getEndTime(), request.getSize()));
  }

  @Override
  public long getExchangeTimestamp() {
    return RestApiInvoker.callSync(requestImpl.getExchangeTimestamp());
  }

  @Override
  public PriceDepth getPriceDepth(String symbol, int size) {
    return RestApiInvoker.callSync(requestImpl.getPriceDepth(symbol, size));
  }

  @Override
  public PriceDepth getPriceDepth(String symbol) {
    return RestApiInvoker.callSync(requestImpl.getPriceDepth(symbol, 20));
  }

  @Override
  public LastTradeAndBestQuote getLastTradeAndBestQuote(String symbol) {
    BestQuote bestQuote = RestApiInvoker.callSync(requestImpl.getBestQuote(symbol));
    Trade lastTrade = getLastTrade(symbol);
    LastTradeAndBestQuote lastTradeAndBestQuote = new LastTradeAndBestQuote();
    lastTradeAndBestQuote.setBidAmount(bestQuote.getBidAmount());
    lastTradeAndBestQuote.setBidPrice(bestQuote.getBidPrice());
    lastTradeAndBestQuote.setAskAmount(bestQuote.getAskAmount());
    lastTradeAndBestQuote.setAskPrice(bestQuote.getAskPrice());
    lastTradeAndBestQuote.setLastTradePrice(lastTrade.getPrice());
    lastTradeAndBestQuote.setLastTradeAmount(lastTrade.getAmount());
    return lastTradeAndBestQuote;
  }

  @Override
  public Trade getLastTrade(String symbol) {
    List<Trade> trades = RestApiInvoker.callSync(requestImpl.getHistoricalTrade(symbol, null, 1));
    if (trades != null && trades.size() != 0) {
      return trades.get(trades.size() - 1);
    } else {
      return null;
    }
  }

  @Override
  public List<Trade> getHistoricalTrade(String symbol, int size) {
    return RestApiInvoker.callSync(requestImpl.getHistoricalTrade(symbol, null, size));
  }

  @Override
  public List<Trade> getTrade(String symbol) {
    return RestApiInvoker.callSync(requestImpl.getTrade(symbol));
  }

  @Override
  public TradeStatistics get24HTradeStatistics(String symbol) {
    return RestApiInvoker.callSync(requestImpl.get24HTradeStatistics(symbol));
  }

  @Override
  public ExchangeInfo getExchangeInfo() {
    List<Symbol> symbolList = RestApiInvoker.callSync(requestImpl.getSymbols());
    List<String> stringList = RestApiInvoker.callSync(requestImpl.getCurrencies());
    ExchangeInfo exchangeInfo = new ExchangeInfo();
    exchangeInfo.setSymbolList(symbolList);
    exchangeInfo.setCurrencies(stringList);
    return exchangeInfo;
  }

  public List<Symbol> getSymbols() {
    return RestApiInvoker.callSync(requestImpl.getSymbols());
  }

  public List<String> getCurrencies() {
    return RestApiInvoker.callSync(requestImpl.getCurrencies());
  }

  public List<Currency> getCurrencyInfo(String currency, Boolean authorizedUser) {
    return RestApiInvoker.callSync(requestImpl.getCurrencyInfo(currency,authorizedUser));
  }

  @Override
  public BestQuote getBestQuote(String symbol) {
    return RestApiInvoker.callSync(requestImpl.getBestQuote(symbol));
  }

  @Override
  public List<Withdraw> getWithdrawHistory(String currency, long fromId, int size) {
    return getWithdrawHistory(currency, fromId, size, null);
  }

  @Override
  public List<Withdraw> getWithdrawHistory(String currency, long fromId, int size, QueryDirection direction) {
    return RestApiInvoker.callSync(requestImpl.getWithdrawHistory(currency, fromId, size, direction));
  }

  @Override
  public List<Deposit> getDepositHistory(String currency, long fromId, int size) {
    return getDepositHistory(currency, fromId, size, null);
  }

  public List<Deposit> getDepositHistory(String currency, long fromId, int size, QueryDirection direction) {
    return RestApiInvoker.callSync(requestImpl.getDepositHistory(currency, fromId, size, direction));
  }

  public List<Deposit> getSubUserDepositHistory(SubUserDepositHistoryRequest request) {
    return RestApiInvoker.callSync(requestImpl.getSubUserDepositHistory(request));
  }

  @Override
  public Long transfer(TransferRequest transferRequest) {
    return RestApiInvoker.callSync(requestImpl.transfer(transferRequest));
  }

  @Override
  public Long transferFutures(TransferFuturesRequest request) {
    return RestApiInvoker.callSync(requestImpl.transferFutures(request));
  }
  @Override
  public long applyLoan(String symbol, String currency, BigDecimal amount) {
    return RestApiInvoker.callSync(requestImpl.applyLoan(symbol, currency, amount));
  }

  @Override
  public long repayLoan(long loadId, BigDecimal amount) {
    return RestApiInvoker.callSync(requestImpl.repayLoan(loadId, amount));
  }

  @Override
  public List<Loan> getLoanHistory(LoanOrderRequest loanOrderRequest) {
    return RestApiInvoker.callSync(requestImpl.getLoan(loanOrderRequest));
  }

  @Override
  public List<MarginLoanInfo> getLoanInfo(String symbols) {
    return RestApiInvoker.callSync(requestImpl.getLoanInfo(symbols));
  }

  @Override
  public long transferCrossMargin(CrossMarginTransferRequest request) {
    return RestApiInvoker.callSync(requestImpl.transferCrossMargin(request));
  }

  public long applyCrossMarginLoan(CrossMarginApplyLoanRequest request) {
    return RestApiInvoker.callSync(requestImpl.applyCrossMarginLoan(request));
  }

  public void repayCrossMarginLoan(CrossMarginRepayLoanRequest request) {
    RestApiInvoker.callSync(requestImpl.repayCrossMarginLoan(request));
  }

  public List<CrossMarginLoanOrder> getCrossMarginLoanHistory(CrossMarginLoanOrderRequest request) {
    return RestApiInvoker.callSync(requestImpl.getCrossMarginLoanHistory(request));
  }

  public CrossMarginAccount getCrossMarginAccount(CrossMarginAccountRequest request) {
    return RestApiInvoker.callSync(requestImpl.getCrossMarginAccount(request));
  }

  public List<CrossMarginLoanInfo> getCrossMarginLoanInfo() {
    return RestApiInvoker.callSync(requestImpl.getCrossMarginLoanInfo());
  }


  @Override
  public List<Account> getAccountBalance() {
    List<Account> accounts = RestApiInvoker.callSync(requestImpl.getAccounts());
    for (Account account : accounts) {
      List<Balance> balances = RestApiInvoker.callSync(requestImpl.getBalance(account));
      account.setBalances(balances);
    }
    return accounts;
  }

  @Override
  public Account getAccountBalance(AccountType accountType) {
    List<Account> accounts = RestApiInvoker.callSync(requestImpl.getAccounts());
    for (Account account : accounts) {
      if (account.getType() == accountType) {
        List<Balance> balances = RestApiInvoker.callSync(requestImpl.getBalance(account));
        account.setBalances(balances);
        return account;
      }
    }
    return null;
  }

  public Account getAccountBalance(AccountType accountType, String symbol) {
    if (AccountType.MARGIN != accountType) {
      return getAccountBalance(accountType);
    }
    if (symbol == null || symbol.trim().length() <= 0) {
      throw new HuobiApiException(HuobiApiException.INPUT_ERROR,"[INPUT] Margin Account Need Symbol");
    }
    List<Account> accounts = RestApiInvoker.callSync(requestImpl.getAccounts());
    for (Account account : accounts) {
      if (account.getType() == accountType && account.getSubtype().equals(symbol)) {
        List<Balance> balances = RestApiInvoker.callSync(requestImpl.getBalance(account));
        account.setBalances(balances);
        return account;
      }
    }
    return null;

  }

  @Override
  public List<AccountHistory> getAccountHistory(AccountHistoryRequest request) {
    return RestApiInvoker.callSync(requestImpl.getAccountHistory(request));
  }

  @Override
  public SubuserManagementResult subuserManagement(SubuserManagementRequest request) {
    return RestApiInvoker.callSync(requestImpl.subuserManagement(request));
  }

  @Override
  public SubUserCreationResult subuserCreation(SubUserCreationRequest request) {
    return RestApiInvoker.callSync(requestImpl.subuserCreation(request));
  }

  @Override
  public GetSubUserListResult getSubuserList(GetSubUserListRequest request) {
    return RestApiInvoker.callSync(requestImpl.getSubUserList(request));
  }

  @Override
  public SubUserState getSubuserState(Long subUid) {
    return RestApiInvoker.callSync(requestImpl.getSubUserState(subUid));
  }

  @Override
  public GetSubUserAccountListResult getSubuserAccountList(GetSubUserAccountListRequest request) {
    return RestApiInvoker.callSync(requestImpl.getSubuserAccountList(request));
  }

  @Override
  public SubUserTransferabilityResult subuserTransferability(SubUserTransferabilityRequest request) {
    return RestApiInvoker.callSync(requestImpl.subuserTransferability(request));
  }

  @Override
  public SubUserTradableMarketResult subuserTradableMarket(SubUserTradableMarketRequest request) {
    return RestApiInvoker.callSync(requestImpl.subuserTradableMarket(request));
  }

  @Override
  public SubUserApiKeyGenerationResult subuserApiKeyGeneration(SubUserApiKeyGenerationRequest request) {
    return RestApiInvoker.callSync(requestImpl.subuserApiKeyGeneration(request));
  }

  @Override
  public SubUserApiKeyModificationResult subuserApiKeyModification(SubUserApiKeyModificationRequest request) {
    return RestApiInvoker.callSync(requestImpl.subuserApiKeyModification(request));
  }

  public void subuserApiKeyDeletion(SubUserApiKeyDeletionRequest request) {
    RestApiInvoker.callSync(requestImpl.subuserApiKeyDeletion(request));
  }

  @Override
  public GetApiKeyListResult getApiKeyList(GetApiKeyListRequest request) {
    return RestApiInvoker.callSync(requestImpl.getApiKeyList(request));
  }


  @Override
  public Map<String, TradeStatistics> getTickers() {
    return RestApiInvoker.callSync(requestImpl.getTickers());
  }

  @Override
  public long createOrder(NewOrderRequest newOrderRequest) {
    return RestApiInvoker.callSync(requestImpl.createOrder(newOrderRequest));
  }

  @Override
  public List<CreateOrderResult> batchCreateOrder(List<NewOrderRequest> requestList) {
    return RestApiInvoker.callSync(requestImpl.batchCreateOrder(requestList));
  }

  @Override
  public List<Order> getOpenOrders(OpenOrderRequest openOrderRequest) {
    return RestApiInvoker.callSync(requestImpl.getOpenOrders(openOrderRequest));
  }

  @Override
  public void cancelOrder(String symbol, long orderId) {
    RestApiInvoker.callSync(requestImpl.cancelOrder(symbol, orderId));
  }

  @Override
  public void cancelOrderByClientOrderId(String symbol, String clientOrderId) {
    RestApiInvoker.callSync(requestImpl.cancelOrderByClientOrderId(symbol, clientOrderId));
  }

  @Override
  public BatchCancelResultV1 cancelOrders(String symbol, List<Long> orderIds) {

    BatchCancelRequest request = new BatchCancelRequest();
    request.setSymbol(symbol);
    request.setOrderIds(orderIds);

    return RestApiInvoker.callSync(requestImpl.cancelOrders(request));
  }

  public BatchCancelResultV1 cancelOrders(BatchCancelRequest BatchCancelRequest) {
    return RestApiInvoker.callSync(requestImpl.cancelOrders(BatchCancelRequest));
  }


  @Override
  public BatchCancelResult cancelOpenOrders(CancelOpenOrderRequest request) {
    return RestApiInvoker.callSync(requestImpl.cancelOpenOrders(request));
  }

  @Override
  public Order getOrder(String symbol, long orderId) {
    return RestApiInvoker.callSync(requestImpl.getOrder(symbol, orderId));
  }

  @Override
  public Order getOrderByClientOrderId(String symbol, String clientOrderId) {
    return RestApiInvoker.callSync(requestImpl.getOrderByClientOrderId(symbol, clientOrderId));
  }

  @Override
  public List<MatchResult> getMatchResults(String symbol, long orderId) {
    return RestApiInvoker.callSync(requestImpl.getMatchResults(symbol, orderId));
  }

  @Override
  public List<MatchResult> getMatchResults(MatchResultRequest matchResultRequest) {
    return RestApiInvoker.callSync(requestImpl.getMatchResults(matchResultRequest));
  }

  @Override
  public List<DepositAddress> getDepositAddress(String currency) {
    return RestApiInvoker.callSync(requestImpl.getDepositAddress(currency));
  }

  @Override
  public List<DepositAddress> getSubUserDepositAddress(Long subUid, String currency) {
    return RestApiInvoker.callSync(requestImpl.getSubUserDepositAddress(subUid, currency));
  }

  @Override
  public WithdrawQuota getWithdrawQuota(String currency) {
    return RestApiInvoker.callSync(requestImpl.getWithdrawQuota(currency));
  }

  @Override
  public long withdraw(WithdrawRequest withdrawRequest) {
    return RestApiInvoker.callSync(requestImpl.withdraw(withdrawRequest));
  }

  @Override
  public void cancelWithdraw(String currency, long withdrawId) {
    RestApiInvoker.callSync(requestImpl.cancelWithdraw(currency, withdrawId));
  }

  @Override
  public List<Order> getHistoricalOrders(HistoricalOrdersRequest req) {
    return RestApiInvoker.callSync(requestImpl.getHistoricalOrders(req));
  }

  @Override
  public List<Order> getOrders(OrdersRequest req) {
    return RestApiInvoker.callSync(requestImpl.getOrders(req));
  }

  @Override
  public List<Order> getOrderHistory(OrdersHistoryRequest req) {
    return RestApiInvoker.callSync(requestImpl.getOrderHistory(req));
  }

  @Override
  public List<FeeRate> getFeeRate(String symbol) {
    return RestApiInvoker.callSync(requestImpl.getFeeRate(symbol));
  }

  @Override
  public List<TransactFeeRate> getTransactFeeRate(String symbols) {
    return RestApiInvoker.callSync(requestImpl.getTransactFeeRate(symbols));
  }

  @Override
  public long transferBetweenParentAndSub(TransferMasterRequest request) {
    return RestApiInvoker.callSync(requestImpl.transferBetweenParentAndSub(request));
  }

  @Override
  public List<Balance> getCurrentUserAggregatedBalance() {
    return RestApiInvoker.callSync(requestImpl.getCurrentUserAggregatedBalance());
  }

  @Override
  public List<CompleteSubAccountInfo> getSpecifyAccountBalance(long subId) {
    return RestApiInvoker.callSync(requestImpl.getSpecifyAccountBalance(subId));
  }

  @Override
  public List<Candlestick> getEtfCandlestick(String symbol, CandlestickInterval interval, Integer limit) {
    return RestApiInvoker.callSync(requestImpl.getETFCandlestick(symbol, interval, limit));
  }

  @Override
  public void etfSwap(String etfSymbol, int amount, EtfSwapType swapType) {
    RestApiInvoker.callSync(requestImpl.etfSwap(etfSymbol, amount, swapType));
  }

  @Override
  public List<EtfSwapHistory> getEtfSwapHistory(String etfSymbol, int offset, int size) {
    return RestApiInvoker.callSync(requestImpl.getEtfSwapHistory(etfSymbol, offset, size));
  }

  @Override
  public EtfSwapConfig getEtfSwapConfig(String etfSymbol) {
    return RestApiInvoker.callSync(requestImpl.getEtfSwapConfig(etfSymbol));
  }

  @Override
  public List<MarginBalanceDetail> getMarginBalanceDetail(String symbol) {
    return RestApiInvoker.callSync(requestImpl.getMarginBalanceDetail(symbol));
  }

	@Override
	public List<AccountLedger> getAccountLedgers(AccountLedgerRequest request) {
		return RestApiInvoker.callSync(requestImpl.getAccountLedgeRequest(request));
	}

  public GetWithdrawAddressResult getAccountWithdrawAddressList(GetWithdrawAddressRequest request) {
    return RestApiInvoker.callSync(requestImpl.getAccountWithdrawAddressList(request));
  }

	@Override
	public AccountTransferResult accountTransfer(AccountTransferRequest request) {
    return RestApiInvoker.callSync(requestImpl.accountTransfer(request));
  }
	
	@Override
	public String getSystemStatus() {
		return RestApiInvoker.callSync(requestImpl.getSystemStatusRequest(), Boolean.FALSE);
	}
}
