package com.huobi.client.impl;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.impl.utils.UrlParamsBuilder;
import com.huobi.client.model.Account;
import com.huobi.client.model.AccountHistory;
import com.huobi.client.model.Balance;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.CompleteSubAccountInfo;
import com.huobi.client.model.CrossMarginAccount;
import com.huobi.client.model.CrossMarginLoanOrder;
import com.huobi.client.model.Currency;
import com.huobi.client.model.Deposit;
import com.huobi.client.model.DepositAddress;
import com.huobi.client.model.DepthEntry;
import com.huobi.client.model.EtfSwapConfig;
import com.huobi.client.model.EtfSwapHistory;
import com.huobi.client.model.FeeRate;
import com.huobi.client.model.Loan;
import com.huobi.client.model.MarginBalanceDetail;
import com.huobi.client.model.MatchResult;
import com.huobi.client.model.Order;
import com.huobi.client.model.PriceDepth;
import com.huobi.client.model.Symbol;
import com.huobi.client.model.Trade;
import com.huobi.client.model.TradeStatistics;
import com.huobi.client.model.UnitPrice;
import com.huobi.client.model.Withdraw;
import com.huobi.client.model.WithdrawQuota;
import com.huobi.client.model.enums.AccountState;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceType;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.enums.CrossMarginTransferType;
import com.huobi.client.model.enums.DealRole;
import com.huobi.client.model.enums.DepositState;
import com.huobi.client.model.enums.EtfStatus;
import com.huobi.client.model.enums.LoanOrderStates;
import com.huobi.client.model.enums.OrderSource;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.enums.QueryDirection;
import com.huobi.client.model.enums.StopOrderOperator;
import com.huobi.client.model.enums.TradeDirection;
import com.huobi.client.model.enums.WithdrawState;
import com.huobi.client.model.request.AccountHistoryRequest;
import com.huobi.client.model.request.CancelOpenOrderRequest;
import com.huobi.client.model.enums.EtfSwapType;
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
import com.huobi.client.model.request.TransferFuturesRequest;
import com.huobi.client.model.request.TransferMasterRequest;
import com.huobi.client.model.request.TransferRequest;
import com.huobi.client.model.request.WithdrawRequest;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

class RestApiRequestImpl {

  private String apiKey;
  private String secretKey;
  private String marketQueryUrl = "https://api.huobi.pro:443";
  private String tradingUrl = "https://api.huobi.pro:443";
  private RequestOptions options;
  private String tradingHost;

