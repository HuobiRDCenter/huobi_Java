package com.huobi.client.impl;

import com.huobi.client.SubscriptionOptions;
import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.JsonWrapper;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.zip.GZIPOutputStream;
import okio.ByteString;
import org.powermock.api.support.membermodification.MemberModifier;

@SuppressWarnings("unchecked")
public class MockWebsocketConnection extends WebSocketConnection {

  private WebsocketRequest request;
  private MockOkHttpWebsocket mockWebsocket;
  static private WebSocketWatchDog watchDog = new WebSocketWatchDog(new SubscriptionOptions());

//  public MockWebsocketConnection() {
//    super(null, options, null);
//  }

  MockWebsocketConnection(String apiKey, String secretKey, WebsocketRequest request) {
    super(apiKey, secretKey, new SubscriptionOptions(), request, watchDog);
    this.request = request;
  }

  void connect() {
    try {
      mockWebsocket = new MockOkHttpWebsocket();
      MemberModifier.field(MockWebsocketConnection.class, "webSocket")
          .set(this, mockWebsocket);
      super.onOpen(mockWebsocket, null);
    } catch (Exception e) {
    }
  }

  MockOkHttpWebsocket getWebsocket() {
    return mockWebsocket;
  }

  @Override
  void send(String str) {
    mockWebsocket.send(str);
  }

  ByteString buildMockServerMessage(String str) {
    if (str == null || str.length() == 0) {
      return null;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream gzip;
    try {
      gzip = new GZIPOutputStream(out);
      gzip.write(str.getBytes("utf8"));
      gzip.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ByteString.of(ByteBuffer.wrap(out.toByteArray()));
  }

  void mockOnReceive(String jsonString) {
    request.updateCallback.onReceive(
        request.jsonParser.parseJson(JsonWrapper.parseFromString(jsonString)));
  }

  void mockOnMessage(String msg) {
    onMessage(mockWebsocket, buildMockServerMessage(msg));
  }

  void mockOnError(String errorString) {
    request.errorHandler.onError(new HuobiApiException("test", "test"));
  }

//  @Test
//  public void testConnection() {
//
//  }
}
