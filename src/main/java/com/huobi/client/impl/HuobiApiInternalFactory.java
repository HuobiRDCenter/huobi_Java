package com.huobi.client.impl;

import com.huobi.client.AsyncRequestClient;
import com.huobi.client.RequestOptions;
import com.huobi.client.SubscriptionClient;
import com.huobi.client.SubscriptionOptions;
import com.huobi.client.SyncRequestClient;
import java.net.URI;

public final class HuobiApiInternalFactory {

  private static final HuobiApiInternalFactory instance = new HuobiApiInternalFactory();

  public static HuobiApiInternalFactory getInstance() {
    return instance;
  }

  private HuobiApiInternalFactory() {
  }

  public SyncRequestClient createSyncRequestClient(
      String apiKey, String secretKey, RequestOptions options) {
    RequestOptions requestOptions = new RequestOptions(options);
    RestApiRequestImpl requestImpl = new RestApiRequestImpl(apiKey, secretKey, requestOptions);
    if (!"".equals(apiKey) && !"".equals(secretKey)) {
      AccountsInfoMap.updateUserInfo(apiKey, requestImpl);
    }
    return new SyncRequestImpl(requestImpl);
  }

  public AsyncRequestClient createAsyncRequestClient(
      String apiKey, String secretKey, RequestOptions options) {
    RequestOptions requestOptions = new RequestOptions(options);
    RestApiRequestImpl requestImpl = new RestApiRequestImpl(apiKey, secretKey, requestOptions);
    if (!"".equals(apiKey) && !"".equals(secretKey)) {
      AccountsInfoMap.updateUserInfo(apiKey, requestImpl);
    }
    return new AsyncRequestImpl(requestImpl);
  }

  public SubscriptionClient createSubscriptionClient(
      String apiKey, String secretKey, SubscriptionOptions options) {
    SubscriptionOptions subscriptionOptions = new SubscriptionOptions(options);
    RequestOptions requestOptions = new RequestOptions();
    try {
      String host = new URI(options.getUri()).getHost();
      requestOptions.setUrl("https://" + host);
    } catch (Exception e) {

    }
    RestApiRequestImpl requestImpl = new RestApiRequestImpl(apiKey, secretKey, requestOptions);
    SubscriptionClient webSocketStreamClient = new WebSocketStreamClientImpl(
        apiKey, secretKey, subscriptionOptions);
    if (!"".equals(apiKey) && !"".equals(secretKey)) {
      AccountsInfoMap.updateUserInfo(apiKey, requestImpl);
    }
    return webSocketStreamClient;
  }


}
