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

    /**
     * transfer from pro to futures
     */
    TransferFuturesRequest toFutureRequest = new TransferFuturesRequest();
    toFutureRequest.setCurrency(currency);
    toFutureRequest.setAmount(new BigDecimal("0.1"));
    toFutureRequest.setDirection(TransferFuturesDirection.PRO_TO_FUTURES);

    long transferToFuturesId = syncRequestClient.transferFutures(toFutureRequest);

    System.out.println(" transfer to futures result:" + transferToFuturesId);

    /**
     * transfer from futures to pro
     */
    TransferFuturesRequest toSpotRequest = new TransferFuturesRequest();
    toSpotRequest.setCurrency(currency);
    toSpotRequest.setAmount(new BigDecimal("0.1"));
    toSpotRequest.setDirection(TransferFuturesDirection.FUTURES_TO_PRO);
    long transferToProId = syncRequestClient.transferFutures(toSpotRequest);

    System.out.println(" transfer to spot result:" + transferToProId);

  }

}
