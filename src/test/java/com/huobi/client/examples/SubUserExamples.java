package com.huobi.client.examples;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.impl.utils.GoogleAuthHelper;
import com.huobi.client.model.ApiKeyInfo;
import com.huobi.client.model.GetApiKeyListResult;
import com.huobi.client.model.GetSubUserAccountListResult;
import com.huobi.client.model.GetSubUserListRequest;
import com.huobi.client.model.GetSubUserListResult;
import com.huobi.client.model.SubUserApiKeyGenerationResult;
import com.huobi.client.model.SubUserApiKeyModificationResult;
import com.huobi.client.model.SubUserCreationResult;
import com.huobi.client.model.SubUserState;
import com.huobi.client.model.SubUserTradableMarketResult;
import com.huobi.client.model.SubUserTradableMarketState;
import com.huobi.client.model.SubUserTransferabilityResult;
import com.huobi.client.model.SubUserTransferabilityState;
import com.huobi.client.model.SubuserManagementResult;
import com.huobi.client.model.enums.SubUserApiKeyPermissionEnums;
import com.huobi.client.model.enums.TradableMarketAccountTypeEnum;
import com.huobi.client.model.enums.TradableMarketActivationEnums;
import com.huobi.client.model.enums.TransferabilityAccountTypeEnum;
import com.huobi.client.model.request.GetApiKeyListRequest;
import com.huobi.client.model.request.GetSubUserAccountListRequest;
import com.huobi.client.model.request.SubUserApiKeyDeletionRequest;
import com.huobi.client.model.request.SubUserApiKeyGenerationRequest;
import com.huobi.client.model.request.SubUserApiKeyModificationRequest;
import com.huobi.client.model.request.SubUserCreationParam;
import com.huobi.client.model.request.SubUserCreationRequest;
import com.huobi.client.model.request.SubUserTradableMarketRequest;
import com.huobi.client.model.request.SubUserTransferabilityRequest;
import com.huobi.client.model.request.SubuserManagementRequest;

public class SubUserExamples {

