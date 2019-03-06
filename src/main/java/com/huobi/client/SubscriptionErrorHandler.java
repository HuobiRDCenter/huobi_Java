package com.huobi.client;

import com.huobi.client.exception.HuobiApiException;

/**
 * The error handler for the subscription.
 */
@FunctionalInterface
public interface SubscriptionErrorHandler {

  void onError(HuobiApiException exception);
}
