- JAVA signature - ApiSignature.java

```
package com.test.huobi;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class ApiSignature {

  public static final String op = "op";
  public static final String opValue = "auth";
  private static final String accessKeyId = "AccessKeyId";
  private static final String signatureMethod = "SignatureMethod";
  private static final String signatureMethodValue = "HmacSHA256";
  private static final String signatureVersion = "SignatureVersion";
  private static final String signatureVersionValue = "2";
  private static final String timestamp = "Timestamp";
  private static final String signature = "Signature";

  private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter
      .ofPattern("uuuu-MM-dd'T'HH:mm:ss");
  private static final ZoneId ZONE_GMT = ZoneId.of("Z");


  public void createSignature(String accessKey, String secretKey, String method, String host,
      String uri, UrlParamsBuilder builder) {
    StringBuilder sb = new StringBuilder(1024);

    if (accessKey == null || "".equals(accessKey) || secretKey == null || "".equals(secretKey)) {
      throw new HuobiApiException(HuobiApiException.KEY_MISSING,
          "API key and secret key are required");
    }

    sb.append(method.toUpperCase()).append('\n')
        .append(host.toLowerCase()).append('\n')
        .append(uri).append('\n');

    builder.putToUrl(accessKeyId, accessKey)
        .putToUrl(signatureVersion, signatureVersionValue)
        .putToUrl(signatureMethod, signatureMethodValue)
        .putToUrl(timestamp, gmtNow());

    sb.append(builder.buildSignature());
    Mac hmacSha256;
    try {
      hmacSha256 = Mac.getInstance(signatureMethodValue);
      SecretKeySpec secKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
          signatureMethodValue);
      hmacSha256.init(secKey);
    } catch (NoSuchAlgorithmException e) {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
          "[Signature] No such algorithm: " + e.getMessage());
    } catch (InvalidKeyException e) {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
          "[Signature] Invalid key: " + e.getMessage());
    }
    String payload = sb.toString();
    byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));

    String actualSign = Base64.getEncoder().encodeToString(hash);

    builder.putToUrl(signature, actualSign);

  }

  private static long epochNow() {
    return Instant.now().getEpochSecond();
  }

  static String gmtNow() {
    return Instant.ofEpochSecond(epochNow()).atZone(ZONE_GMT).format(DT_FORMAT);
  }
}
```

-UrlParamsBuilder.class

```
package com.test.huobi;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class UrlParamsBuilder {

  class ParamsMap {

    final Map<String, String> map = new HashMap<>();
    final Map<String, List> stringListMap = new HashMap<>();

    void put(String name, String value) {

      if (name == null || "".equals(name)) {
        throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
            "[URL] Key can not be null");
      }
      if (value == null || "".equals(value)) {
        return;
      }

      map.put(name, value);
    }

    void put(String name, Integer value) {
      put(name, value != null ? Integer.toString(value) : null);
    }

    void put(String name, Date value, String format) {
      SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
      put(name, value != null ? dateFormatter.format(value) : null);
    }

    void put(String name, Long value) {
      put(name, value != null ? Long.toString(value) : null);
    }

    void put(String name, BigDecimal value) {
      put(name, value != null ? value.toPlainString() : null);
    }

  }

  private static final MediaType JSON_TYPE = MediaType.parse("application/json");
  private final ParamsMap paramsMap = new ParamsMap();
  private final ParamsMap postBodyMap = new ParamsMap();
  private boolean postMode = false;

  public static UrlParamsBuilder build() {
    return new UrlParamsBuilder();
  }

  private UrlParamsBuilder() {
  }

  public UrlParamsBuilder setPostMode(boolean mode) {
    postMode = mode;
    return this;
  }

  public <T extends Enum> UrlParamsBuilder putToUrl(String name, T value) {
    if (value != null) {
      paramsMap.put(name, value.toString());
    }
    return this;
  }

  public UrlParamsBuilder putToUrl(String name, String value) {
    paramsMap.put(name, value);
    return this;
  }

  public UrlParamsBuilder putToUrl(String name, Date value, String format) {
    paramsMap.put(name, value, format);
    return this;
  }

  public UrlParamsBuilder putToUrl(String name, Integer value) {
    paramsMap.put(name, value);
    return this;
  }

  public UrlParamsBuilder putToUrl(String name, Long value) {
    paramsMap.put(name, value);
    return this;
  }

  public UrlParamsBuilder putToUrl(String name, BigDecimal value) {
    paramsMap.put(name, value);
    return this;
  }

  public UrlParamsBuilder putToPost(String name, String value) {
    postBodyMap.put(name, value);
    return this;
  }

  public <T extends Enum> UrlParamsBuilder putToPost(String name, T value) {
    if (value != null) {
      postBodyMap.put(name, value.toString());
    }
    return this;
  }

  public UrlParamsBuilder putToPost(String name, Date value, String format) {
    postBodyMap.put(name, value, format);
    return this;
  }

  public UrlParamsBuilder putToPost(String name, Integer value) {
    postBodyMap.put(name, value);
    return this;
  }

  public <T> UrlParamsBuilder putToPost(String name, List<String> list) {
    postBodyMap.stringListMap.put(name, list);
    return this;
  }

  public UrlParamsBuilder putToPost(String name, Long value) {
    postBodyMap.put(name, value);
    return this;
  }

  public UrlParamsBuilder putToPost(String name, BigDecimal value) {
    postBodyMap.put(name, value);
    return this;
  }

  public String buildUrl() {
    Map<String, String> map = new HashMap<>(paramsMap.map);
    StringBuilder head = new StringBuilder("?");
    return AppendUrl(map, head);

  }

  public String buildSignature() {
    Map<String, String> map = new TreeMap<>(paramsMap.map);
    StringBuilder head = new StringBuilder();
    return AppendUrl(map, head);

  }

  private String AppendUrl(Map<String, String> map, StringBuilder stringBuilder) {
    for (Map.Entry<String, String> entry : map.entrySet()) {
      if (!("").equals(stringBuilder.toString())) {
        stringBuilder.append("&");
      }
      stringBuilder.append(entry.getKey());
      stringBuilder.append("=");
      stringBuilder.append(urlEncode(entry.getValue()));
    }
    return stringBuilder.toString();
  }

  public RequestBody buildPostBody() {
    if (postBodyMap.stringListMap.isEmpty()) {
      if (postBodyMap.map.isEmpty()) {
        return RequestBody.create(JSON_TYPE, "");
      } else {
        return RequestBody.create(JSON_TYPE, JSON.toJSONString(postBodyMap.map));
      }
    } else {
      return RequestBody.create(JSON_TYPE, JSON.toJSONString(postBodyMap.stringListMap));


    }
  }

  public boolean hasPostParam() {
    return !postBodyMap.map.isEmpty() || postMode;
  }


  public String buildUrlToJsonString() {
    return JSON.toJSONString(paramsMap.map);
  }

  /**
   * 使用标准URL Encode编码。注意和JDK默认的不同，空格被编码为%20而不是+。
   *
   * @param s String字符串
   * @return URL编码后的字符串
   */
  private static String urlEncode(String s) {
    try {
      return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
    } catch (UnsupportedEncodingException e) {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
          "[URL] UTF-8 encoding not supported!");
    }
  }
}
```

