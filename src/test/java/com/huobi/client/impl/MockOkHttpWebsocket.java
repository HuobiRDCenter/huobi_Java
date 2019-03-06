package com.huobi.client.impl;

import java.util.LinkedList;
import java.util.List;
import okhttp3.Request;
import okhttp3.WebSocket;
import okio.ByteString;

public class MockOkHttpWebsocket implements WebSocket {

  List<String> outputList = new LinkedList<>();

  String popOutputMessage() {
    if (!outputList.isEmpty()) {
      return outputList.remove(0);
    }
    return "";
  }

  @Override
  public Request request() {
    return null;
  }

  @Override
  public long queueSize() {
    return 0;
  }

  @Override
  public boolean send(String text) {
    outputList.add(text);
    return true;
  }

  @Override
  public boolean send(ByteString bytes) {
    return false;
  }

  @Override
  public boolean close(int code, String reason) {
    return false;
  }

  @Override
  public void cancel() {

  }
}
