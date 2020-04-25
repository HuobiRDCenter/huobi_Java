package com.huobi.client.impl;

import com.huobi.client.AsyncResult;
import com.huobi.client.ResponseCallback;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.EtfResult;
import com.huobi.client.impl.utils.FailedAsyncResult;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.SucceededAsyncResult;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//import com.sun.istack.internal.NotNull;

//import com.sun.istack.internal.NotNull;

public abstract class RestApiInvoker {

  private static final Logger log = LoggerFactory.getLogger(RestApiInvoker.class);

  public static Map<String,Long> EXECUTE_COST_MAP = new HashMap<>();
  public static final String START_TIME_KEY = "startTime";
  public static final String END_TIME_KEY = "endTime";

  private static boolean PERFORMANCE_SWITCH = false;
  private static Proxy proxy;



  private static OkHttpClient client = getBuilder(proxy).build();

  @NotNull
  private static OkHttpClient.Builder getBuilder(Proxy proxy) {
    if (proxy!=null) {

      return new OkHttpClient.Builder()
              .pingInterval(20, TimeUnit.SECONDS)
              .readTimeout(10, TimeUnit.SECONDS)
              .proxy(proxy)
              .addInterceptor(new Interceptor() {
                @NotNull
                @Override
                public Response intercept(@NotNull Chain chain) throws IOException {
                  Request request = chain.request();

                  Long startTime = System.currentTimeMillis();
                  Response response = chain.proceed(request);
                  Long endTime = System.currentTimeMillis();

                  if (PERFORMANCE_SWITCH) {
                    EXECUTE_COST_MAP.clear();
                    EXECUTE_COST_MAP.put(START_TIME_KEY,startTime);
                    EXECUTE_COST_MAP.put(END_TIME_KEY,endTime);
                  }

                  return response;
                }
              });
    }else
    {
      return new OkHttpClient.Builder()
              .pingInterval(20, TimeUnit.SECONDS)
              .readTimeout(10, TimeUnit.SECONDS)
              .addInterceptor(new Interceptor() {
                @NotNull
                @Override
                public Response intercept(@NotNull Chain chain) throws IOException {
                  Request request = chain.request();

                  Long startTime = System.currentTimeMillis();
                  Response response = chain.proceed(request);
                  Long endTime = System.currentTimeMillis();

                  if (PERFORMANCE_SWITCH) {
                    EXECUTE_COST_MAP.clear();
                    EXECUTE_COST_MAP.put(START_TIME_KEY,startTime);
                    EXECUTE_COST_MAP.put(END_TIME_KEY,endTime);
                  }

                  return response;
                }
              });
    }

  }

  static void checkResponse(JsonWrapper json) {
    try {
      if (json.containKey("status")) {
        String status = json.getString("status");
        if ("error".equals(status)) {
          String err_code = json.getString("err-code");
          String err_msg = json.getString("err-msg");
          throw new HuobiApiException(HuobiApiException.EXEC_ERROR,
                                      "[Executing] " + err_code + ": " + err_msg);
        } else if (!"ok".equals(status)) {
          throw new HuobiApiException(
                  HuobiApiException.RUNTIME_ERROR, "[Invoking] Response is not expected: " + status);
        }
      } else if (json.containKey("success")) {
        boolean success = json.getBoolean("success");
        if (!success) {
          String err_code = EtfResult.checkResult(json.getInteger("code"));
          String err_msg = json.getString("message");
          if ("".equals(err_code)) {
            throw new HuobiApiException(HuobiApiException.EXEC_ERROR, "[Executing] " + err_msg);
          } else {
            throw new HuobiApiException(HuobiApiException.EXEC_ERROR,
                                        "[Executing] " + err_code + ": " + err_msg);
          }
        }
      } else if (json.containKey("code")) {

        int code = json.getInteger("code");
        if (code != 200) {
          String message = json.getString("message");
          throw new HuobiApiException(HuobiApiException.EXEC_ERROR, "[Executing] " + code + ": " + message);
        }
      } else {
        throw new HuobiApiException(
                HuobiApiException.RUNTIME_ERROR, "[Invoking] Status cannot be found in response.");
      }
    } catch (HuobiApiException e) {
      throw e;
    } catch (Exception e) {
      throw new HuobiApiException(
              HuobiApiException.RUNTIME_ERROR, "[Invoking] Unexpected error: " + e.getMessage());
    }
  }