- HuobiApiException.java

```
package com.test.huobi;

public class HuobiApiException extends RuntimeException {


  public static final String RUNTIME_ERROR = "RuntimeError";
  public static final String INPUT_ERROR = "InputError";
  public static final String KEY_MISSING = "KeyMissing";
  public static final String SYS_ERROR = "SysError";
  public static final String SUBSCRIPTION_ERROR = "SubscriptionError";
  public static final String ENV_ERROR = "EnvironmentError";
  public static final String EXEC_ERROR = "ExecuteError";
  private final String errCode;

  public HuobiApiException(String errType, String errMsg) {
    super(errMsg);
    this.errCode = errType;
  }

  public HuobiApiException(String errType, String errMsg, Throwable e) {
    super(errMsg, e);
    this.errCode = errType;
  }

  public String getErrType() {
    return this.errCode;
  }
}
```

- JAVA demo - HuobiSignatureDemo.java

```
package com.test.huobi;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HuobiSignatureDemo {

  static final String HOST = "https://api.huobi.vn";
  static OkHttpClient okHttpClient = new OkHttpClient();
  static final String API_KEY = "api key";
  static final String SECRET_KEY = "secret key";
  static final Long SPOT_ACCOUNT_ID =123456L;

  public static void main(String[] args) {

    String clientOrderId = "TT" + System.nanoTime();
    String symbol = "htusdt";

    // 创建订单
    Long orderId = createOrder(SPOT_ACCOUNT_ID, clientOrderId, symbol, "buy-limit", new BigDecimal("2"), new BigDecimal("1"));

    // 查询订单
    getOrderByClientOrderId(clientOrderId);

    // 撤销订单
    submitCancelClientOrder(clientOrderId);


  }

  public static Long createOrder(Long accountId, String clientOrderId, String symbol, String type, BigDecimal price, BigDecimal amount) {

    String path = "/v1/order/orders/place";

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("account-id", accountId)
        .putToPost("amount", amount)
        .putToPost("price", price)
        .putToPost("symbol", symbol)
        .putToPost("type", type)
        .putToPost("client-order-id", clientOrderId)
        .putToPost("source", "spot-api");

    new ApiSignature().createSignature(API_KEY, SECRET_KEY, "POST", getHost(), path, builder);

    String requestUrl = HOST + path + builder.buildUrl();
    Request executeRequest = new Request.Builder().url(requestUrl).post(builder.buildPostBody())
        .addHeader("Content-Type", "application/json").build();

    String response = execute(executeRequest);
    System.out.println(response);
    JSONObject jsonObject = JSON.parseObject(response);
    return jsonObject.getLong("data");
  }

  public static void getOrderByClientOrderId(String clientOrderId) {
    String path = "/v1/order/orders/getClientOrder";

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("clientOrderId", clientOrderId);

    new ApiSignature().createSignature(API_KEY, SECRET_KEY, "GET", getHost(), path, builder);

    String requestUrl = HOST + path + builder.buildUrl();

    Request executeRequest = new Request.Builder().url(requestUrl)
        .addHeader("Content-Type", "application/x-www-form-urlencoded").build();

    String response = execute(executeRequest);
    System.out.println(response);
  }

  public static void submitCancelClientOrder(String clientOrderId) {
    String path = "/v1/order/orders/submitCancelClientOrder";

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("client-order-id", clientOrderId);

    new ApiSignature().createSignature(API_KEY, SECRET_KEY, "POST", getHost(), path, builder);

    String requestUrl = HOST + path + builder.buildUrl();
    Request executeRequest = new Request.Builder().url(requestUrl).post(builder.buildPostBody())
        .addHeader("Content-Type", "application/json").build();

    String response = execute(executeRequest);
    System.out.println(response);
  }

  public static String getHost() {
    try {
      URL url = new URL(HOST);
      return url.getHost();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String execute(Request executeRequest) {
    Response response = null;
    String str = null;
    try {
      response = okHttpClient.newCall(executeRequest).execute();
      if (response.code() == 200) {
        str = response.body().string();
      } else {
        str = response.toString();
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (response != null) {
        response.close();
      }
    }
    return str;
  }
}
```