package com.huobi.service.huobi.connection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Request;

import com.huobi.client.exception.HuobiApiException;
import com.huobi.client.impl.utils.EtfResult;
import com.huobi.constant.Options;
import com.huobi.exception.SDKException;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.ConnectionFactory;

public class HuobiRestConnection {

  private Options options;

  public Options getOptions() {
    return options;
  }

  public HuobiRestConnection(Options options) {
    this.options = options;
  }

  public JSONObject executeGet(String path, UrlParamsBuilder paramsBuilder){

    Options options = this.getOptions();

    String url = options.getOptionRestHost() + path + paramsBuilder.buildUrl();

    Request executeRequest =  new Request.Builder()
        .url(url)
        .addHeader("Content-Type", "application/x-www-form-urlencoded")
        .build();

    String resp = ConnectionFactory.execute(executeRequest);
    return checkAndGetResponse(resp);
  }


  public JSONObject executePostAndSignature(){

    // TODO 验签

    String resp = ConnectionFactory.execute(null);
    return checkAndGetResponse(resp);
  }


  private JSONObject checkAndGetResponse(String resp) {
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
      } else {
        throw new SDKException(SDKException.RUNTIME_ERROR, "[Invoking] Status cannot be found in response.");
      }
    } catch (SDKException e) {
      throw e;
    } catch (Exception e) {
      throw new SDKException(HuobiApiException.RUNTIME_ERROR, "[Invoking] Unexpected error: " + e.getMessage());
    }

    return json;
  }

}
