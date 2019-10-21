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
import com.huobi.constant.Constants;
import com.huobi.constant.HuobiOptions;
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
    initAccount(accountList);
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

    Account account = getAccount(AccountTypeEnum.SPOT);

    System.out.println("");
    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("account-id", account.getId());

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


  public Account getAccount(AccountTypeEnum accountType) {
    // 若accountMap为空，同步初始化该map
    if (accountMap.isEmpty()) {
      this.getAccounts();
    }

    return accountMap.get(accountType);
  }

  public  Account getMarginAccount(String symbol) {
    if (marginAccountMap.isEmpty()) {
      this.getAccounts();
    }
    return marginAccountMap.get(symbol);
  }

  public void initAccount(List<Account> accountList) {
    synchronized (accountMap) {
      if (accountList == null || accountList.size() <= 0) {
        return;
      }

      accountList.forEach(account -> {
        if (account.getType() == AccountTypeEnum.MARGIN) {
          marginAccountMap.put(account.getSubtype(), account);
        } else {
          accountMap.put(account.getType(), account);
        }
      });
    }
  }

  public static void main(String[] args) {

    HuobiAccountService huobiAccountService = new HuobiAccountService(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY)
        .build());

    huobiAccountService.getAccountHistory();

//    List<Account> accountList = accountService.getAccounts();
//    accountList.forEach(account -> {
//      System.out.println(account.toString());
//    });
//
//
//    AccountBalance accountBalance = accountService.getAccountBalance(AccountBalanceRequest.builder()
//        .accountId(290082L)
//        .build());
//
//    System.out.println(accountBalance.getId());
//    System.out.println(accountBalance.getUserId());
//    System.out.println(accountBalance.getType());
//    System.out.println(accountBalance.getState());
//    accountBalance.getList().forEach(balance -> {
//      System.out.println(balance.toString());
//    });

//    System.out.println("===========transfer to subuser ===============");
//    long outTransferId = accountService.transferSubuser(TransferSubuserRequest.builder()
//        .subUid(120491258L)
//        .currency("usdt")
//        .amount(new BigDecimal("10"))
//        .type(TransferMasterTypeEnum.MASTER_TRANSFER_OUT)
//        .build());
//    System.out.println("===========transfer to subuser  result:"+outTransferId+"===============");
//
//    List<AccountBalance> subAccountBalanceList = accountService.getSubuserAccountBalance(120491258L);
//    System.out.println(subAccountBalanceList);
//
//    List<SubuserAggregateBalance> aggBalanceList = accountService.getSubuserAggregateBalance();
//    System.out.println("agg balance list:"+aggBalanceList.toString());
//
//    System.out.println("===========transfer to master ===============");
//    long inTransferId = accountService.transferSubuser(TransferSubuserRequest.builder()
//        .subUid(120491258L)
//        .currency("usdt")
//        .amount(new BigDecimal("10"))
//        .type(TransferMasterTypeEnum.MASTER_TRANSFER_IN)
//        .build());
//    System.out.println("===========transfer to subuser  result:"+inTransferId+"===============");
//
//    List<AccountBalance> subAccountBalanceList1 = accountService.getSubuserAccountBalance(120491258L);
//    System.out.println(subAccountBalanceList1);
//
//    accountService.subAccounts(SubAccountChangeRequest.builder()
//        .balanceMode(BalanceModeEnum.AVAILABLE)
//        .build(),(accountChangeEvent)->{
//
//      System.out.println("event:"+accountChangeEvent.getEvent());
//      accountChangeEvent.getList().forEach(accountChange -> {
//        System.out.println(accountChange.toString());
//      });
//    });

//    accountService.reqAccounts((accountReq) -> {
//
//      System.out.println("topic:"+accountReq.getTopic());
//      System.out.println("cid:"+accountReq.getCid());
//      accountReq.getBalanceList().forEach(accountBalance -> {
//        System.out.println(accountBalance.toString());
//      });
//
//    });

  }
}
