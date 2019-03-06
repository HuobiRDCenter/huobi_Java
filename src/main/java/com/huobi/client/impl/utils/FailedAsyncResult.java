package com.huobi.client.impl.utils;

import com.huobi.client.AsyncResult;
import com.huobi.client.exception.HuobiApiException;
import com.sun.istack.internal.Nullable;

public class FailedAsyncResult<T> implements AsyncResult<T> {

  private final HuobiApiException exception;

  public FailedAsyncResult(HuobiApiException exception) {
    this.exception = exception;
  }

  @Override
  public @Nullable
  HuobiApiException getException() {
    return exception;
  }

  @Override
  public boolean succeeded() {
    return false;
  }

  @Override
  public T getData() {
    return null;
  }
}
