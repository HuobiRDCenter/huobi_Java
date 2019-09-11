package com.huobi.client.examples;

import java.math.BigDecimal;

import com.huobi.client.SyncRequestClient;
import com.huobi.client.examples.constants.Constants;
import com.huobi.client.model.enums.TransferFuturesDirection;
import com.huobi.client.model.request.TransferFuturesRequest;

public class TransferFutures {

  public static void main(String[] args) {

    String currency = "ltc";
    SyncRequestClient syncRequestClient = SyncRequestClient.create(Constants.API_KEY, Constants.SECRET_KEY);

    long transferToFuturesId = syncRequestClient.transferFutures(TransferFuturesRequest.builder()
        .currency(currency)
        .amount(new BigDecimal("0.1"))
        .direction(TransferFuturesDirection.PRO_TO_FUTURES)
        .build());

    System.out.println(" transfer to futures result:" + transferToFuturesId);

    long transferToProId = syncRequestClient.transferFutures(TransferFuturesRequest.builder()
        .currency(currency)
        .amount(new BigDecimal("0.1"))
        .direction(TransferFuturesDirection.FUTURES_TO_PRO)
        .build());

    System.out.println(" transfer to futures result:" + transferToProId);

  }

}
