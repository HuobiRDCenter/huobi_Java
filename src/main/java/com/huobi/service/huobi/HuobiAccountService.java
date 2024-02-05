package com.huobi.service.huobi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.AccountClient;
import com.huobi.client.req.account.*;
import com.huobi.client.req.trade.SubOrderUpdateV2Request;
import com.huobi.client.req.trade.SubTradeClearingRequest;
import com.huobi.constant.Options;
import com.huobi.constant.WebSocketConstants;
import com.huobi.constant.enums.AccountTypeEnum;
import com.huobi.exception.SDKException;
import com.huobi.model.account.*;
import com.huobi.model.trade.OrderUpdateV2Event;
import com.huobi.model.trade.TradeClearingEvent;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.connection.HuobiWebSocketConnection;
import com.huobi.service.huobi.parser.account.*;
import com.huobi.service.huobi.parser.trade.OrderUpdateEventV2Parser;
import com.huobi.service.huobi.parser.trade.TradeClearingEventParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;
import com.huobi.utils.ResponseCallback;
import com.huobi.utils.SymbolUtils;

public class HuobiAccountService implements AccountClient {

  public static final String GET_ACCOUNTS_PATH = "/v1/account/accounts";//账户信息
  public static final String GET_ACCOUNT_BALANCE_PATH = "/v1/account/accounts/{account-id}/balance";//账户余额
  public static final String GET_ACCOUNT_HISTORY_PATH = "/v1/account/history";//账户流水
  public static final String GET_ACCOUNT_LEDGER_PATH = "/v2/account/ledger";//财务流水
  public static final String ACCOUNT_TRANSFER_PATH = "/v1/account/transfer";//资产划转
  public static final String ACCOUNT_FUTURES_TRANSFER_PATH = "/v1/futures/transfer";//币币现货账户与合约账户划转
  public static final String POINT_ACCOUNT_PATH = "/v2/point/account";//点卡余额查询
  public static final String POINT_TRANSFER_PATH = "/v2/point/transfer";//点卡划转
  public static final String ACCOUNT_ASSET_VALUATION_PATH = "/v2/account/asset-valuation";//获取指定账户资产估值（现货、杠杆、OTC）
  public static final String ACCOUNT_VALUATION_PATH = "/v2/account/valuation";//获取平台资产总估值
  public static final String ACCOUNT_TRANSFER_PATH_V2 = "/v2/account/transfer";//【通用】现货-合约账户和OTC账户间进行资金的划转



  public static final String SUB_ACCOUNT_UPDATE_TOPIC = "accounts.update#${mode}";//订阅账户变更
  public static final String WEBSOCKET_ORDER_UPDATE_V2_TOPIC = "orders#${symbol}";//订阅订单更新
  public static final String WEBSOCKET_TRADE_CLEARING_TOPIC = "trade.clearing#${symbol}#${mode}";//订阅清算后成交及撤单更新


  private Map<AccountTypeEnum, Account> accountMap = new ConcurrentHashMap<>();
  private Map<String, Account> marginAccountMap = new ConcurrentHashMap<>();

  private Options options;

  private HuobiRestConnection restConnection;

  public HuobiAccountService(Options options) {
    this.options = options;
    this.restConnection = new HuobiRestConnection(options);
  }

