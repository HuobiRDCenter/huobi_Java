package com.huobi.service.huobi.signature;

import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.util.PrivateKeyFactory;

import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ApiSignature.class})
@PowerMockIgnore({"javax.crypto.*"})
public class ApiSignatureTest {

  @Test
  public void testSignature() {
    PowerMockito.mockStatic(ApiSignature.class);
    PowerMockito.when(ApiSignature.gmtNow()).thenReturn("123");
    ApiSignature signature = new ApiSignature();
    UrlParamsBuilder builder = UrlParamsBuilder.build();
    signature.createSignature("123", "456", "GET", "host", "url", builder);
    assertEquals("AccessKeyId=123"
            + "&Signature=Hfdw0d%2F3ZOEB2eHJZiwVZ%2F1YLL62MFuKKmSW83vM3ps%3D"
            + "&SignatureMethod=HmacSHA256"
            + "&SignatureVersion=2"
            + "&Timestamp=123",
        builder.buildSignature());
  }

  @Test
  public void testVerifySign() throws Exception {
    Ed25519PrivateKeyParameters privateKey = (Ed25519PrivateKeyParameters) PrivateKeyFactory.createKey(Base64.getDecoder().decode("MC4CAQAwBQYDK2VwBCIEILHeu7fAq16wUfz/w91bQVEQO+J1dJ6HE4YGdyfYMPqt"));

    Ed25519PublicKeyParameters publicKey = (Ed25519PublicKeyParameters) PublicKeyFactory.createKey(Base64.getDecoder().decode("MCowBQYDK2VwAyEAIQ3rnAT8JKIBLyitmADX7Xtj2F5TbCDqpWBnYWlZ5rU="));

    String data = "{\"method\":\"GET\",\"requestUrl\":\"/v2/settings/common/symbols11\",\"serverName\":\"dawn-broker-common-pro.global-base.tc-jp1.huobiapps11.com\",\"privilege\":\"WRITE\",\"params\":{\"SignatureVersion\":[\"2\"],\"AccessKeyId\":[\"ghxertfvbf-946bcb5b-689d6d30-73277\"],\"Signature\":[\"YFcOXnxeLTxZnM0ONUqaBc2NYJ6PdD26Ik+o9yzUZAU=\"],\"SignatureMethod\":[\"HmacSHA256\"],\"Timestamp\":[\"2024-06-26T06:35:16\"]}}";
    byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);

    Ed25519Signer signer = new Ed25519Signer();
    signer.init(true, privateKey);
    signer.update(dataBytes, 0, dataBytes.length);
    byte[] signatureBytes = signer.generateSignature();
    String signature = Base64.getEncoder().encodeToString(signatureBytes);

    System.out.println(signature + " " + signature.length());
    System.out.println(verifySign(publicKey, data, signature));
  }

  private boolean verifySign(Ed25519PublicKeyParameters publicKey, String data, String signature) {
    //初始化签名器
    Ed25519Signer verifier = new Ed25519Signer();
    verifier.init(false, publicKey);
    //执行验签
    byte[] bytesMsg = data.getBytes(StandardCharsets.UTF_8);
    verifier.update(bytesMsg, 0, bytesMsg.length);
    return verifier.verifySignature(Base64.getDecoder().decode(signature));
  }

}
