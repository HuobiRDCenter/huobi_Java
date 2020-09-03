package com.huobi.service.huobi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.AccountClient;
import com.huobi.client.req.account.AccountAssetValuationRequest;
import com.huobi.client.req.account.AccountBalanceRequest;
import com.huobi.client.req.account.AccountFuturesTransferRequest;
import com.huobi.client.req.account.AccountHistoryRequest;
import com.huobi.client.req.account.AccountLedgerRequest;
import com.huobi.client.req.account.AccountTransferRequest;
import com.huobi.client.req.account.PointRequest;
import com.huobi.client.req.account.PointTransferRequest;
import com.huobi.client.req.account.SubAccountUpdateRequest;
import com.huobi.constant.Options;
import com.huobi.constant.WebSocketConstants;
import com.huobi.constant.enums.AccountTypeEnum;
import com.huobi.model.account.Account;
import com.huobi.model.account.AccountAssetValuationResult;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.AccountFuturesTransferResult;
import com.huobi.model.account.AccountHistory;
import com.huobi.model.account.AccountLedgerResult;
import com.huobi.model.account.AccountTransferResult;
import com.huobi.model.account.AccountUpdateEvent;
import com.huobi.model.account.Point;
import com.huobi.model.account.PointTransferResult;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.connection.HuobiWebSocketConnection;
import com.huobi.service.huobi.parser.account.AccountAssetValuationResultParser;
import com.huobi.service.huobi.parser.account.AccountBalanceParser;
import com.huobi.service.huobi.parser.account.AccountFuturesTransferResultParser;
import com.huobi.service.huobi.parser.account.AccountHistoryParser;
import com.huobi.service.huobi.parser.account.AccountLedgerParser;
import com.huobi.service.huobi.parser.account.AccountParser;
import com.huobi.service.huobi.parser.account.AccountTransferResultParser;
import com.huobi.service.huobi.parser.account.AccountUpdateEventParser;
import com.huobi.service.huobi.parser.account.PointParser;
import com.huobi.service.huobi.parser.account.PointTransferResultParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;
import com.huobi.utils.ResponseCallback;

public class HuobiAccountService implements AccountClient {

  public static final String GET_ACCOUNTS_PATH = "/v1/account/accounts";
  public static final String GET_ACCOUNT_BALANCE_PATH = "/v1/account/accounts/{account-id}/balance";
  public static final String GET_ACCOUNT_HISTORY_PATH = "/v1/account/history";
  public static final String GET_ACCOUNT_LEDGER_PATH = "/v2/account/ledger";
  public static final String ACCOUNT_TRANSFER_PATH = "/v1/account/transfer";
  public static final String ACCOUNT_FUTURES_TRANSFER_PATH = "/v1/futures/transfer";
  public static final String POINT_ACCOUNT_PATH = "/v2/point/account";
  public static final String POINT_TRANSFER_PATH = "/v2/point/transfer";
  public static final String ACCOUNT_ASSET_VALUATION_PATH = "/v2/account/asset-valuation";



  public static final String SUB_ACCOUNT_UPDATE_TOPIC = "accounts.update#${mode}";


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
        .putToUrl("size", request.getSize());

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
    return new PointParser().parse(jsonObject);
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
    return new PointTransferResultParser().parse(jsonObject);
  }

  @Override
  public AccountAssetValuationResult accountAssetValuation(AccountAssetValuationRequest request) {
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("accountType", request.getAccountType().getCode())
        .putToUrl("valuationCurrency", request.getValuationCurrency())
        .putToUrl("subUid", request.getSubUid())
      ;
    JSONObject jsonObject = restConnection.executeGetWithSignature(ACCOUNT_ASSET_VALUATION_PATH, builder);
    return new AccountAssetValuationResultParser().parse(jsonObject);
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

}