  static <T> T callSync(RestApiRequest<T> request) {
    return callSync(request, Boolean.TRUE);
  }

  static <T> T callSync(RestApiRequest<T> request, boolean ifCheck) {
    try {
      String str;
      log.debug("Request URL " + request.request.url());
      Response response = client.newCall(request.request).execute();
      if (response.code() != 200) {
        throw new HuobiApiException(
                HuobiApiException.EXEC_ERROR, "[Invoking] Response Status Error : "+response.code()+" message:"+response.message());
      }
      if (response != null && response.body() != null) {
        str = response.body().string();
        response.close();
      } else {
        throw new HuobiApiException(
                HuobiApiException.ENV_ERROR, "[Invoking] Cannot get the response from server");
      }
      log.debug("Response =====> " + str);
      JsonWrapper jsonWrapper = JsonWrapper.parseFromString(str);

      if (ifCheck) {
        checkResponse(jsonWrapper);
      }
      return request.jsonParser.parseJson(jsonWrapper);
    } catch (HuobiApiException e) {
      throw e;
    } catch (Exception e) {
      throw new HuobiApiException(
              HuobiApiException.ENV_ERROR, "[Invoking] Unexpected error: " + e.getMessage());
    }
  }


  static <T> void callASync(RestApiRequest<T> request, ResponseCallback<AsyncResult<T>> callback) {
    try {
      Call call = client.newCall(request.request);
      call.enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
          FailedAsyncResult<T> result = new FailedAsyncResult<>(
                  new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
                                        "[Invoking] Rest api call failed"));
          try {
            callback.onResponse(result);
          } catch (Exception exception) {
            log.error("[Invoking] Unexpected error: " + exception.getMessage(), e);
          }
        }

        @Override
        public void onResponse(Call call, Response response) {
          String str = "";
          JsonWrapper jsonWrapper;
          try {
            if (response != null && response.body() != null) {
              str = response.body().string();
              response.close();
            }
            jsonWrapper = JsonWrapper.parseFromString(str);
            checkResponse(jsonWrapper);

          } catch (HuobiApiException e) {
            FailedAsyncResult<T> result = new FailedAsyncResult<>(e);
            callback.onResponse(result);
            return;
          } catch (Exception e) {
            FailedAsyncResult<T> result = new FailedAsyncResult<>(
                    new HuobiApiException(
                            HuobiApiException.RUNTIME_ERROR, "[Invoking] Rest api call failed"));
            callback.onResponse(result);
            return;
          }
          try {
            SucceededAsyncResult<T> result = new SucceededAsyncResult<>(
                    request.jsonParser.parseJson(jsonWrapper));
            callback.onResponse(result);
          } catch (Exception e) {
            log.error("[Invoking] Unexpected error: " + e.getMessage(), e);
          }

        }
      });
    } catch (Throwable e) {
      throw new HuobiApiException(
              HuobiApiException.ENV_ERROR, "[Invoking] Unexpected error: " + e.getMessage());
    }
  }

  public static void enablePerformanceSwitch(boolean performanceSwitch) {
    PERFORMANCE_SWITCH = performanceSwitch;
  }

  static WebSocket createWebSocket(Request request, WebSocketListener listener) {
    return client.newWebSocket(request, listener);
  }

  static WebSocket createWebSocket(Request request, WebSocketListener listener, Proxy proxy) {
    client=getBuilder(proxy).build();
    return client.newWebSocket(request, listener);

  }
}


