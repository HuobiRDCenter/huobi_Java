package com.huobi.service.huobi;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.AccountClient;
import com.huobi.client.req.AccountBalanceRequest;
import com.huobi.client.req.SubAccountChangeRequest;
import com.huobi.constant.Constants;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.enums.BalanceModeEnum;
import com.huobi.model.account.Account;
import com.huobi.constant.Options;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.AccountChangeEvent;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.connection.HuobiWebSocketConnection;
import com.huobi.service.huobi.parser.AccountBalanceParser;
import com.huobi.service.huobi.parser.AccountChangeEventParser;
import com.huobi.service.huobi.parser.AccountParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;
import com.huobi.utils.ResponseCallback;

public class AccountService implements AccountClient {

  public static final String GET_ACCOUNTS_PATH = "/v1/account/accounts";
  public static final String GET_ACCOUNT_BALANCE_PATH = "/v1/account/accounts/{account-id}/balance";

  private Options options;

  private HuobiRestConnection restConnection;

  public AccountService(Options options) {
    this.options = options;
    this.restConnection = new HuobiRestConnection(options);
  }

  @Override
  public List<Account> getAccounts() {

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_ACCOUNTS_PATH, UrlParamsBuilder.build());
    JSONArray data = jsonObject.getJSONArray("data");
    return new AccountParser().parseArray(data);
  }

  @Override
  public AccountBalance getAccountBalance(AccountBalanceRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getAccountId(),"account-id");

    String path = GET_ACCOUNT_BALANCE_PATH.replace("{account-id}",request.getAccountId()+"");
    JSONObject jsonObject = restConnection.executeGetWithSignature(path, UrlParamsBuilder.build());
    JSONObject data = jsonObject.getJSONObject("data");
    return new AccountBalanceParser().parse(data);
  }

  @Override
  public void transferSubuser() {

  }

  @Override
  public void getSubuserAccountBalance() {

  }

  @Override
  public void getSubuserAggregateBalance() {

  }

  @Override
  public void subAccounts(SubAccountChangeRequest request, ResponseCallback<AccountChangeEvent> callback) {

    InputChecker.checker()
        .shouldNotNull(request.getBalanceMode(),"balance model");


    JSONObject command = new JSONObject();
    command.put("op","sub");
    command.put("cid",System.currentTimeMillis()+"");
    command.put("topic","accounts");
    command.put("model",request.getBalanceMode().getCode());

    List<String> commandList = new ArrayList<>();
    commandList.add(command.toJSONString());

    HuobiWebSocketConnection.createAssetConnection(options,commandList,new AccountChangeEventParser(),callback,false);

  }

  public static void main(String[] args) {

    AccountService accountService = new AccountService(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY)
        .build());

    List<Account> accountList = accountService.getAccounts();
    accountList.forEach(account -> {
      System.out.println(account.toString());
    });


    AccountBalance accountBalance = accountService.getAccountBalance(AccountBalanceRequest.builder()
        .accountId(290082L)
        .build());

    System.out.println(accountBalance.getId());
    System.out.println(accountBalance.getUserId());
    System.out.println(accountBalance.getType());
    System.out.println(accountBalance.getState());
    accountBalance.getList().forEach(balance -> {
      System.out.println(balance.toString());
    });

    accountService.subAccounts(SubAccountChangeRequest.builder()
        .balanceMode(BalanceModeEnum.AVAILABLE)
        .build(),(accountChangeEvent)->{

      System.out.println("event:"+accountChangeEvent.getEvent());
      accountChangeEvent.getList().forEach(accountChange -> {
        System.out.println(accountChange.toString());
      });


    });

  }
}
