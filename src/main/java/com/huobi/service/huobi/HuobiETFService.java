package com.huobi.service.huobi;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.client.ETFClient;
import com.huobi.client.req.etf.ETFSwapListRequest;
import com.huobi.client.req.etf.ETFSwapRequest;
import com.huobi.constant.Constants;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.Options;
import com.huobi.constant.enums.EtfSwapDirectionEnum;
import com.huobi.model.etf.ETFConfig;
import com.huobi.model.etf.ETFSwapRecord;
import com.huobi.service.huobi.connection.HuobiRestConnection;
import com.huobi.service.huobi.parser.etf.ETFConfigParser;
import com.huobi.service.huobi.parser.etf.ETFSwapRecordParser;
import com.huobi.service.huobi.signature.UrlParamsBuilder;
import com.huobi.utils.InputChecker;

public class HuobiETFService implements ETFClient {

  public static final String GET_SWAP_CONFIG_PATH = "/etf/swap/config";

  public static final String ETF_SWAP_IN_PATH = "/etf/swap/in";
  public static final String ETF_SWAP_OUT_PATH = "/etf/swap/out";
  public static final String GET_SWAP_LIST_PATH = "/etf/swap/list";


  private Options options;

  private HuobiRestConnection restConnection;

  public HuobiETFService(Options options) {
    this.options = options;
    this.restConnection = new HuobiRestConnection(options);
  }


  @Override
  public ETFConfig getConfig(String etfName) {

    InputChecker.checker()
        .shouldNotNull(etfName, "etf_name");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("etf_name", etfName);

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_SWAP_CONFIG_PATH, builder);
    JSONObject data = jsonObject.getJSONObject("data");
    return new ETFConfigParser().parse(data);
  }

  @Override
  public void etfSwap(ETFSwapRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getDirection(), "direction")
        .shouldNotNull(request.getAmount(), "amount");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToPost("etf_name", request.getEtfName())
        .putToPost("amount", request.getAmount());

    String path = null;
    if (request.getDirection() == EtfSwapDirectionEnum.SWAP_IN_ETF) {
      path = ETF_SWAP_IN_PATH;
    } else {
      path = ETF_SWAP_OUT_PATH;
    }

    restConnection.executePostWithSignature(path, builder);
  }

  @Override
  public List<ETFSwapRecord> getEtfSwapList(ETFSwapListRequest request) {

    InputChecker.checker()
        .shouldNotNull(request.getEtfName(), "etf_name")
        .shouldNotNull(request.getOffset(), "offset")
        .shouldNotNull(request.getLimit(), "limit")
        .checkRange(request.getLimit(), 1, 100, "limit");

    UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("etf_name", request.getEtfName())
        .putToUrl("offset", request.getOffset())
        .putToUrl("limit", request.getLimit());

    JSONObject jsonObject = restConnection.executeGetWithSignature(GET_SWAP_LIST_PATH,builder);
    JSONArray data = jsonObject.getJSONArray("data");
    return new ETFSwapRecordParser().parseArray(data);
  }




}
