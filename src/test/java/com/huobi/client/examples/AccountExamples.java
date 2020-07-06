package com.huobi.client.examples;

import java.math.BigDecimal;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.model.AccountTransferResult;
import com.huobi.client.model.enums.TransferAccountTypeEnum;
import com.huobi.client.model.request.AccountTransferRequest;

public class AccountExamples {


  public static void main(String[] args) {

    SyncRequestClient syncRequestClient = SyncRequestClient.create(Constants.API_KEY, Constants.SECRET_KEY);

//    AccountTransferRequest transferRequest = new AccountTransferRequest();
//    transferRequest.setFromUser(123L);
//    transferRequest.setFromAccount(234L);
//    transferRequest.setFromAccountType(TransferAccountTypeEnum.SPOT.getAccountType());
//
//    transferRequest.setToUser(345L);
//    transferRequest.setToAccount(456L);
//    transferRequest.setToAccountType(TransferAccountTypeEnum.SPOT.getAccountType());
//
//    transferRequest.setCurrency("usdt");
//    transferRequest.setAmount(new BigDecimal("100"));
//
//    AccountTransferResult transferResult = syncRequestClient.accountTransfer(transferRequest);
//    if (transferRequest != null) {
//      System.out.println("transact-id:"+transferResult.getTransactId());
//      System.out.println("transact-time:"+transferResult.getTransactTime());
//    }





  }


}
