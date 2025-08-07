package com.huobi.service.huobi.signature;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.huobi.exception.SDKException;

public class ApiSignatureV2 {

  static final String op = "op";
  static final String opValue = "auth";
  private static final String ACCESS_KEY_NAME = "accessKey";
  private static final String SIGNATURE_METHOD_NAME = "signatureMethod";
  private static final String SIGNATURE_METHOD_VALUE = "HmacSHA256";
  private static final String SIGNATURE_VERSION_NAME = "signatureVersion";
  public static final String SIGNATURE_VERSION_VALUE = "2.1";
  private static final String TIMESTAMP_NAME = "timestamp";
  private static final String SIGNATURE_NAME = "signature";

  private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter
      .ofPattern("uuuu-MM-dd'T'HH:mm:ss");
  private static final ZoneId ZONE_GMT = ZoneId.of("Z");


  public void createSignature(String accessKey, String secretKey, String method, String host,
      String uri, UrlParamsBuilder builder) {
    StringBuilder sb = new StringBuilder(1024);

    if (accessKey == null || "".equals(accessKey) || secretKey == null || "".equals(secretKey)) {
      throw new SDKException(SDKException.KEY_MISSING,
          "API key and secret key are required");
    }

    sb.append(method.toUpperCase()).append('\n')
        .append(host.toLowerCase()).append('\n')
        .append(uri).append('\n');

    builder.putToUrl(ACCESS_KEY_NAME, accessKey)
        .putToUrl(SIGNATURE_VERSION_NAME, SIGNATURE_VERSION_VALUE)
        .putToUrl(SIGNATURE_METHOD_NAME, SIGNATURE_METHOD_VALUE)
        .putToUrl(TIMESTAMP_NAME, gmtNow());

    sb.append(builder.buildSignature());
    Mac hmacSha256;
    try {
      hmacSha256 = Mac.getInstance(SIGNATURE_METHOD_VALUE);
      SecretKeySpec secKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SIGNATURE_METHOD_VALUE);
      hmacSha256.init(secKey);
    } catch (NoSuchAlgorithmException e) {
      throw new SDKException(SDKException.RUNTIME_ERROR, "[Signature] No such algorithm: " + e.getMessage());
    } catch (InvalidKeyException e) {
      throw new SDKException(SDKException.RUNTIME_ERROR, "[Signature] Invalid key: " + e.getMessage());
    }
    String payload = sb.toString();
    byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));

    String actualSign = Base64.getEncoder().encodeToString(hash);

    builder.putToUrl(SIGNATURE_NAME, actualSign);

  }

  private static long epochNow() {
    return Instant.now().getEpochSecond();
  }

  static String gmtNow() {
    return Instant.ofEpochSecond(epochNow()).atZone(ZONE_GMT).format(DT_FORMAT);
  }
}
