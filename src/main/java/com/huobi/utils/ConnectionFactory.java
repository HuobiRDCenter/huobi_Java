package com.huobi.utils;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import com.huobi.exception.SDKException;

@Slf4j
public class ConnectionFactory {

  private static final OkHttpClient client = new OkHttpClient();


  public static String execute(Request request) {

    Response response = null;
    String str = null;
    try {
      response = client.newCall(request).execute();
      if (response.code() != 200) {
        throw new SDKException(SDKException.EXEC_ERROR, "[Execute] Response Status Error : "+response.code()+" message:"+response.message());
      }
      if (response != null && response.body() != null) {
        str = response.body().string();
        response.close();
      } else {
        throw new SDKException(SDKException.ENV_ERROR, "[Execute] Cannot get the response from server");
      }
      return str;
    } catch (IOException e) {
      e.printStackTrace();
      throw new SDKException(SDKException.RUNTIME_ERROR, "[Execute] Cannot get the response from server");
    }

  }

  public static WebSocket createWebSocket(Request request, WebSocketListener listener) {
    return client.newWebSocket(request,listener);
  }

}
