package com.huobi.client.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import com.huobi.client.AsyncResult;
import com.huobi.client.ResponseCallback;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RestApiInvoker.class, OkHttpClient.class, Response.class, ResponseBody.class,
    Call.class})
//@PowerMockIgnore({"okhttp3.OkHttpClient"})
@SuppressWarnings("unchecked")
public class TestApiInvoking {

  private static String data = "{\n"
      + "\t\"status\": \"ok\",\n"
      + "\t\"data\": 123\n"
      + "}";

  @Mock
  Response mockResponse;
  @Mock
  ResponseBody mockResponseBody;

  MockHttpCall mockCall;

  static OkHttpClient mockOkHttpClient = null;

  @Before
  public void init() {
    try {
      if (mockOkHttpClient == null) {
        mockOkHttpClient = mock(OkHttpClient.class);
      }
      whenNew(OkHttpClient.class).withNoArguments().thenReturn(mockOkHttpClient);
      mockCall = new MockHttpCall(mockResponse);
      when(mockOkHttpClient.newCall(any())).thenReturn(mockCall);
      when(mockResponse.body()).thenReturn(mockResponseBody);
      when(mockResponseBody.string()).thenReturn(data);
    } catch (Exception e) {
    }
  }


  @Test
  public void testASync() {
    RestApiRequest<Long> restApiRequest = new RestApiRequest<>();
    restApiRequest.jsonParser = (jsonWrapper) -> jsonWrapper.getLong("data");
    ResponseCallback<AsyncResult<Long>> mockCallback = mock(ResponseCallback.class);
    RestApiInvoker.callASync(restApiRequest, mockCallback);
    ArgumentCaptor<AsyncResult> captor = ArgumentCaptor.forClass(AsyncResult.class);
    verify(mockCallback, times(1)).onResponse(captor.capture());
    assertEquals(123L, ((Long) captor.getValue().getData()).longValue());
  }


}
