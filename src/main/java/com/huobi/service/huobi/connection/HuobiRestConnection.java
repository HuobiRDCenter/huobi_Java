package com.huobi.service.huobi.connection;

import java.net.MalformedURLException;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Request;

import com.huobi.constant.EtfResult;
import com.huobi.constant.Options;
import com.huobi.exception.SDKException;
import com.huobi.service.huobi.signature.ApiSignature;
import com.huobi.service.huobi.signature.ApiSignatureED25519;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.ConnectionFactory;

public class HuobiRestConnection {

    private Options options;

    String host;

    public Options getOptions() {
        return options;
    }

    public HuobiRestConnection(Options options) {
        this.options = options;
        try {
            this.host = new URL(this.options.getRestHost()).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public JSONObject executeGet(String path, UrlParamsBuilder paramsBuilder) {

        Options options = this.getOptions();

        String url = options.getRestHost() + path + paramsBuilder.buildUrl();

        Request executeRequest = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        String resp = ConnectionFactory.execute(executeRequest);
        return checkAndGetResponse(resp);
    }

    public String executeGetString(String url, UrlParamsBuilder paramsBuilder) {
        String realUrl = url + paramsBuilder.buildUrl();
        Request executeRequest = new Request.Builder()
                .url(realUrl)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        String resp = ConnectionFactory.execute(executeRequest);
        return resp;
    }

  public JSONObject executeGetWithSignature(String path, UrlParamsBuilder paramsBuilder) {


    Options options = this.getOptions();

    String requestUrl =  options.getRestHost() + path;
    new ApiSignature().createSignature(options.getApiKey(), options.getSecretKey(), "GET", host, path, paramsBuilder);
    requestUrl += paramsBuilder.buildUrl();
    System.out.println(requestUrl);

    Request executeRequest = new Request.Builder().url(requestUrl)
        .addHeader("Content-Type", "application/x-www-form-urlencoded").build();

    String resp = ConnectionFactory.execute(executeRequest);
    return checkAndGetResponse(resp);
  }


  public JSONObject executePostWithSignature(String path, UrlParamsBuilder paramsBuilder){
    Options options = this.getOptions();
    String requestUrl =  options.getRestHost() + path;
    new ApiSignature().createSignature(options.getApiKey(), options.getSecretKey(), "POST", host, path, paramsBuilder);
    requestUrl += paramsBuilder.buildUrl();
    Request executeRequest = new Request.Builder().url(requestUrl).post(paramsBuilder.buildPostBody())
        .addHeader("Content-Type", "application/json").build();

    String resp = ConnectionFactory.execute(executeRequest);
    return checkAndGetResponse(resp);
  }

   /* public JSONObject executeGetWithSignature(String path, UrlParamsBuilder paramsBuilder) {
        Options options = this.getOptions();
        System.out.println("API Key: " + options.getApiKey());
        System.out.println("Secret Key: " + options.getSecretKey());
//        paramsBuilder.putToUrl("AccessKeyId", options.getApiKey());
// 创建 ApiSignatureED25519 实例并传递 Base64 编码的私钥和公钥
        ApiSignatureED25519 apiSignature = new ApiSignatureED25519();
        try {
            apiSignature.ApiSignature( options.getApiKey(),options.getSecretKey()); // 确保顺序正确
        } catch (Exception e) {
            // 抛出 SDKException 只使用 String 参数
            e.printStackTrace();
            throw new SDKException(SDKException.KEY_MISSING, "Invalid API key or secret key: " + e.getMessage());
        }
        String requestUrl = options.getRestHost() + path;
        apiSignature.createSignature("GET", host, path, paramsBuilder,options.getApiKey());
        requestUrl += paramsBuilder.buildUrl();
        System.out.println(requestUrl);

        Request executeRequest = new Request.Builder().url(requestUrl)
                .addHeader("Content-Type", "application/x-www-form-urlencoded").
                addHeader("x-connecting-ip", "192.2.12.19")
                .addHeader("HB-REAL-IP", "192.2.12.19")
                .addHeader("X-Forwarded-For", "192.2.12.19")
                .addHeader("X-Real-IP", "192.2.12.19")
        .build();

        String resp = ConnectionFactory.execute(executeRequest);
        return checkAndGetResponse(resp);
    }


    public JSONObject executePostWithSignature(String path, UrlParamsBuilder paramsBuilder) {

        Options options = this.getOptions();
        ApiSignatureED25519 apiSignature = new ApiSignatureED25519();
        try {
            apiSignature.ApiSignature(options.getSecretKey(), options.getApiKey()); // 确保顺序正确
        } catch (Exception e) {
            // 抛出 SDKException 只使用 String 参数
            throw new SDKException(SDKException.KEY_MISSING, "Invalid API key or secret key: " + e.getMessage());
        }
        String requestUrl = options.getRestHost() + path;
        apiSignature.createSignature("POST", host, path, paramsBuilder,options.getApiKey());
        requestUrl += paramsBuilder.buildUrl();
        Request executeRequest = new Request.Builder().url(requestUrl).post(paramsBuilder.buildPostBody())
                .addHeader("Content-Type", "application/json")
                .addHeader("x-connecting-ip", "192.2.12.19")
                .addHeader("HB-REAL-IP", "192.2.12.19")
                .addHeader("X-Forwarded-For", "192.2.12.19")
                .addHeader("X-Real-IP", "192.2.12.19").build();

        String resp = ConnectionFactory.execute(executeRequest);
        return checkAndGetResponse(resp);
    }*/

    JSONObject checkAndGetResponse(String resp) {
        JSONObject json = JSON.parseObject(resp);
        try {
            if (json.containsKey("status")) {
                String status = json.getString("status");
                if ("error".equals(status)) {
                    String err_code = json.getString("err-code");
                    String err_msg = json.getString("err-msg");
                    throw new SDKException(SDKException.EXEC_ERROR, "[Executing] " + err_code + ": " + err_msg);
                } else if (!"ok".equals(status)) {
                    throw new SDKException(SDKException.RUNTIME_ERROR, "[Invoking] Response is not expected: " + status);
                }
            } else if (json.containsKey("success")) {
                boolean success = json.getBoolean("success");
                if (!success) {
                    String err_code = EtfResult.checkResult(json.getInteger("code"));
                    String err_msg = json.getString("message");
                    if ("".equals(err_code)) {
                        throw new SDKException(SDKException.EXEC_ERROR, "[Executing] " + err_msg);
                    } else {
                        throw new SDKException(SDKException.EXEC_ERROR, "[Executing] " + err_code + ": " + err_msg);
                    }
                }
            } else if (json.containsKey("code")) {
                int code = json.getInteger("code");
                if (code != 200) {
                    String message = json.getString("message");
                    throw new SDKException(SDKException.EXEC_ERROR, "[Executing]" + message);
                }
            } else {
                throw new SDKException(SDKException.RUNTIME_ERROR, "[Invoking] Status cannot be found in response.");
            }
        } catch (SDKException e) {
            throw e;
        } catch (Exception e) {
            throw new SDKException(SDKException.RUNTIME_ERROR, "[Invoking] Unexpected error: " + e.getMessage());
        }

        return json;
    }

}
