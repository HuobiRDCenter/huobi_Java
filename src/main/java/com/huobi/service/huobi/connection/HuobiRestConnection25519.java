package com.huobi.service.huobi.connection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobi.constant.EtfResult;
import com.huobi.constant.Options;
import com.huobi.exception.SDKException;
import com.huobi.service.huobi.signature.ApiSignature;
import com.huobi.service.huobi.signature.ApiSignatureED25519;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.ConnectionFactory;
import okhttp3.Request;

import java.net.MalformedURLException;
import java.net.URL;

public class HuobiRestConnection25519 extends HuobiRestConnection {

    public HuobiRestConnection25519(Options options) {
        super(options);
    }

    @Override
    public JSONObject executeGetWithSignature(String path, UrlParamsBuilder paramsBuilder) {
        Options options = this.getOptions();
        System.out.println("API Key: " + options.getApiKey());
        System.out.println("Secret Key: " + options.getSecretKey());

        // 创建 ApiSignatureED25519 实例
        ApiSignatureED25519 apiSignature = new ApiSignatureED25519();
        try {
            apiSignature.ApiSignature(options.getApiKey(), options.getSecretKey()); // 确保顺序正确
        } catch (Exception e) {
            e.printStackTrace();
            throw new SDKException(SDKException.KEY_MISSING, "Invalid API key or secret key: " + e.getMessage());
        }

        String requestUrl = options.getRestHost() + path;
        apiSignature.createSignature("GET", host, path, paramsBuilder, options.getApiKey());
        requestUrl += paramsBuilder.buildUrl();
        System.out.println(requestUrl);

        Request executeRequest = new Request.Builder()
                .url(requestUrl)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("x-connecting-ip", "192.2.12.19")
                .addHeader("HB-REAL-IP", "192.2.12.19")
                .addHeader("X-Forwarded-For", "192.2.12.19")
                .addHeader("X-Real-IP", "192.2.12.19")
                .build();

        String resp = ConnectionFactory.execute(executeRequest);
        return checkAndGetResponse(resp);
    }

    @Override
    public JSONObject executePostWithSignature(String path, UrlParamsBuilder paramsBuilder) {
        Options options = this.getOptions();
        ApiSignatureED25519 apiSignature = new ApiSignatureED25519();
        try {
            apiSignature.ApiSignature( options.getApiKey(),options.getSecretKey()); // 确保顺序正确
        } catch (Exception e) {
            throw new SDKException(SDKException.KEY_MISSING, "Invalid API key or secret key: " + e.getMessage());
        }

        String requestUrl = options.getRestHost() + path;
        apiSignature.createSignature("POST", host, path, paramsBuilder, options.getApiKey());
        requestUrl += paramsBuilder.buildUrl();

        Request executeRequest = new Request.Builder()
                .url(requestUrl)
                .post(paramsBuilder.buildPostBody())
                .addHeader("Content-Type", "application/json")
                .build();

        String resp = ConnectionFactory.execute(executeRequest);
        return checkAndGetResponse(resp);
    }

}
