package com.huobi.client.impl;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okio.Timeout;

public class MockHttpCall implements Call {

  Response response;

  MockHttpCall(Response response) {
    this.response = response;
  }

  @Override
  public Request request() {
    return null;
  }

  @Override
  public Response execute() throws IOException {
    return response;
  }

  @Override
  public void enqueue(Callback responseCallback) {
    try {
      responseCallback.onResponse(this, response);
    } catch (Exception e) {

    }
  }

  @Override
  public void cancel() {

  }

  @Override
  public boolean isExecuted() {
    return false;
  }

  @Override
  public boolean isCanceled() {
    return false;
  }

  @Override
  public Timeout timeout() {
    return null;
  }

  @Override
  public Call clone() {
    return null;
  }
}