  RestApiRequestImpl(String apiKey, String secretKey, RequestOptions options) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;
    this.options = options;
    try {
      String host = new URL(this.options.getUrl()).getHost();
      this.tradingHost = host;
      if (host.indexOf("api") == 0) {
        this.marketQueryUrl = "https://" + host;
        this.tradingUrl = "https://" + host;
      } else {
        this.marketQueryUrl = "https://" + host + "/api";
        this.tradingUrl = "https://" + host + "/api";
      }
    } catch (Exception e) {
    }
  }

  private Request createRequestByGet(String address, UrlParamsBuilder builder) {
    return createRequestByGet(marketQueryUrl, address, builder);
  }

  private Request createRequestByGet(String url, String address, UrlParamsBuilder builder) {
    return createRequest(url, address, builder);
  }

  private Request createRequest(String url, String address, UrlParamsBuilder builder) {
    String requestUrl = url + address;
    if (builder != null) {
      if (builder.hasPostParam()) {
        return new Request.Builder().url(requestUrl).post(builder.buildPostBody())
            .addHeader("Content-Type", "application/json").build();
      } else {
        return new Request.Builder().url(requestUrl + builder.buildUrl())
            .addHeader("Content-Type", "application/x-www-form-urlencoded").build();
      }
    } else {
      return new Request.Builder().url(requestUrl)
          .addHeader("Content-Type", "application/x-www-form-urlencoded").build();
    }
  }

  private Request createRequestWithSignature(String url, String address,
      String host,
      UrlParamsBuilder builder) {
    if (builder == null) {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
          "[Invoking] Builder is null when create request with Signature");
    }
    String requestUrl = url + address;
    if (builder.hasPostParam()) {
      new ApiSignature().createSignature(apiKey, secretKey, "POST", host, address, builder);
      requestUrl += builder.buildUrl();
      return new Request.Builder().url(requestUrl).post(builder.buildPostBody())
          .addHeader("Content-Type", "application/json").build();
    } else {
      new ApiSignature().createSignature(apiKey, secretKey, "GET", host, address, builder);
      requestUrl += builder.buildUrl();
      return new Request.Builder().url(requestUrl)
          .addHeader("Content-Type", "application/x-www-form-urlencoded").build();
    }
  }

  private Request createRequestByPostWithSignature(String address, UrlParamsBuilder builder) {
    return createRequestWithSignature(tradingUrl, address, tradingHost, builder.setPostMode(true));
  }

  private Request createRequestByGetWithSignature(String address, UrlParamsBuilder builder) {
    return createRequestWithSignature(tradingUrl, address, tradingHost, builder);
  }

  RestApiRequest<Long> getExchangeTimestamp() {
    RestApiRequest<Long> request = new RestApiRequest<>();
    request.request = createRequestByGet("/v1/common/timestamp", null);
    request.jsonParser = (json ->
        TimeService.convertCSTInMillisecondToUTC(json.getLong("data")));
    return request;
  }

  RestApiRequest<List<Candlestick>> getCandlestick(
      String symbol, CandlestickInterval interval, Long startTime, Long endTime, Integer size) {
    InputChecker.checker()
        .checkSymbol(symbol)
        .checkRange(size, 1, 2000, "size")
        .shouldNotNull(interval, "CandlestickInterval");
    RestApiRequest<List<Candlestick>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol)
        .putToUrl("period", interval)
        .putToUrl("size", size)
        .putToUrl("start", startTime)
        .putToUrl("end", endTime);
    request.request = createRequestByGet("/market/history/kline", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Candlestick> res = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Candlestick candlestick = new Candlestick();
        candlestick.setId(item.getLong("id"));
        candlestick.setTimestamp(
            TimeService.convertCSTInSecondToUTC(item.getLong("id")));
        candlestick.setOpen(item.getBigDecimal("open"));
        candlestick.setClose(item.getBigDecimal("close"));
        candlestick.setLow(item.getBigDecimal("low"));
        candlestick.setHigh(item.getBigDecimal("high"));
        candlestick.setAmount(item.getBigDecimal("amount"));
        candlestick.setCount(item.getLong("count"));
        candlestick.setVolume(item.getBigDecimal("vol"));
        res.add(candlestick);
      });
      return res;
    });
    return request;
  }

  RestApiRequest<List<Account>> getAccounts() {
    RestApiRequest<List<Account>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    request.request = createRequestByGetWithSignature("/v1/account/accounts", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Account> res = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Account account = new Account();
        account.setId(item.getLong("id"));
        account.setType(AccountType.lookup(item.getString("type")));
        account.setState(AccountState.lookup(item.getString("state")));
        account.setSubtype(item.getStringOrDefault("subtype", ""));
        res.add(account);
      });
      return res;
    });
    return request;
  }

  RestApiRequest<List<AccountHistory>> getAccountHistory(AccountHistoryRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getAccountId(),"account-id");

    RestApiRequest<List<AccountHistory>> restApiRequest = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("account-id",request.getAccountId())
        .putToUrl("currency",request.getCurrency())
        .putToUrl("transact-types",request.getTypeString())
        .putToUrl("start-time",request.getStartTime())
        .putToUrl("end-time",request.getEndTime())
        .putToUrl("sort",request.getSort() == null ? null : request.getSort().getCode())
        .putToUrl("size",request.getSize())
        ;
    restApiRequest.request = createRequestByGetWithSignature("/v1/account/history", builder);
    restApiRequest.jsonParser = (jsonWrapper -> {
      List<AccountHistory> res = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        AccountHistory history = new AccountHistory();
        history.setAccountId(item.getLongOrDefault("account-id",0L));
        history.setCurrency(item.getStringOrDefault("currency",null));
        history.setTransactAmt(item.getBigDecimalOrDefault("transact-amt",null));
        history.setTransactType(item.getStringOrDefault("transact-type",null));
        history.setRecordId(item.getLongOrDefault("record-id",0L));
        history.setAvailBalance(item.getBigDecimalOrDefault("avail-balance",null));
        history.setAcctBalance(item.getBigDecimalOrDefault("acct-balance",null));
        history.setTransactTime(item.getLongOrDefault("transact-time",0L));
        res.add(history);
      });
      return res;
    });
    return restApiRequest;
  }

  RestApiRequest<PriceDepth> getPriceDepth(String symbol, Integer size) {
    InputChecker.checker().checkSymbol(symbol)
        .checkRange(size, 1, 150, "size");
    RestApiRequest<PriceDepth> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol)
        .putToUrl("type", "step0");
    request.request = createRequestByGet("/market/depth", builder);
    request.jsonParser = (jsonWrapper -> {
      JsonWrapper tick = jsonWrapper.getJsonObject("tick");
      PriceDepth dp = new PriceDepth();
      long ts = TimeService.convertCSTInMillisecondToUTC(tick.getLong("ts"));
      JsonWrapperArray bids = tick.getJsonArray("bids");
      JsonWrapperArray asks = tick.getJsonArray("asks");
      List<DepthEntry> bidList = new LinkedList<>();
      List<DepthEntry> askList = new LinkedList<>();
      for (int i = 0; i < size; i++) {
        JsonWrapperArray bidEntry = bids.getArrayAt(i);
        DepthEntry entry = new DepthEntry();
        entry.setPrice(bidEntry.getBigDecimalAt(0));
        entry.setAmount(bidEntry.getBigDecimalAt(1));
        bidList.add(entry);
      }
      for (int i = 0; i < size; i++) {
        JsonWrapperArray askEntry = asks.getArrayAt(i);
        DepthEntry entry = new DepthEntry();
        entry.setPrice(askEntry.getBigDecimalAt(0));
        entry.setAmount(askEntry.getBigDecimalAt(1));
        askList.add(entry);
      }
      dp.setBids(bidList);
      dp.setAsks(askList);
      dp.setTimestamp(ts);
      return dp;
    });
    return request;
  }

  RestApiRequest<List<Trade>> getHistoricalTrade(String symbol, String fromId, Integer size) {
    InputChecker.checker().checkSymbol(symbol).checkRange(size, 1, 2000, "size");
    RestApiRequest<List<Trade>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol)
        .putToUrl("size", size);  //.put("fromid", fromId)
    request.request = createRequestByGet("/market/history/trade", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Trade> res = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        JsonWrapperArray dataArrayIn = item.getJsonArray("data");
        dataArrayIn.forEach((itemIn) -> {
          Trade trade = new Trade();
          trade.setUniqueTradeId(itemIn.getLongOrDefault("trade-id",0));
          trade.setPrice(itemIn.getBigDecimal("price"));
          trade.setAmount(itemIn.getBigDecimal("amount"));
          trade.setTradeId(itemIn.getString("id"));
          trade.setTimestamp(TimeService.convertCSTInMillisecondToUTC(itemIn.getLong("ts")));
          trade.setDirection(TradeDirection.lookup(itemIn.getString("direction")));
          res.add(trade);
        });
      });
      return res;
    });
    return request;
  }

  RestApiRequest<List<Trade>> getTrade(String symbol) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<List<Trade>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol);
    request.request = createRequestByGet("/market/trade", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Trade> res = new LinkedList<>();

      JsonWrapper tick = jsonWrapper.getJsonObject("tick");
      JsonWrapperArray dataArray = tick.getJsonArray("data");
      dataArray.forEach((item) -> {
        Trade trade = new Trade();
        trade.setUniqueTradeId(item.getLongOrDefault("trade-id",0));
        trade.setPrice(item.getBigDecimal("price"));
        trade.setAmount(item.getBigDecimal("amount"));
        trade.setTradeId(item.getString("id"));
        trade.setTimestamp(TimeService.convertCSTInMillisecondToUTC(item.getLong("ts")));
        trade.setDirection(TradeDirection.lookup(item.getString("direction")));
        res.add(trade);
      });
      return res;
    });
    return request;
  }

  RestApiRequest<TradeStatistics> get24HTradeStatistics(String symbol) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<TradeStatistics> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol);
    request.request = createRequestByGet("/market/detail", builder);
    request.jsonParser = (jsonWrapper -> {
      TradeStatistics tradeStatistics = new TradeStatistics();
      tradeStatistics.setTimestamp(
          TimeService.convertCSTInMillisecondToUTC(jsonWrapper.getLong("ts")));
      JsonWrapper tick = jsonWrapper.getJsonObject("tick");
      tradeStatistics.setAmount(tick.getBigDecimal("amount"));
      tradeStatistics.setOpen(tick.getBigDecimal("open"));
      tradeStatistics.setClose(tick.getBigDecimal("close"));
      tradeStatistics.setHigh(tick.getBigDecimal("high"));
      tradeStatistics.setLow(tick.getBigDecimal("low"));
      //tradeStatistics.setId(tick.getLong("id"));
      tradeStatistics.setCount(tick.getLong("count"));
      tradeStatistics.setVolume(tick.getBigDecimal("vol"));
      return tradeStatistics;
    });
    return request;
  }

  RestApiRequest<List<Symbol>> getSymbols() {

    RestApiRequest<List<Symbol>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    request.request = createRequestByGet("/v1/common/symbols", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Symbol> symbolList = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Symbol symbol = new Symbol();
        symbol.setBaseCurrency(item.getString("base-currency"));
        symbol.setQuoteCurrency(item.getString("quote-currency"));
        symbol.setPricePrecision(item.getInteger("price-precision"));
        symbol.setAmountPrecision(item.getInteger("amount-precision"));
        symbol.setSymbolPartition(item.getString("symbol-partition"));
        symbol.setSymbol(item.getString("symbol"));
        symbol.setValuePrecision(item.getIntegerOrDefault("value-precision", null));
        symbol.setMinOrderAmt(item.getBigDecimalOrDefault("min-order-amt", null));
        symbol.setMaxOrderAmt(item.getBigDecimalOrDefault("max-order-amt", null));
        symbol.setMinOrderValue(item.getBigDecimalOrDefault("min-order-value", null));
        symbol.setLeverageRatio(item.getIntegerOrDefault("leverage-ratio", null));
        symbolList.add(symbol);

      });
      return symbolList;
    });
    return request;

  }

  RestApiRequest<List<String>> getCurrencies() {
    RestApiRequest<List<String>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    request.request = createRequestByGet("/v1/common/currencys", builder);
    request.jsonParser = (jsonWrapper -> {
      List<String> stringList = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEachAsString(stringList::add);
      return stringList;
    });
    return request;
  }

  RestApiRequest<List<Currency>> getCurrencyInfo(String currency, Boolean authorizedUser) {
    RestApiRequest<List<Currency>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("currency", currency)
        .putToUrl("authorizedUser", authorizedUser == null ? "true" : authorizedUser.toString());
    request.request = createRequestByGet("/v2/reference/currencies", builder);
    request.jsonParser = (jsonWrapper -> {

      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      List<Currency> list = new ArrayList<>();
      dataArray.forEach(item -> {
        Currency cu = item.getJson().toJavaObject(Currency.class);
        list.add(cu);
      });

      return list;
    });
    return request;
  }


  RestApiRequest<BestQuote> getBestQuote(String symbol) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<BestQuote> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol);
    request.request = createRequestByGet("/market/detail/merged", builder);
    request.jsonParser = (jsonWrapper -> {
      BestQuote bestQuote = new BestQuote();
      bestQuote.setTimestamp(TimeService.convertCSTInMillisecondToUTC(jsonWrapper.getLong("ts")));
      JsonWrapper jsonObject = jsonWrapper.getJsonObject("tick");
      JsonWrapperArray askArray = jsonObject.getJsonArray("ask");
      bestQuote.setAskPrice(askArray.getBigDecimalAt(0));
      bestQuote.setAskAmount(askArray.getBigDecimalAt(1));
      JsonWrapperArray bidArray = jsonObject.getJsonArray("bid");
      bestQuote.setBidPrice(bidArray.getBigDecimalAt(0));
      bestQuote.setBidAmount(bidArray.getBigDecimalAt(1));
      return bestQuote;
    });
    return request;
  }

  RestApiRequest<List<Withdraw>> getWithdrawHistory(String currency, Long fromId, Integer size) {
    return getWithdrawHistory(currency, fromId, size, null);
  }

  RestApiRequest<List<Withdraw>> getWithdrawHistory(String currency, Long fromId,
      Integer size, QueryDirection queryDirection) {
    InputChecker.checker().checkCurrency(currency).shouldNotNull(fromId, "fromId")
        .shouldNotNull(size, "size");
    RestApiRequest<List<Withdraw>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("currency", currency)
        .putToUrl("type", "withdraw")
        .putToUrl("from", fromId)
        .putToUrl("size", size)
        .putToUrl("direct", queryDirection);
    request.request = createRequestByGetWithSignature("/v1/query/deposit-withdraw", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Withdraw> withdraws = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Withdraw withdraw = new Withdraw();
        withdraw.setId(item.getLong("id"));
        withdraw.setCurrency(item.getString("currency"));
        withdraw.setChain(item.getStringOrDefault("chain",null));
        withdraw.setTxHash(item.getString("tx-hash"));
        withdraw.setAmount(item.getBigDecimal("amount"));
        withdraw.setAddress(item.getString("address"));
        withdraw.setAddressTag(item.getString("address-tag"));
        withdraw.setFee(item.getBigDecimal("fee"));
        withdraw.setWithdrawState(WithdrawState.lookup(item.getString("state")));
        withdraw.setCreatedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("created-at")));
        withdraw.setUpdatedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("updated-at")));
        withdraws.add(withdraw);
      });
      return withdraws;
    });
    return request;
  }

  RestApiRequest<List<Deposit>> getDepositHistory(String currency, Long fromId, Integer size) {
    return getDepositHistory(currency, fromId, size, null);
  }

  RestApiRequest<List<Deposit>> getDepositHistory(String currency, Long fromId,
      Integer size, QueryDirection queryDirection) {
    InputChecker.checker().checkCurrency(currency).shouldNotNull(fromId, "fromId")
        .shouldNotNull(size, "size");
    RestApiRequest<List<Deposit>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("currency", currency)
        .putToUrl("type", "deposit")
        .putToUrl("from", fromId)
        .putToUrl("size", size)
        .putToUrl("direct", queryDirection);
    request.request = createRequestByGetWithSignature("/v1/query/deposit-withdraw", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Deposit> deposits = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Deposit deposit = new Deposit();
        deposit.setId(item.getLong("id"));
        deposit.setCurrency(item.getString("currency"));
        deposit.setChain(item.getStringOrDefault("chain",null));
        deposit.setTxHash(item.getString("tx-hash"));
        deposit.setAmount(item.getBigDecimal("amount"));
        deposit.setAddress(item.getString("address"));
        deposit.setAddressTag(item.getString("address-tag"));
        deposit.setFee(item.getBigDecimal("fee"));
        deposit.setDepositState(DepositState.lookup(item.getString("state")));
        deposit.setCreatedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("created-at")));
        deposit.setUpdatedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("updated-at")));
        deposits.add(deposit);
      });
      return deposits;
    });
    return request;
  }

  RestApiRequest<List<Balance>> getBalance(Account account) {
    RestApiRequest<List<Balance>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    String url = String.format("/v1/account/accounts/%d/balance", account.getId());
    request.request = createRequestByGetWithSignature(url, builder);
    request.jsonParser = (jsonWrapper -> {
      List<Balance> balances = new LinkedList<>();
      JsonWrapper data = jsonWrapper.getJsonObject("data");
      JsonWrapperArray listArray = data.getJsonArray("list");
      listArray.forEach((item) -> {
        Balance balance = new Balance();
        balance.setBalance(item.getBigDecimal("balance"));
        balance.setCurrency(item.getString("currency"));
        balance.setType(BalanceType.lookup(item.getString("type")));
        balances.add(balance);
      });
      return balances;
    });
    return request;
  }

  RestApiRequest<Long> transfer(TransferRequest transferRequest) {
    InputChecker.checker().checkSymbol(transferRequest.symbol)
        .checkCurrency(transferRequest.currency)
        .shouldNotNull(transferRequest.from, "from")
        .shouldNotNull(transferRequest.to, "to")
        .shouldNotNull(transferRequest.amount, "amount");
    String address;
    if (transferRequest.from == AccountType.SPOT && transferRequest.to == AccountType.MARGIN) {
      address = "/v1/dw/transfer-in/margin";
    } else if (transferRequest.from == AccountType.MARGIN
        && transferRequest.to == AccountType.SPOT) {
      address = "/v1/dw/transfer-out/margin";
    } else {
      throw new HuobiApiException(HuobiApiException.INPUT_ERROR, "[Input] incorrect transfer type");
    }
    RestApiRequest<Long> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("currency", transferRequest.currency)
        .putToPost("symbol", transferRequest.symbol)
        .putToPost("amount", transferRequest.amount);
    request.request = createRequestByPostWithSignature(address, builder);
    request.jsonParser = (jsonWrapper -> {
      if ("ok".equals(jsonWrapper.getString("status"))) {
        return jsonWrapper.getLong("data");
      }
      return null;
    });
    return request;

  }

  RestApiRequest<Long> transferFutures(TransferFuturesRequest transferRequest) {
    InputChecker.checker()
        .checkCurrency(transferRequest.getCurrency())
        .shouldNotNull(transferRequest.getAmount(), "amount");

    RestApiRequest<Long> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("currency", transferRequest.getCurrency())
        .putToPost("amount", transferRequest.getAmount())
        .putToPost("type", transferRequest.getDirection().getDirection());
    request.request = createRequestByPostWithSignature("/v1/futures/transfer", builder);
    request.jsonParser = (jsonWrapper -> {
      if ("ok".equals(jsonWrapper.getString("status"))) {
        return jsonWrapper.getLong("data");
      }
      return null;
    });
    return request;

  }

  RestApiRequest<Long> applyLoan(String symbol, String currency, BigDecimal amount) {
    InputChecker.checker().checkSymbol(symbol).checkCurrency(currency)
        .shouldNotNull(amount, "amount");
    RestApiRequest<Long> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("currency", currency)
        .putToPost("symbol", symbol)
        .putToPost("amount", amount);
    request.request = createRequestByPostWithSignature("/v1/margin/orders", builder);
    request.jsonParser = (jsonWrapper -> {
      long id;
      id = jsonWrapper.getLong("data");
      return id;
    });
    return request;
  }

  RestApiRequest<Long> repayLoan(long loadId, BigDecimal amount) {

    InputChecker.checker().shouldNotNull(amount, "amount");
    RestApiRequest<Long> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("amount", amount);
    String url = String.format("/v1/margin/orders/%d/repay", loadId);

    request.request = createRequestByPostWithSignature(url, builder);
    request.jsonParser = (jsonWrapper -> jsonWrapper.getLong("data"));
    return request;
  }


  RestApiRequest<List<Loan>> getLoan(LoanOrderRequest loanOrderRequest) {
    InputChecker.checker().checkSymbol(loanOrderRequest.getSymbol());
    RestApiRequest<List<Loan>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", loanOrderRequest.getSymbol())
        .putToUrl("start-date", loanOrderRequest.getStartDate(), "yyyy-MM-dd")
        .putToUrl("end-date", loanOrderRequest.getEndDate(), "yyyy-MM-dd")
        .putToUrl("states", loanOrderRequest.getStates())
        .putToUrl("from", loanOrderRequest.getFromId())
        .putToUrl("size", loanOrderRequest.getSize())
        .putToUrl("direct", loanOrderRequest.getDirection());

    request.request = createRequestByGetWithSignature("/v1/margin/loan-orders", builder);
    request.jsonParser = (jsonWrapper -> {
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
        loan.setAccountType(AccountsInfoMap.getAccount(apiKey, item.getLong("account-id")).getType());
        loan.setUserId(item.getLong("user-id"));
        loan.setPaidCoin(item.getBigDecimalOrDefault("paid-point",null));
        loan.setPaidCoin(item.getBigDecimalOrDefault("paid-coin",null));
        loan.setDeductCurrency(item.getStringOrDefault("deduct-currency",null));
        loan.setDeductAmount(item.getBigDecimalOrDefault("deduct-amount",null));
        loan.setDeductRate(item.getBigDecimalOrDefault("deduct-rate",null));
        loan.setHourInterestRate(item.getBigDecimalOrDefault("hour-interest-rate",null));
        loan.setDayInterestRate(item.getBigDecimalOrDefault("day-interest-rate",null));
        loan.setAccruedTimestamp(TimeService.convertCSTInMillisecondToUTC(item.getLong("accrued-at")));
        loan.setCreatedTimestamp(TimeService.convertCSTInMillisecondToUTC(item.getLong("created-at")));
        loans.add(loan);
      });
      return loans;
    });
    return request;

  }


  RestApiRequest<Long> transferCrossMargin(CrossMarginTransferRequest transferRequest) {
    InputChecker.checker()
        .shouldNotNull(transferRequest.getType(),"type")
        .checkCurrency(transferRequest.getCurrency())
        .shouldNotNull(transferRequest.getAmount(), "amount");
    String address;
    if (transferRequest.getType() == CrossMarginTransferType.SPOT_TO_SUPER_MARGIN) {
      address = "/v1/cross-margin/transfer-in";
    } else if (transferRequest.getType() == CrossMarginTransferType.SUPER_MARGIN_TO_SPOT) {
      address = "/v1/cross-margin/transfer-out";
    } else {
      throw new HuobiApiException(HuobiApiException.INPUT_ERROR, "[Input] incorrect transfer type");
    }
    RestApiRequest<Long> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("currency", transferRequest.getCurrency())
        .putToPost("amount", transferRequest.getAmount());
    request.request = createRequestByPostWithSignature(address, builder);
    request.jsonParser = (jsonWrapper -> {
      if ("ok".equals(jsonWrapper.getString("status"))) {
        return jsonWrapper.getLong("data");
      }
      return null;
    });
    return request;

  }

  RestApiRequest<Long> applyCrossMarginLoan(CrossMarginApplyLoanRequest applyLoanRequest) {
    InputChecker.checker()
        .checkCurrency(applyLoanRequest.getCurrency())
        .shouldNotNull(applyLoanRequest.getAmount(), "amount");

    RestApiRequest<Long> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("currency", applyLoanRequest.getCurrency())
        .putToPost("amount", applyLoanRequest.getAmount());
    request.request = createRequestByPostWithSignature("/v1/cross-margin/orders", builder);
    request.jsonParser = (jsonWrapper -> {
      long id;
      id = jsonWrapper.getLong("data");
      return id;
    });
    return request;
  }

  RestApiRequest<Long> repayCrossMarginLoan(CrossMarginRepayLoanRequest repayLoanRequest) {

    InputChecker.checker()
        .shouldNotNull(repayLoanRequest.getOrderId(),"order-id")
        .shouldNotNull(repayLoanRequest, "amount");
    RestApiRequest<Long> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("amount", repayLoanRequest.getAmount());
    String url = String.format("/v1/cross-margin/orders/%d/repay", repayLoanRequest.getOrderId());

    request.request = createRequestByPostWithSignature(url, builder);
    request.jsonParser = (jsonWrapper -> {
      return 1L;
    });
    return request;
  }


  RestApiRequest<List<CrossMarginLoanOrder>> getCrossMarginLoanHistory(CrossMarginLoanOrderRequest loanOrderRequest) {

    RestApiRequest<List<CrossMarginLoanOrder>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("currency", loanOrderRequest.getCurrency())
        .putToUrl("start-date", loanOrderRequest.getStartDate(), "yyyy-MM-dd")
        .putToUrl("end-date", loanOrderRequest.getEndDate(), "yyyy-MM-dd")
        .putToUrl("states", loanOrderRequest.getState())
        .putToUrl("from", loanOrderRequest.getFrom())
        .putToUrl("size", loanOrderRequest.getSize())
        .putToUrl("direct", loanOrderRequest.getDirection());

    request.request = createRequestByGetWithSignature("/v1/margin/loan-orders", builder);
    request.jsonParser = (jsonWrapper -> {
      List<CrossMarginLoanOrder> loans = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {

        CrossMarginLoanOrder order = new CrossMarginLoanOrder();
        order.setId(item.getLong("id"));
        order.setAccountId(item.getLong("account-id"));
        order.setUserId(item.getLongOrDefault("user-id",0));
        order.setCurrency(item.getStringOrDefault("currency",null));
        order.setLoanBalance(item.getBigDecimalOrDefault("loan-balance",null));
        order.setLoanAmount(item.getBigDecimalOrDefault("loan-amount",null));
        order.setInterestBalance(item.getBigDecimalOrDefault("interest-balance",null));
        order.setInterestAmount(item.getBigDecimalOrDefault("interest-amount",null));
        order.setFilledPoints(item.getBigDecimalOrDefault("filled-points",null));
        order.setFilledHt(item.getBigDecimalOrDefault("filled-ht",null));
        order.setState(item.getStringOrDefault("state",null));
        order.setAccruedAt(item.getLongOrDefault("accrued-at",0));
        order.setCreatedAt(item.getLong("created-at"));

        loans.add(order);
      });
      return loans;
    });
    return request;

  }



  RestApiRequest<CrossMarginAccount> getCrossMarginAccount() {
    RestApiRequest<CrossMarginAccount> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    request.request = createRequestByGetWithSignature("/v1/cross-margin/accounts/balance", builder);
    request.jsonParser = (jsonWrapper -> {
      List<CrossMarginAccount> accountList = new LinkedList<>();
      JsonWrapper itemInData = jsonWrapper.getJsonObject("data");

        CrossMarginAccount account = new CrossMarginAccount();
        account.setId(itemInData.getLong("id"));
        account.setType(itemInData.getStringOrDefault("type",null));
        account.setState(itemInData.getStringOrDefault("state",null));
        account.setRiskRate(itemInData.getBigDecimalOrDefault("risk-rate",null));
        account.setAcctBalanceSum(itemInData.getBigDecimalOrDefault("acct-balance-sum",null));
        account.setDebtBalanceSum(itemInData.getBigDecimalOrDefault("debt-balance-sum",null));
        JsonWrapperArray array = itemInData.getJsonArray("list");
        List<Balance> balanceList = new ArrayList<>();
        array.forEach(balanceItem->{
          Balance balance = new Balance();
          balance.setCurrency(balanceItem.getStringOrDefault("currency",null));
          balance.setType(BalanceType.lookup(balanceItem.getString("type")));
          balance.setBalance(balanceItem.getBigDecimalOrDefault("balance",null));
          balanceList.add(balance);
        });
        account.setList(balanceList);

      return account;
    });
    return request;
  }



  RestApiRequest<Long> createOrder(NewOrderRequest newOrderRequest) {
    InputChecker.checker().checkSymbol(newOrderRequest.getSymbol())
        .shouldNotNull(newOrderRequest.getAccountType(), "AccountType")
        .shouldNotNull(newOrderRequest.getAmount(), "Amount")
        .shouldNotNull(newOrderRequest.getType(), "Type");

    if (newOrderRequest.getType() == OrderType.SELL_LIMIT
        || newOrderRequest.getType() == OrderType.BUY_LIMIT
        || newOrderRequest.getType() == OrderType.BUY_LIMIT_MAKER
        || newOrderRequest.getType() == OrderType.SELL_LIMIT_MAKER) {
      InputChecker.checker()
          .shouldNotNull(newOrderRequest.getPrice(), "Price");
    }
    if (newOrderRequest.getType() == OrderType.SELL_MARKET
        || newOrderRequest.getType() == OrderType.BUY_MARKET) {
      InputChecker.checker()
          .shouldNull(newOrderRequest.getPrice(), "Price");
    }
    Account account = AccountsInfoMap.getUser(apiKey).getAccount(newOrderRequest.getAccountType(), newOrderRequest.getSymbol());
    if (account == null) {
      throw new HuobiApiException(HuobiApiException.INPUT_ERROR, "[Input] No such account");
    }
    RestApiRequest<Long> request = new RestApiRequest<>();
    String source = "api";
    if (newOrderRequest.getAccountType() == AccountType.MARGIN) {
      source = "margin-api";
    } else if (newOrderRequest.getAccountType() == AccountType.SUPER_MARGIN) {
      source = "super-margin-api";
    }
    String stopOp = newOrderRequest.getOperator() != null ? newOrderRequest.getOperator().getOperator() : "";
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("account-id", account.getId())
        .putToPost("amount", newOrderRequest.getAmount())
        .putToPost("price", newOrderRequest.getPrice())
        .putToPost("symbol", newOrderRequest.getSymbol())
        .putToPost("type", newOrderRequest.getType())
        .putToPost("source", source)
        .putToPost("client-order-id", newOrderRequest.getClientOrderId())
        .putToPost("stop-price", newOrderRequest.getStopPrice())
        .putToPost("operator", stopOp);
    request.request = createRequestByPostWithSignature("/v1/order/orders/place", builder);
    request.jsonParser = (json -> {
      String data = json.getString("data");
      return Long.valueOf(data);
    });
    return request;
  }

  RestApiRequest<List<Order>> getOpenOrders(OpenOrderRequest openOrderRequest) {
    InputChecker.checker().checkSymbol(openOrderRequest.getSymbol())
        .shouldNotNull(openOrderRequest.getAccountType(), "accountType")
        .checkRange(openOrderRequest.getSize(), 1, 2000, "size");
    AccountType accountType = openOrderRequest.getAccountType();
    Account account = AccountsInfoMap.getUser(apiKey).getAccount(accountType, openOrderRequest.getSymbol());
    if (account == null) {
      throw new HuobiApiException(HuobiApiException.INPUT_ERROR, "[Input] No such account");
    }
    RestApiRequest<List<Order>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("account-id", account.getId())
        .putToUrl("symbol", openOrderRequest.getSymbol())
        .putToUrl("side", openOrderRequest.getSide())
        .putToUrl("size", openOrderRequest.getSize())
        .putToUrl("direct", openOrderRequest.getDirect())
        .putToUrl("from", openOrderRequest.getFrom());

    request.request = createRequestByGetWithSignature("/v1/order/openOrders", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Order> orderList = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Order order = new Order();
        order.setOrderId(item.getLong("id"));
        order.setSymbol(item.getString("symbol"));
        order.setPrice(item.getBigDecimal("price"));
        order.setAmount(item.getBigDecimal("amount"));
        order.setAccountType(
            AccountsInfoMap.getAccount(apiKey, item.getLong("account-id")).getType());
        order.setCreatedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("created-at")));
        order.setType(OrderType.lookup(item.getString("type")));
        order.setFilledAmount(item.getBigDecimal("filled-amount"));
        order.setFilledCashAmount(item.getBigDecimal("filled-cash-amount"));
        order.setFilledFees(item.getBigDecimal("filled-fees"));
        order.setSource(OrderSource.lookup(item.getString("source")));
        order.setState(OrderState.lookup(item.getString("state")));
        order.setStopPrice(item.getBigDecimalOrDefault("stop-price", null));
        order.setOperator(StopOrderOperator.find(item.getStringOrDefault("operator", null)));
        orderList.add(order);
      });
      return orderList;
    });
    return request;
  }

  RestApiRequest<Void> cancelOrder(String symbol, long orderId) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<Void> request = new RestApiRequest<>();
    String url = String.format("/v1/order/orders/%d/submitcancel", orderId);
    request.request = createRequestByPostWithSignature(url, UrlParamsBuilder.build());
    request.jsonParser = (json -> (null));
    return request;
  }

  RestApiRequest<Void> cancelOrderByClientOrderId(String symbol, String clientOrderId) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<Void> request = new RestApiRequest<>();
    String url = "/v1/order/orders/submitCancelClientOrder";
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    builder.putToPost("client-order-id", clientOrderId);
    request.request = createRequestByPostWithSignature(url, builder);
    request.jsonParser = (json -> (null));
    return request;
  }

  RestApiRequest<Void> cancelOrders(String symbol, List<Long> orderIds) {
    InputChecker.checker()
        .checkSymbol(symbol)
        .shouldNotNull(orderIds, "orderIds")
        .checkList(orderIds, 1, 50, "orderIds");
    RestApiRequest<Void> request = new RestApiRequest<>();
    List<String> stringList = new LinkedList<>();
    for (Object obj : orderIds) {
      stringList.add(obj.toString());
    }
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("order-ids", stringList);
    request.request = createRequestByPostWithSignature("/v1/order/orders/batchcancel", builder);
    request.jsonParser = (json -> (null));
    return request;
  }

  RestApiRequest<BatchCancelResult> cancelOpenOrders(
      CancelOpenOrderRequest cancelOpenOrderRequest) {
    InputChecker.checker()
        .checkSymbol(cancelOpenOrderRequest.getSymbol())
        .shouldNotNull(cancelOpenOrderRequest.getAccountType(), "AccountType");
    Account account = AccountsInfoMap.getUser(apiKey)
        .getAccount(cancelOpenOrderRequest.getAccountType(), cancelOpenOrderRequest.getSymbol());
    if (account == null) {
      throw new HuobiApiException(HuobiApiException.INPUT_ERROR, "[Input] No such account");
    }
    RestApiRequest<BatchCancelResult> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("account-id", account.getId())
        .putToPost("symbol", cancelOpenOrderRequest.getSymbol())
        .putToPost("side", cancelOpenOrderRequest.getSide())
        .putToPost("size", cancelOpenOrderRequest.getSize());
    request.request = createRequestByPostWithSignature("/v1/order/orders/batchCancelOpenOrders",
        builder);
    request.jsonParser = (jsonWrapper -> {
      BatchCancelResult batchCancelResult = new BatchCancelResult();
      JsonWrapper data = jsonWrapper.getJsonObject("data");
      batchCancelResult.setSuccessCount(data.getInteger("success-count"));
      batchCancelResult.setFailedCount(data.getInteger("failed-count"));
      return batchCancelResult;
    });
    return request;
  }

  RestApiRequest<Order> getOrder(String symbol, long orderId) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<Order> request = new RestApiRequest<>();
    String url = String.format("/v1/order/orders/%d", orderId);
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    request.request = createRequestByGetWithSignature(url, builder);
    request.jsonParser = getOrderParser();
    return request;
  }

  RestApiRequest<Order> getOrderByClientOrderId(String symbol, String clientOrderId) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<Order> request = new RestApiRequest<>();
    String url = "/v1/order/orders/getClientOrder";
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    builder.putToUrl("clientOrderId", clientOrderId);
    request.request = createRequestByGetWithSignature(url, builder);
    request.jsonParser = getOrderParser();
    return request;
  }

  RestApiRequest<List<MatchResult>> getMatchResults(String symbol, long orderId) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<List<MatchResult>> request = new RestApiRequest<>();
    String url = String.format("/v1/order/orders/%d/matchresults", orderId);
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    request.request = createRequestByGetWithSignature(url, builder);
    request.jsonParser = getMatchResultListParser();
    return request;
  }

  RestApiRequest<List<MatchResult>> getMatchResults(MatchResultRequest matchResultRequest) {
    InputChecker.checker().checkSymbol(matchResultRequest.getSymbol())
        .checkRange(matchResultRequest.getSize(), 1, 100, "size");
    RestApiRequest<List<MatchResult>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", matchResultRequest.getSymbol())
        .putToUrl("types", matchResultRequest.getType())
        .putToUrl("start-date", matchResultRequest.getStartDate(), "yyyy-MM-dd")
        .putToUrl("end-date", matchResultRequest.getEndDate(), "yyyy-MM-dd")
        .putToUrl("from", matchResultRequest.getFrom())
        //.putToUrl("direct", "prev")
        .putToUrl("size", matchResultRequest.getSize());
    request.request = createRequestByGetWithSignature("/v1/order/matchresults", builder);
    request.jsonParser = getMatchResultListParser();
    return request;
  }


  RestApiRequest<List<DepositAddress>> getDepositAddress(String currency) {
    InputChecker.checker()
        .checkCurrency(currency);
    RestApiRequest<List<DepositAddress>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("currency", currency);

    request.request = createRequestByGetWithSignature("/v2/account/deposit/address", builder);
    request.jsonParser = (jsonWrapper -> {

      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      List<DepositAddress> addressList = new ArrayList<>();
      dataArray.forEach(item ->{
        DepositAddress address = item.getJson().toJavaObject(DepositAddress.class);
        addressList.add(address);
      });
      return addressList;
    });
    return request;
  }


  RestApiRequest<WithdrawQuota> getWithdrawQuota(String currency) {
    InputChecker.checker()
        .checkCurrency(currency);
    RestApiRequest<WithdrawQuota> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("currency", currency);

    request.request = createRequestByGetWithSignature("/v2/account/withdraw/quota", builder);
    request.jsonParser = (jsonWrapper -> {
      JsonWrapper data = jsonWrapper.getJsonObject("data");
      return data.getJson().toJavaObject(WithdrawQuota.class);
    });
    return request;
  }

  RestApiRequest<Long> withdraw(WithdrawRequest withdrawRequest) {
    InputChecker.checker()
        .checkCurrency(withdrawRequest.getCurrency())
        .shouldNotNull(withdrawRequest.getAddress(), "address")
        .shouldNotNull(withdrawRequest.getAmount(), "amount");
    RestApiRequest<Long> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("address", withdrawRequest.getAddress())
        .putToPost("amount", withdrawRequest.getAmount())
        .putToPost("currency", withdrawRequest.getCurrency())
        .putToPost("chain",withdrawRequest.getChain())
        .putToPost("fee", withdrawRequest.getFee())
        .putToPost("addr-tag", withdrawRequest.getAddressTag());
    request.request = createRequestByPostWithSignature("/v1/dw/withdraw/api/create", builder);
    request.jsonParser = (jsonWrapper -> jsonWrapper.getLong("data"));
    return request;
  }

  RestApiRequest<Void> cancelWithdraw(String currency, long withdrawId) {
    InputChecker.checker().checkSymbol(currency);
    RestApiRequest<Void> request = new RestApiRequest<>();
    String url = String.format("/v1/dw/withdraw-virtual/%d/cancel", withdrawId);
    UrlParamsBuilder builder = UrlParamsBuilder.build().setPostMode(true);
    request.request = createRequestByPostWithSignature(url, builder);
    request.jsonParser = (json -> null);
    return request;
  }

  RestApiRequest<List<Order>> getHistoricalOrders(HistoricalOrdersRequest req) {
    return getOrders(new OrdersRequest(req));
  }

  RestApiRequest<List<Order>> getOrders(OrdersRequest req) {
    InputChecker.checker().checkSymbol(req.getSymbol())
        .shouldNotNull(req.getStates(), "states");
    RestApiRequest<List<Order>> request = new RestApiRequest<>();

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", req.getSymbol())
        .putToUrl("types", req.getTypesString())
        .putToUrl("start-date", req.getStartDate(), "yyyy-MM-dd")
        .putToUrl("end-date", req.getEndDate(), "yyyy-MM-dd")
        .putToUrl("from", req.getStartId())
        .putToUrl("states", req.getStatesString())
        .putToUrl("size", req.getSize())
        .putToUrl("direct", req.getDirect());
    request.request = createRequestByGetWithSignature("/v1/order/orders", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Order> orderList = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Order order = new Order();
        order.setAccountType(
            AccountsInfoMap.getAccount(apiKey, item.getLong("account-id")).getType());
        order.setAmount(item.getBigDecimal("amount"));
        order.setCanceledTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLongOrDefault("canceled-at", 0)));
        order.setFinishedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLongOrDefault("finished-at", 0)));
        order.setOrderId(item.getLong("id"));
        order.setSymbol(item.getString("symbol"));
        order.setPrice(item.getBigDecimal("price"));
        order.setCreatedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("created-at")));
        order.setType(OrderType.lookup(item.getString("type")));
        order.setFilledAmount(item.getBigDecimal("field-amount"));
        order.setFilledCashAmount(item.getBigDecimal("field-cash-amount"));
        order.setFilledFees(item.getBigDecimal("field-fees"));
        order.setSource(OrderSource.lookup(item.getString("source")));
        order.setState(OrderState.lookup(item.getString("state")));
        order.setStopPrice(item.getBigDecimalOrDefault("stop-price", null));
        order.setOperator(StopOrderOperator.find(item.getStringOrDefault("operator", null)));
        orderList.add(order);
      });
      return orderList;
    });
    return request;
  }


  RestApiRequest<List<Order>> getOrderHistory(OrdersHistoryRequest req) {
    RestApiRequest<List<Order>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", req.getSymbol())
        .putToUrl("start-date", req.getStartDate(), "yyyy-MM-dd")
        .putToUrl("end-date", req.getEndDate(), "yyyy-MM-dd")
        .putToUrl("size", req.getSize())
        .putToUrl("direct", req.getDirect());
    request.request = createRequestByGetWithSignature("/v1/order/history", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Order> orderList = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Order order = new Order();
        order.setAccountType(
            AccountsInfoMap.getAccount(apiKey, item.getLong("account-id")).getType());
        order.setAmount(item.getBigDecimal("amount"));
        order.setCanceledTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLongOrDefault("canceled-at", 0)));
        order.setFinishedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLongOrDefault("finished-at", 0)));
        order.setOrderId(item.getLong("id"));
        order.setSymbol(item.getString("symbol"));
        order.setPrice(item.getBigDecimal("price"));
        order.setCreatedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("created-at")));
        order.setType(OrderType.lookup(item.getString("type")));
        order.setFilledAmount(item.getBigDecimal("field-amount"));
        order.setFilledCashAmount(item.getBigDecimal("field-cash-amount"));
        order.setFilledFees(item.getBigDecimal("field-fees"));
        order.setSource(OrderSource.lookup(item.getString("source")));
        order.setState(OrderState.lookup(item.getString("state")));
        order.setStopPrice(item.getBigDecimalOrDefault("stop-price", null));
        order.setOperator(StopOrderOperator.find(item.getStringOrDefault("operator", null)));
        orderList.add(order);
      });
      return orderList;
    });
    return request;
  }


  RestApiRequest<List<FeeRate>> getFeeRate(String symbol) {
    RestApiRequest<List<FeeRate>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbols", symbol);
    request.request = createRequestByGetWithSignature("/v1/fee/fee-rate/get", builder);
    request.jsonParser = (jsonWrapper -> {
      List<FeeRate> rateList = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        FeeRate rate = new FeeRate();
        rate.setSymbol(item.getString("symbol"));
        rate.setMakerFee(item.getBigDecimalOrDefault("maker-fee", null));
        rate.setTakerFee(item.getBigDecimalOrDefault("taker-fee", null));
        rateList.add(rate);
      });
      return rateList;
    });
    return request;
  }

  RestApiRequest<Long> transferBetweenParentAndSub(TransferMasterRequest req) {
    InputChecker.checker()
        .checkCurrency(req.getCurrency())
        .shouldNotNull(req.getAmount(), "amount")
        .shouldNotNull(req.getSubUid(), "sub-uid")
        .shouldNotNull(req.getType(), "type");
    RestApiRequest<Long> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("sub-uid", req.getSubUid())
        .putToPost("amount", req.getAmount())
        .putToPost("currency", req.getCurrency())
        .putToPost("type", req.getType());
    request.request = createRequestByPostWithSignature("/v1/subuser/transfer", builder);
    request.jsonParser = (jsonWrapper -> jsonWrapper.getLong("data"));
    return request;
  }

  RestApiRequest<List<Balance>> getCurrentUserAggregatedBalance() {
    RestApiRequest<List<Balance>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    request.request = createRequestByGetWithSignature("/v1/subuser/aggregate-balance", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Balance> balances = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Balance balance = new Balance();
        balance.setCurrency(item.getString("currency"));
        balance.setBalance(item.getBigDecimal("balance"));
        balances.add(balance);
      });
      return balances;
    });
    return request;
  }

  RestApiRequest<List<CompleteSubAccountInfo>> getSpecifyAccountBalance(long subId) {
    RestApiRequest<List<CompleteSubAccountInfo>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    String url = String.format("/v1/account/accounts/%d", subId);
    request.request = createRequestByGetWithSignature(url, builder);
    request.jsonParser = (jsonWrapper -> {
      List<CompleteSubAccountInfo> completeSubAccountInfos = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        CompleteSubAccountInfo completeSubAccountInfo = new CompleteSubAccountInfo();
        completeSubAccountInfo.setId(item.getLong("id"));
        completeSubAccountInfo.setType(AccountType.lookup(item.getString("type")));
        JsonWrapperArray list = item.getJsonArray("list");
        List<Balance> balances = new LinkedList<>();
        list.forEach((val) -> {
          Balance balance = new Balance();
          balance.setCurrency(val.getString("currency"));
          balance.setType(BalanceType.lookup(val.getString("type")));
          balance.setBalance(val.getBigDecimal("balance"));
          balances.add(balance);
        });
        completeSubAccountInfo.setBalances(balances);
        completeSubAccountInfos.add(completeSubAccountInfo);
      });
      return completeSubAccountInfos;
    });
    return request;
  }

  RestApiRequest<List<Candlestick>> getETFCandlestick(
      String symbol, CandlestickInterval interval, Integer size) {
    InputChecker.checker()
        .checkETF(symbol)
        .checkRange(size, 1, 2000, "size")
        .shouldNotNull(interval, "interval");

    RestApiRequest<List<Candlestick>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol)
        .putToUrl("period", interval)
        .putToUrl("limit", size);
    request.request = createRequestByGet("/quotation/market/history/kline", builder);
    request.jsonParser = (jsonWrapper -> {
      List<Candlestick> res = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        Candlestick candlestick = new Candlestick();
        candlestick.setId(item.getLong("id"));
        candlestick.setTimestamp(
            TimeService.convertCSTInSecondToUTC(item.getLong("id")));
        candlestick.setOpen(item.getBigDecimal("open"));
        candlestick.setClose(item.getBigDecimal("close"));
        candlestick.setLow(item.getBigDecimal("low"));
        candlestick.setHigh(item.getBigDecimal("high"));
        candlestick.setAmount(item.getBigDecimal("amount"));
        candlestick.setCount(0);
        candlestick.setVolume(item.getBigDecimal("vol"));
        res.add(candlestick);
      });
      return res;
    });
    return request;
  }

  RestApiRequest<EtfSwapConfig> getEtfSwapConfig(String etfSymbol) {
    InputChecker.checker().checkSymbol(etfSymbol);
    RestApiRequest<EtfSwapConfig> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("etf_name", etfSymbol);
    request.request = createRequestByGet("/etf/swap/config", builder);
    request.jsonParser = (jsonWrapper -> {
      JsonWrapper data = jsonWrapper.getJsonObject("data");
      EtfSwapConfig etfSwapConfig = new EtfSwapConfig();
      etfSwapConfig.setPurchaseMaxAmount(data.getInteger("purchase_max_amount"));
      etfSwapConfig.setPurchaseMinAmount(data.getInteger("purchase_min_amount"));
      etfSwapConfig.setRedemptionMaxAmount(data.getInteger("redemption_max_amount"));
      etfSwapConfig.setRedemptionMinAmount(data.getInteger("redemption_min_amount"));
      etfSwapConfig.setPurchaseFeeRate(data.getBigDecimal("purchase_fee_rate"));
      etfSwapConfig.setRedemptionFeeRate(data.getBigDecimal("redemption_fee_rate"));
      etfSwapConfig.setStatus(
          EtfStatus.lookup(Integer.toString(data.getInteger("etf_status"))));
      JsonWrapperArray unitPrices = data.getJsonArray("unit_price");
      List<UnitPrice> unitPriceList = new LinkedList<>();
      unitPrices.forEach((item) -> {
        UnitPrice unitPrice = new UnitPrice();
        unitPrice.setCurrency(item.getString("currency"));
        unitPrice.setAmount(item.getBigDecimal("amount"));
        unitPriceList.add(unitPrice);
      });
      etfSwapConfig.setUnitPriceList(unitPriceList);
      return etfSwapConfig;
    });
    return request;
  }

  RestApiRequest<Void> etfSwap(String etfSymbol, int amount, EtfSwapType swapType) {
    InputChecker.checker().checkSymbol(etfSymbol).shouldNotNull(swapType, "swapType");
    RestApiRequest<Void> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("etf_name", etfSymbol)
        .putToPost("amount", amount);
    if (swapType == EtfSwapType.ETF_SWAP_IN) {
      request.request = createRequestByPostWithSignature("/etf/swap/in", builder);
    } else if (swapType == EtfSwapType.ETF_SWAP_OUT) {
      request.request = createRequestByPostWithSignature("/etf/swap/out", builder);
    }
    request.jsonParser = (json -> (null));
    return request;
  }

  RestApiRequest<List<EtfSwapHistory>> getEtfSwapHistory(String etfSymbol, int offset, int size) {
    InputChecker.checker()
        .checkSymbol(etfSymbol)
        .checkRange(size, 1, 100, "size")
        .greaterOrEqual(offset, 0, "offset");
    RestApiRequest<List<EtfSwapHistory>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("etf_name", etfSymbol)
        .putToUrl("offset", offset)
        .putToUrl("limit", size);
    request.request = createRequestByGetWithSignature("/etf/swap/list", builder);
    request.jsonParser = (jsonWrapper -> {
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
          UnitPrice unitPrice = new UnitPrice();
          unitPrice.setAmount(currency.getBigDecimal("amount"));
          unitPrice.setCurrency(currency.getString("currency"));
          usedCurrencyList.add(unitPrice);
        });
        etfSwapHistory.setUsedCurrencyList(usedCurrencyList);
        JsonWrapperArray obtainCurrencyArray = detail.getJsonArray("obtain_currency_list");
        List<UnitPrice> obtainCurrencyList = new LinkedList<>();
        obtainCurrencyArray.forEach((currency) -> {
          UnitPrice unitPrice = new UnitPrice();
          unitPrice.setAmount(currency.getBigDecimal("amount"));
          unitPrice.setCurrency(currency.getString("currency"));
          obtainCurrencyList.add(unitPrice);
        });
        etfSwapHistory.setObtainCurrencyList(obtainCurrencyList);
        etfSwapHistoryList.add(etfSwapHistory);
      });
      return etfSwapHistoryList;
    });
    return request;
  }

  RestApiRequest<List<MarginBalanceDetail>> getMarginBalanceDetail(String symbol) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<List<MarginBalanceDetail>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol);
    request.request = createRequestByGetWithSignature("/v1/margin/accounts/balance", builder);
    request.jsonParser = (jsonWrapper -> {
      List<MarginBalanceDetail> marginBalanceDetailList = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((itemInData) -> {
        MarginBalanceDetail marginBalanceDetail = new MarginBalanceDetail();
        marginBalanceDetail.setId(itemInData.getLong("id"));
        marginBalanceDetail.setType(AccountType.lookup(itemInData.getString("type")));
        marginBalanceDetail.setSymbol(itemInData.getString("symbol"));
        marginBalanceDetail.setFlPrice(itemInData.getBigDecimal("fl-price"));
        marginBalanceDetail.setFlType(itemInData.getString("fl-type"));
        marginBalanceDetail.setState(AccountState.lookup(itemInData.getString("state")));
        marginBalanceDetail.setRiskRate(itemInData.getBigDecimal("risk-rate"));
        List<Balance> balanceList = new LinkedList<>();
        JsonWrapperArray listArray = itemInData.getJsonArray("list");
        listArray.forEach((itemInList) -> {
          Balance balance = new Balance();
          balance.setCurrency(itemInList.getString("currency"));
          balance.setType(BalanceType.lookup(itemInList.getString("type")));
          balance.setBalance(itemInList.getBigDecimal("balance"));
          balanceList.add(balance);
        });
        marginBalanceDetail.setSubAccountBalance(balanceList);
        marginBalanceDetailList.add(marginBalanceDetail);
      });
      return marginBalanceDetailList;
    });
    return request;
  }

  RestApiRequest<Map<String, TradeStatistics>> getTickers() {
    RestApiRequest<Map<String, TradeStatistics>> request = new RestApiRequest<>();
    request.request = createRequestByGet("/market/tickers", UrlParamsBuilder.build());
    request.jsonParser = (jsonWrapper -> {
      Map<String, TradeStatistics> map = new HashMap<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      long ts = TimeService.convertCSTInMillisecondToUTC(jsonWrapper.getLong("ts"));
      dataArray.forEach(item -> {
        TradeStatistics statistics = new TradeStatistics();
        statistics.setTimestamp(ts);
        statistics.setAmount(item.getBigDecimal("amount"));
        statistics.setOpen(item.getBigDecimal("open"));
        statistics.setClose(item.getBigDecimal("close"));
        statistics.setHigh(item.getBigDecimal("high"));
        statistics.setLow(item.getBigDecimal("low"));
        statistics.setCount(item.getLong("count"));
        statistics.setVolume(item.getBigDecimal("vol"));
        map.put(item.getString("symbol"), statistics);
      });
      return map;
    });
    return request;
  }


  public RestApiJsonParser<Order> getOrderParser() {
    return (jsonWrapper -> {
      JsonWrapper data = jsonWrapper.getJsonObject("data");
      Order order = new Order();
      order.setSymbol(data.getString("symbol"));
      order.setOrderId(data.getLong("id"));
      order
          .setAccountType(AccountsInfoMap.getAccount(apiKey, data.getLong("account-id")).getType());
      order.setAmount(data.getBigDecimal("amount"));
      order.setCanceledTimestamp(
          TimeService.convertCSTInMillisecondToUTC(data.getLong("canceled-at")));
      order.setCreatedTimestamp(
          TimeService.convertCSTInMillisecondToUTC(data.getLong("created-at")));
      order.setFinishedTimestamp(
          TimeService.convertCSTInMillisecondToUTC(data.getLong("finished-at")));
      order.setFilledAmount(data.getBigDecimal("field-amount"));
      order.setFilledCashAmount(data.getBigDecimal("field-cash-amount"));
      order.setFilledFees(data.getBigDecimal("field-fees"));
      order.setPrice(data.getBigDecimal("price"));
      order.setSource(OrderSource.lookup(data.getString("source")));
      order.setState(OrderState.lookup(data.getString("state")));
      order.setType(OrderType.lookup(data.getString("type")));
      order.setStopPrice(data.getBigDecimalOrDefault("stop-price", null));
      order.setOperator(StopOrderOperator.find(data.getStringOrDefault("operator", null)));
      return order;
    });
  }

  public RestApiJsonParser<List<MatchResult>> getMatchResultListParser() {
    return (jsonWrapper -> {
      List<MatchResult> matchResultList = new LinkedList<>();
      JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
      dataArray.forEach((item) -> {
        MatchResult matchResult = new MatchResult();
        matchResult.setId(item.getLong("id"));
        matchResult.setCreatedTimestamp(
            TimeService.convertCSTInMillisecondToUTC(item.getLong("created-at")));
        matchResult.setFilledAmount(item.getBigDecimal("filled-amount"));
        matchResult.setFilledFees(item.getBigDecimal("filled-fees"));
        matchResult.setMatchId(item.getLong("match-id"));
        matchResult.setOrderId(item.getLong("order-id"));
        matchResult.setPrice(item.getBigDecimal("price"));
        matchResult.setSource(OrderSource.lookup(item.getString("source")));
        matchResult.setSymbol(item.getString("symbol"));
        matchResult.setType(OrderType.lookup(item.getString("type")));
        matchResult.setFilledPoints(item.getBigDecimalOrDefault("filled-points", null));
        matchResult.setFeeDeductCurrency(item.getStringOrDefault("fee-deduct-currency", null));
        matchResult.setRole(DealRole.find(item.getStringOrDefault("role", null)));
        matchResultList.add(matchResult);
      });
      return matchResultList;
    });
  }


}
