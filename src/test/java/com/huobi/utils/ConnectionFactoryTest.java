package com.huobi.utils;

import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import com.huobi.exception.SDKException;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"okhttp3.*"})
public class ConnectionFactoryTest {

  @Before
  public void init() {

  }


  @Test
  public void execute_test() {

    // 测试正常请求
    String url = "http://api.huobi.vn/market/history/kline?period=1min&size=2&symbol=htusdt";
    Request executeRequest = new Request.Builder()
        .url(url)
        .addHeader("Content-Type", "application/x-www-form-urlencoded")
        .build();

    String str = ConnectionFactory.execute(executeRequest);

    Assert.assertNotNull(str);
    Assert.assertTrue(str.length() > 0);

    // 测试访问状态异常捕获
    String url1 = "http://api.huobi.vn/market/notfund";
    Request executeRequest1 = new Request.Builder()
        .url(url1)
        .addHeader("Content-Type", "application/x-www-form-urlencoded")
        .build();
    try {
      ConnectionFactory.execute(executeRequest1);
      Assert.fail("!!! no catch exception !!!");
    } catch (SDKException e) {
      e.printStackTrace();
      Assert.assertTrue(SDKException.EXEC_ERROR.equals(e.getErrCode()));
    }

  }

  @Test
  public void create_websocket_test() {

    Request request = new Request.Builder().url("wss://api.huobi.vn/ws").build();
    WebSocket socket = ConnectionFactory.createWebSocket(request, new WebSocketListener() {
    });

    Assert.assertNotNull(socket);
  }


}
