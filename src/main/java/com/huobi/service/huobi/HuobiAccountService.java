package com.huobi.service.huobi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.AccountClient;
import com.huobi.client.req.account.AccountBalanceRequest;
import com.huobi.client.req.account.SubAccountChangeRequest;
import com.huobi.client.req.account.TransferSubuserRequest;
import com.huobi.constant.Options;
import com.huobi.constant.WebSocketConstants;
import com.huobi.constant.enums.AccountTypeEnum;
import com.huobi.model.account.Account;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.AccountChangeEvent;
import com.huobi.model.account.AccountReq;
import com.huobi.model.account.SubuserAggregateBalance;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.connection.HuobiWebSocketConnection;
import com.huobi.service.huobi.parser.account.AccountBalanceParser;
import com.huobi.service.huobi.parser.account.AccountChangeEventParser;
import com.huobi.service.huobi.parser.account.AccountParser;
import com.huobi.service.huobi.parser.account.AccountReqParser;
import com.huobi.service.huobi.parser.account.SubuserAggregateBalanceParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;
import com.huobi.utils.ResponseCallback;

public class HuobiAccountService implements AccountClient {

  public static final String GET_ACCOUNTS_PATH = "/v1/account/accounts";
  public static final String GET_ACCOUNT_BALANCE_PATH = "/v1/account/accounts/{account-id}/balance";
  public static final String GET_SUBUSER_ACCOUNT_BALANCE_PATH = "/v1/account/accounts/{sub-uid}";
  public static final String TRANSFER_SUBUSER_PATH = "/v1/subuser/transfer";
  public static final String GET_SUBUSER_AGGREGATE_BALANCE_PATH = "/v1/subuser/aggregate-balance";


  public static final String SUB_ACCOUNT_TOPIC = "accounts";
  public static final String REQ_ACCOUNT_TOPIC = "accounts.list";


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

  @Override
  public long transferSubuser(TransferSubuserRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getSubUid(), "sub-uid")
        .shouldNotNull(request.getCurrency(), "currency")
        .shouldNotNull(request.getAmount(), "amount")
        .shouldNotNull(request.getType(), "type");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("sub-uid", request.getSubUid())
        .putToPost("currency", request.getCurrency())
        .putToPost("amount", request.getAmount().toPlainString())
        .putToPost("type", request.getType().getCode());

    JSONObject jsonObject = restConnection.executePostWithSignature(TRANSFER_SUBUSER_PATH, builder);
    return jsonObject.getLong("data");
  }

  @Override
  public List<AccountBalance> getSubuserAccountBalance(Long subuserId) {

    InputChecker.checker()
        .shouldNotNull(subuserId, "sub-uid");

    String path = GET_SUBUSER_ACCOUNT_BALANCE_PATH.replace("{sub-uid}", subuserId + "");
    JSONObject jsonObject = restConnection.executeGetWithSignature(path, UrlParamsBuilder.build());
    JSONArray data = jsonObject.getJSONArray("data");
    return new AccountBalanceParser().parseArray(data);
  }

  @Override
  public List<SubuserAggregateBalance> getSubuserAggregateBalance() {

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_SUBUSER_AGGREGATE_BALANCE_PATH, UrlParamsBuilder.build());
    JSONArray data = jsonObject.getJSONArray("data");
    return new SubuserAggregateBalanceParser().parseArray(data);

  }

  public void getAccountHistory() {


    System.out.println("");
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("account-id", 123);

    JSONObject jsonObject = restConnection.executeGetWithSignature("/v1/account/history", builder);
    System.out.println(jsonObject.toJSONString());
  }

  @Override
  public void subAccounts(SubAccountChangeRequest request, ResponseCallback<AccountChangeEvent> callback) {

    InputChecker.checker()
        .shouldNotNull(request.getBalanceMode(), "balance model");

    JSONObject command = new JSONObject();
    command.put("op", WebSocketConstants.OP_SUB);
    command.put("cid", System.currentTimeMillis() + "");
    command.put("topic", SUB_ACCOUNT_TOPIC);
    command.put("model", request.getBalanceMode().getCode());

    List<String> commandList = new ArrayList<>();
    commandList.add(command.toJSONString());

    HuobiWebSocketConnection.createAssetConnection(options, commandList, new AccountChangeEventParser(), callback, false);

  }

  @Override
  public void reqAccounts(ResponseCallback<AccountReq> callback) {

    JSONObject command = new JSONObject();
    command.put("op", WebSocketConstants.OP_REQ);
    command.put("cid", System.currentTimeMillis() + "");
    command.put("topic", REQ_ACCOUNT_TOPIC);

    List<String> commandList = new ArrayList<>();
    commandList.add(command.toJSONString());
    HuobiWebSocketConnection.createAssetConnection(options, commandList, new AccountReqParser(), callback, true);
  }


}
