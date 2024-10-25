package com.huobi.service.huobi.signature;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Security;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;

import com.huobi.exception.SDKException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

public class ApiSignatureED25519 {

  public static final String op = "op";
  public static final String opValue = "auth";
  private static final String accessKeyId = "AccessKeyId";
  private static final String signatureMethod = "SignatureMethod";
  private static final String signatureMethodValue = "Ed25519"; // 使用 Ed25519 签名
  private static final String signatureVersion = "SignatureVersion";
  private static final String signatureVersionValue = "2";
  private static final String timestamp = "Timestamp";
  private static final String signature = "Signature";

  private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter
          .ofPattern("uuuu-MM-dd'T'HH:mm:ss");
  private static final ZoneId ZONE_GMT = ZoneId.of("Z");

  private Ed25519PrivateKeyParameters privateKey;
  private Ed25519PublicKeyParameters accessKey;

  // 构造函数接收 Base64 编码的私钥和公钥
  public void ApiSignature(String base64PublicKey,String base64PrivateKey) throws Exception {

//    this.privateKey = (Ed25519PrivateKeyParameters) PrivateKeyFactory.createKey(Base64.getDecoder().decode(base64PrivateKey));

    Security.addProvider(new BouncyCastleProvider());
    // 从 PEM 格式中提取私钥
    try (PEMParser pemParser = new PEMParser(new StringReader(base64PrivateKey))) {
      Object object = pemParser.readObject();
      if (object instanceof PrivateKeyInfo) {
        PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) object;
        // 使用 JcaPEMKeyConverter 将 PrivateKeyInfo 转换为 Java PrivateKey
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);
        // 将 Java PrivateKey 转换为 Ed25519PrivateKeyParameters
        this.privateKey = (Ed25519PrivateKeyParameters) PrivateKeyFactory.createKey(privateKey.getEncoded());
      } else {
        throw new IllegalArgumentException("Invalid PEM format: not a private key");
      }
    }

  }

  public void createSignature(String method, String host, String uri, UrlParamsBuilder builder,String accessKey) {
    StringBuilder sb = new StringBuilder(1024);

    // 检查 API 密钥是否为空
    if ( privateKey == null || "".equals(privateKey)) {
      throw new SDKException(SDKException.KEY_MISSING,
              "API key and secret key are required");
    }

    sb.append(method.toUpperCase()).append('\n')
            .append(host.toLowerCase()).append('\n')
            .append(uri).append('\n');

    builder.putToUrl(accessKeyId, String.valueOf(accessKey))
            .putToUrl(signatureVersion,signatureVersionValue)
            .putToUrl(timestamp, gmtNow())
            .putToUrl(signatureMethod, signatureMethodValue);


    sb.append(builder.buildSignature());
    System.out.println(sb.toString());
    System.out.println(sb.toString().length());

    // 使用 Ed25519 进行签名
    Ed25519Signer signer = new Ed25519Signer();
    signer.init(true, privateKey);

    signer.update(sb.toString().getBytes(StandardCharsets.UTF_8), 0, sb.length());
    byte[] signatureBytes = signer.generateSignature();
    String actualSign = Base64.getEncoder().encodeToString(signatureBytes);

    builder.putToUrl(signature, actualSign);
  }

  private static long epochNow() {
    return Instant.now().getEpochSecond();
  }

  static String gmtNow() {
    return Instant.ofEpochSecond(epochNow()).atZone(ZONE_GMT).format(DT_FORMAT);
  }
}