  public static void main(String[] args) {

    Long subUid = 1234L;
    Long parentUid = 5678L;
    String googleAuthKey = "GOOGLEAUTHKEY";

    SyncRequestClient syncRequestClient = SyncRequestClient.create(Constants.API_KEY, Constants.SECRET_KEY);

    // Lock sub user
    SubuserManagementResult lockResult = syncRequestClient.subuserManagement(SubuserManagementRequest.lock(subUid));
    System.out.println(" subuser lock result: uid:"+lockResult.getSubUid()+"   state:"+lockResult.getUserState());

    // Unlock sub user
    SubuserManagementResult unlockResult = syncRequestClient.subuserManagement(SubuserManagementRequest.unlock(subUid));
    System.out.println(" subuser unlock result: uid:"+unlockResult.getSubUid()+"   state:"+unlockResult.getUserState());

    // Create sub user
    List<SubUserCreationParam> creationSubUserList = new ArrayList<>();
    creationSubUserList.add(new SubUserCreationParam("test1000001", "huobiSdk"));
    SubUserCreationRequest creationRequest = new SubUserCreationRequest(creationSubUserList);

    SubUserCreationResult result = syncRequestClient.subuserCreation(creationRequest);
    if (result != null) {
      result.getResults().forEach(info -> {
        System.out.println("uid:" + info.getUid());
        System.out.println("userName:" + info.getUserName());
        System.out.println("note:" + info.getNode());
        System.out.println("errCode:" + info.getErrCode());
        System.out.println("errMessage:" + info.getErrMessage());
        System.out.println("----------------");
      });
    }

    // Get sub user state list
    GetSubUserListResult subUserListResult = syncRequestClient.getSubuserList(new GetSubUserListRequest());
    if (subUserListResult != null) {
      for (SubUserState subUserState : subUserListResult.getUserList()) {
        System.out.println("list::  uid:" + subUserState.getUid() + "    userState:" + subUserState.getUserState());
        System.out.println("----------------");
      }
    }

    // Get single sub user state
    SubUserState state = syncRequestClient.getSubuserState(subUid);
    if (state != null) {
      System.out.println("single:: uid:" + state.getUid() + "    userState:" + state.getUserState());
    }
    System.out.println("----------------");

    // Get sub user account list
    GetSubUserAccountListResult getSubUserAccountListResult = syncRequestClient
        .getSubuserAccountList(new GetSubUserAccountListRequest(subUid));
    if (getSubUserAccountListResult != null) {
      System.out.println("sub user account List : " + JSON.toJSONString(getSubUserAccountListResult));
    }
    System.out.println("----------------");

    // set subuser transferrable state
    SubUserTransferabilityRequest transferabilityRequest = new SubUserTransferabilityRequest();
    transferabilityRequest.setSubUids(subUid + "");
    transferabilityRequest.setTransferrable(Boolean.TRUE.toString());
    transferabilityRequest.setAccountType(TransferabilityAccountTypeEnum.SPOT.getAccountType());
    SubUserTransferabilityResult transferabilityResult = syncRequestClient.subuserTransferability(transferabilityRequest);
    if (transferabilityResult != null) {
      for (SubUserTransferabilityState transferabilityState : transferabilityResult.getList()) {
        System.out.println("transferabilityState:  "
            + "subUid:" + transferabilityState.getSubUid()
            + "  accountType:" + transferabilityState.getAccountType()
            + "   transferrable: " + transferabilityState.getTransferrable());
      }
    }

//     set sub user tradable market
    SubUserTradableMarketRequest tradableMarketRequest = new SubUserTradableMarketRequest();
    tradableMarketRequest.setSubUids(subUid+"");
    tradableMarketRequest.setAccountType(TradableMarketAccountTypeEnum.ISOLATED_MARGIN.getAccountType());
    tradableMarketRequest.setActivation(TradableMarketActivationEnums.ACTIVATED.getActivation());
    SubUserTradableMarketResult tradableMarketResult = syncRequestClient.subuserTradableMarket(tradableMarketRequest);
    if (tradableMarketResult != null) {
      for (SubUserTradableMarketState marketState : tradableMarketResult.getList()) {
        System.out.println("tradable market State:  "
            + "subUid:" + marketState.getSubUid()
            + "  accountType:" + marketState.getAccountType()
            + "   activation: " + marketState.getActivation());
      }
    }


    // generate sub user api key
    String optToken = GoogleAuthHelper.getVercodeTime(googleAuthKey) + "";
    String permission = SubUserApiKeyPermissionEnums.READ_ONLY.getPermission() + "," + SubUserApiKeyPermissionEnums.TRADE.getPermission();
    SubUserApiKeyGenerationRequest apiKeyGenerationRequest = new SubUserApiKeyGenerationRequest();
    apiKeyGenerationRequest.setOtpToken(optToken);
    apiKeyGenerationRequest.setSubUid(subUid);
    apiKeyGenerationRequest.setNote("test_2");
    apiKeyGenerationRequest.setPermission(permission);
    apiKeyGenerationRequest.setIpAddresses("");

    SubUserApiKeyGenerationResult apiKeyGenerationResult = syncRequestClient.subuserApiKeyGeneration(apiKeyGenerationRequest);
    if (apiKeyGenerationResult != null) {
      System.out.println("api key generation ::" + JSON.toJSONString(apiKeyGenerationResult));
    }

    // modify sub user api key
    SubUserApiKeyModificationRequest apiKeyModificationRequest = new SubUserApiKeyModificationRequest();
    apiKeyModificationRequest.setSubUid(subUid);
    apiKeyModificationRequest.setAccessKey(apiKeyGenerationResult.getAccessKey());
    apiKeyModificationRequest.setNote("test_m_2");
    apiKeyModificationRequest.setPermission(SubUserApiKeyPermissionEnums.READ_ONLY.getPermission());
    apiKeyModificationRequest.setIpAddresses("127.0.0.2");

    SubUserApiKeyModificationResult apiKeyModificationResult = syncRequestClient.subuserApiKeyModification(apiKeyModificationRequest);
    if (apiKeyModificationResult != null) {
      System.out.println("api key modification ::" + JSON.toJSONString(apiKeyModificationResult));
    }

    // delete sub user api key
    syncRequestClient.subuserApiKeyDeletion(new SubUserApiKeyDeletionRequest(subUid,apiKeyGenerationResult.getAccessKey()));



    // Get api key info list
    GetApiKeyListResult getApiKeyListResult = syncRequestClient.getApiKeyList(new GetApiKeyListRequest(subUid));
    if (getApiKeyListResult != null) {
      for (ApiKeyInfo apiKeyInfo : getApiKeyListResult.getList()) {
        System.out.println("key :" + JSON.toJSONString(apiKeyInfo));
      }
    }



  }

}