  @Override
  public List<Account> getAccounts() {

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_ACCOUNTS_PATH, UrlParamsBuilder.build());
    JSONArray data = jsonObject.getJSONArray("data");
    List<Account> accountList = new AccountParser().parseArray(data);
    return accountList;
  }

  @Override
  public AccountBalance getAccountBalance(AccountBalanceRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getAccountId(), "account-id");

    String path = GET_ACCOUNT_BALANCE_PATH.replace("{account-id}", request.getAccountId() + "");
    JSONObject jsonObject = restConnection.executeGetWithSignature(path, UrlParamsBuilder.build());
    JSONObject data = jsonObject.getJSONObject("data");
    return new AccountBalanceParser().parse(data);
  }

  public List<AccountHistory> getAccountHistory(AccountHistoryRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getAccountId(), "account-id");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("account-id", request.getAccountId())
        .putToUrl("currency", request.getCurrency())
        .putToUrl("transact-types", request.getTypesString())
        .putToUrl("start-time", request.getStartTime())
        .putToUrl("end-time", request.getEndTime())
        .putToUrl("sort", request.getSort() == null ? null : request.getSort().getSort())
        .putToUrl("size", request.getSize())
        .putToUrl("from-id", request.getFromId());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_ACCOUNT_HISTORY_PATH, builder);
    JSONArray jsonArray = jsonObject.getJSONArray("data");
    AccountHistoryParser parser = new AccountHistoryParser();
    List<AccountHistory> list = new ArrayList<>(jsonArray.size());
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject jsonItem = jsonArray.getJSONObject(i);
      list.add(parser.parse(jsonItem));
    }
    list.get(list.size()-1).setNextId(jsonObject.getLong("next-id"));
    return list;
  }

  public AccountLedgerResult getAccountLedger(AccountLedgerRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getAccountId(), "accountId");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("accountId", request.getAccountId())
        .putToUrl("currency", request.getCurrency())
        .putToUrl("transactTypes", request.getTypesString())
        .putToUrl("startTime", request.getStartTime())
        .putToUrl("endTime", request.getEndTime())
        .putToUrl("sort", request.getSort() == null ? null : request.getSort().getSort())
        .putToUrl("limit", request.getLimit())
        .putToUrl("fromId", request.getFromId());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_ACCOUNT_LEDGER_PATH, builder);
    Long nextId = jsonObject.getLong("nextId");
    JSONArray jsonArray = jsonObject.getJSONArray("data");
    return AccountLedgerResult.builder()
        .nextId(nextId)
        .ledgerList(new AccountLedgerParser().parseArray(jsonArray))
        .build();
  }

  public AccountTransferResult accountTransfer(AccountTransferRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getFromUser(), "from-user")
        .shouldNotNull(request.getFromAccount(), "from-account")
        .shouldNotNull(request.getFromAccountType(), "from-account-type")
        .shouldNotNull(request.getToUser(), "to-user")
        .shouldNotNull(request.getToAccount(), "to-account")
        .shouldNotNull(request.getToAccountType(), "to-account-type")
        .shouldNotNull(request.getCurrency(), "currency")
        .shouldNotNull(request.getAmount(), "amount");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("from-user", request.getFromUser())
        .putToPost("from-account", request.getFromAccount())
        .putToPost("from-account-type", request.getFromAccountType().getAccountType())
        .putToPost("to-user", request.getToUser())
        .putToPost("to-account", request.getToAccount())
        .putToPost("to-account-type", request.getToAccountType().getAccountType())
        .putToPost("currency", request.getCurrency())
        .putToPost("amount", request.getAmount());

    JSONObject jsonObject = restConnection.executePostWithSignature(ACCOUNT_TRANSFER_PATH, builder);
    return new AccountTransferResultParser().parse(jsonObject.getJSONObject("data"));
  }

  public AccountFuturesTransferResult accountFuturesTransfer(AccountFuturesTransferRequest request) {
    InputChecker.checker()
        .shouldNotNull(request.getCurrency(),"currency")
        .shouldNotNull(request.getAmount(),"amount")
        .shouldNotNull(request.getType(),"type");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("currency", request.getCurrency())
        .putToPost("amount", request.getAmount())
        .putToPost("type", request.getType().getType());

    JSONObject jsonObject = restConnection.executePostWithSignature(ACCOUNT_FUTURES_TRANSFER_PATH, builder);

    return new AccountFuturesTransferResultParser().parse(jsonObject);
  }

  @Override
  public Point getPoint(PointRequest request) {

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("subUid", request.getSubUid());

    JSONObject jsonObject = restConnection.executeGetWithSignature(POINT_ACCOUNT_PATH, builder);
    JSONObject data = jsonObject.getJSONObject("data");
    return new PointParser().parse(data);
  }

  @Override
  public PointTransferResult pointTransfer(PointTransferRequest request) {
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("fromUid", request.getFromUid())
        .putToPost("toUid", request.getToUid())
        .putToPost("groupId", request.getGroupId())
        .putToPost("amount", request.getAmount())
      ;
    JSONObject jsonObject = restConnection.executePostWithSignature(POINT_TRANSFER_PATH, builder);
    JSONObject data = jsonObject.getJSONObject("data");
    return new PointTransferResultParser().parse(data);
  }

  @Override
  public AccountAssetValuationResult accountAssetValuation(AccountAssetValuationRequest request) {
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("accountType", request.getAccountType().getCode())
        .putToUrl("valuationCurrency", request.getValuationCurrency())
        .putToUrl("subUid", request.getSubUid())
      ;
    JSONObject jsonObject = restConnection.executeGetWithSignature(ACCOUNT_ASSET_VALUATION_PATH, builder);
    JSONObject data = jsonObject.getJSONObject("data");
    return new AccountAssetValuationResultParser().parse(data);
  }

  @Override
  public AccountValuationResult accountValuation(AccountValuationRequest request) {
    UrlParamsBuilder builder = UrlParamsBuilder.build()
            .putToUrl("accountType", request.getAccountType().getCode())
            .putToUrl("valuationCurrency", request.getValuationCurrency());
    JSONObject jsonObject = restConnection.executeGetWithSignature(ACCOUNT_VALUATION_PATH, builder);
    JSONObject data = jsonObject.getJSONObject("data");
    return new AccountValuationResultParser().parse(data);
  }

  @Override
  public AccountTransferV2Result accountTransferV2(AccountTransferV2Request request) {
    InputChecker.checker()
            .shouldNotNull(request.getFrom(), "from")
            .shouldNotNull(request.getTo(), "to")
            .shouldNotNull(request.getCurrency(), "currency")
            .shouldNotNull(request.getAmount(), "amount")
            .shouldNotNull(request.getMarginAccount(), "margin-account");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
            .putToPost("from", request.getFrom().getType())
            .putToPost("to", request.getTo().getType())
            .putToPost("currency", request.getCurrency())
            .putToPost("amount", request.getAmount())
            .putToPost("margin-account", request.getMarginAccount());

    JSONObject jsonObject = restConnection.executePostWithSignature(ACCOUNT_TRANSFER_PATH_V2, builder);
    return new AccountTransferV2ResultParser().parse(jsonObject);
  }

  public void subAccountsUpdate(SubAccountUpdateRequest request, ResponseCallback<AccountUpdateEvent> callback) {
    InputChecker.checker()
        .shouldNotNull(request.getAccountUpdateMode(), "account update model");

    JSONObject command = new JSONObject();
    command.put("action", WebSocketConstants.ACTION_SUB);
    command.put("cid", System.currentTimeMillis() + "");
    command.put("ch", SUB_ACCOUNT_UPDATE_TOPIC.replace("${mode}", request.getAccountUpdateMode().getCode()));
    command.put("model", request.getAccountUpdateMode().getCode());

    List<String> commandList = new ArrayList<>();
    commandList.add(command.toJSONString());

    HuobiWebSocketConnection.createAssetV2Connection(options, commandList, new AccountUpdateEventParser(), callback, false);
  }

  @Override
  public void subOrderUpdateV2(SubOrderUpdateV2Request request, ResponseCallback<OrderUpdateV2Event> callback) {
    // 检查参数
    InputChecker.checker()
            .shouldNotNull(request.getSymbols(), "symbols");

    // 格式化symbol为数组
    List<String> symbolList = SymbolUtils.parseSymbols(request.getSymbols());

    // 检查数组
    InputChecker.checker().checkSymbolList(symbolList);

    List<String> commandList = new ArrayList<>(symbolList.size());
    symbolList.forEach(symbol -> {

      String topic = WEBSOCKET_ORDER_UPDATE_V2_TOPIC
              .replace("${symbol}", symbol);

      JSONObject command = new JSONObject();
      command.put("action", WebSocketConstants.ACTION_SUB);
      command.put("ch", topic);
      command.put("id", System.nanoTime());
      commandList.add(command.toJSONString());
    });

    HuobiWebSocketConnection.createAssetV2Connection(options, commandList, new OrderUpdateEventV2Parser(), callback, false);
  }

  @Override
  public void subTradeClearing(SubTradeClearingRequest request, ResponseCallback<TradeClearingEvent> callback) {
    // 检查参数
    InputChecker.checker()
            .shouldNotNull(request.getSymbols(), "symbols");

    // 格式化symbol为数组
    List<String> symbolList = SymbolUtils.parseSymbols(request.getSymbols());
    int[] modeArray = request.getModes();
    // 检查数组
    InputChecker.checker().checkSymbolList(symbolList);
    if (symbolList.size() != modeArray.length) {
      throw new SDKException(SDKException.INPUT_ERROR, "[Input] The number of symbol and mode must be equal");
    }
    List<String> commandList = new ArrayList<>(symbolList.size());
    for (int i = 0; i < symbolList.size(); i++) {
      String topic = WEBSOCKET_TRADE_CLEARING_TOPIC
              .replace("${symbol}", symbolList.get(i))
              .replace("${mode}", String.valueOf(modeArray[i]));
      JSONObject command = new JSONObject();
      command.put("action", WebSocketConstants.ACTION_SUB);
      command.put("ch", topic);
      command.put("id", System.nanoTime());
      commandList.add(command.toJSONString());
    }
    HuobiWebSocketConnection.createAssetV2Connection(options, commandList, new TradeClearingEventParser(), callback, false);

  }

}
