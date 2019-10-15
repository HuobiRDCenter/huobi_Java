package com.huobi.client.impl;

import com.huobi.client.RequestOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.JsonWrapperArray;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.impl.utils.UrlParamsBuilder;
import com.huobi.client.model.Account;
import com.huobi.client.model.Balance;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.BestQuote;
import com.huobi.client.model.Candlestick;
import com.huobi.client.model.CompleteSubAccountInfo;
import com.huobi.client.model.Deposit;
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
import com.huobi.client.model.enums.AccountState;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceType;
import com.huobi.client.model.enums.CandlestickInterval;
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
import com.huobi.client.model.request.CancelOpenOrderRequest;
import com.huobi.client.model.enums.EtfSwapType;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
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
    request.jsonParser = Candlestick.getListParser();
    return request;
  }

  RestApiRequest<List<Account>> getAccounts() {
    RestApiRequest<List<Account>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    request.request = createRequestByGetWithSignature("/v1/account/accounts", builder);
    request.jsonParser = Account.getListParser();
    return request;
  }

  RestApiRequest<PriceDepth> getPriceDepth(String symbol, Integer size) {
    InputChecker.checker().checkSymbol(symbol)
        .checkRange(size, 1, 150, "size");
    RestApiRequest<PriceDepth> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol)
        .putToUrl("type", "step0");
    request.request = createRequestByGet("/market/depth", builder);
    request.jsonParser = PriceDepth.getParser(size);
    return request;
  }

  RestApiRequest<List<Trade>> getHistoricalTrade(String symbol, String fromId, Integer size) {
    InputChecker.checker().checkSymbol(symbol).checkRange(size, 1, 2000, "size");
    RestApiRequest<List<Trade>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol)
        .putToUrl("size", size);  //.put("fromid", fromId)
    request.request = createRequestByGet("/market/history/trade", builder);
    request.jsonParser = Trade.getDataListParser();
    return request;
  }

  RestApiRequest<List<Trade>> getTrade(String symbol) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<List<Trade>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol);
    request.request = createRequestByGet("/market/trade", builder);
    request.jsonParser = Trade.getTickListParser();
    return request;
  }

  RestApiRequest<TradeStatistics> get24HTradeStatistics(String symbol) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<TradeStatistics> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol);
    request.request = createRequestByGet("/market/detail", builder);
    request.jsonParser = TradeStatistics.getParser();
    return request;
  }

  RestApiRequest<List<Symbol>> getSymbols() {

    RestApiRequest<List<Symbol>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    request.request = createRequestByGet("/v1/common/symbols", builder);
    request.jsonParser = Symbol.getListParser();
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


  RestApiRequest<BestQuote> getBestQuote(String symbol) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<BestQuote> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol);
    request.request = createRequestByGet("/market/detail/merged", builder);
    request.jsonParser = BestQuote.getParser();
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
    request.jsonParser = Withdraw.getListParser();
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
    request.jsonParser = Deposit.getListParser();
    return request;
  }

  RestApiRequest<List<Balance>> getBalance(Account account) {
    RestApiRequest<List<Balance>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    String url = String.format("/v1/account/accounts/%d/balance", account.getId());
    request.request = createRequestByGetWithSignature(url, builder);
    request.jsonParser = Balance.getListParser();
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
      return jsonWrapper.getLong("data");
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
    request.jsonParser = Loan.getListParser(apiKey);
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
    Account account = AccountsInfoMap.getUser(apiKey).getAccount(newOrderRequest.getAccountType());
    if (account == null) {
      throw new HuobiApiException(HuobiApiException.INPUT_ERROR, "[Input] No such account");
    }
    RestApiRequest<Long> request = new RestApiRequest<>();
    String source = "api";
    if (newOrderRequest.getAccountType() == AccountType.MARGIN) {
      source = "margin-api";
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
    Account account = AccountsInfoMap.getUser(apiKey).getAccount(accountType);
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
    request.jsonParser = Order.getListParser(apiKey);
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
    request.jsonParser = (json -> {

      System.out.println(clientOrderId + "===>" + json.getJson().toJSONString());


      return null;
    });
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
        .getAccount(cancelOpenOrderRequest.getAccountType());
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
    request.jsonParser = Order.getParser(apiKey);
    return request;
  }

  RestApiRequest<Order> getOrderByClientOrderId(String symbol, String clientOrderId) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<Order> request = new RestApiRequest<>();
    String url = "/v1/order/orders/getClientOrder";
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    builder.putToUrl("clientOrderId", clientOrderId);
    request.request = createRequestByGetWithSignature(url, builder);
    request.jsonParser = Order.getParser(apiKey);
    return request;
  }

  RestApiRequest<List<MatchResult>> getMatchResults(String symbol, long orderId) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<List<MatchResult>> request = new RestApiRequest<>();
    String url = String.format("/v1/order/orders/%d/matchresults", orderId);
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    request.request = createRequestByGetWithSignature(url, builder);
    request.jsonParser = MatchResult.getMatchResultListParser();
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
    request.jsonParser = MatchResult.getMatchResultListParser();
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
    request.jsonParser = Order.getListParser(apiKey);
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
    request.jsonParser = Order.getListParser(apiKey);
    return request;
  }


  RestApiRequest<List<FeeRate>> getFeeRate(String symbol) {
    RestApiRequest<List<FeeRate>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbols", symbol);
    request.request = createRequestByGetWithSignature("/v1/fee/fee-rate/get", builder);
    request.jsonParser = FeeRate.getListParser();
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
    request.jsonParser = Balance.getAggregatedBalanceParser();
    return request;
  }

  RestApiRequest<List<CompleteSubAccountInfo>> getSpecifyAccountBalance(long subId) {
    RestApiRequest<List<CompleteSubAccountInfo>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    String url = String.format("/v1/account/accounts/%d", subId);
    request.request = createRequestByGetWithSignature(url, builder);
    request.jsonParser = CompleteSubAccountInfo.getListParser();
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
    request.jsonParser = Candlestick.getListParser();
    return request;
  }

  RestApiRequest<EtfSwapConfig> getEtfSwapConfig(String etfSymbol) {
    InputChecker.checker().checkSymbol(etfSymbol);
    RestApiRequest<EtfSwapConfig> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("etf_name", etfSymbol);
    request.request = createRequestByGet("/etf/swap/config", builder);
    request.jsonParser = EtfSwapConfig.getParser();
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
    request.jsonParser = EtfSwapHistory.getListParser();
    return request;
  }

  RestApiRequest<List<MarginBalanceDetail>> getMarginBalanceDetail(String symbol) {
    InputChecker.checker().checkSymbol(symbol);
    RestApiRequest<List<MarginBalanceDetail>> request = new RestApiRequest<>();
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol);
    request.request = createRequestByGetWithSignature("/v1/margin/accounts/balance", builder);
    request.jsonParser = MarginBalanceDetail.getListParser();
    return request;
  }

  RestApiRequest<Map<String, TradeStatistics>> getTickers() {
    RestApiRequest<Map<String, TradeStatistics>> request = new RestApiRequest<>();
    request.request = createRequestByGet("/market/tickers", UrlParamsBuilder.build());
    request.jsonParser = TradeStatistics.getMapParser();
    return request;
  }




}
