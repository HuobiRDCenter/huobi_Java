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


  public static void main(String[] args) {
    HuobiETFService etfService = new HuobiETFService(HuobiOptions.builder()
        .apiKey(Constants.API_KEY)
        .secretKey(Constants.SECRET_KEY)
        .build());

    String etfName = "hb10";

    ETFConfig etfConfig = etfService.getConfig(etfName);
    System.out.println(etfConfig.toString());
    etfConfig.getUnitPriceList().forEach(etfUnitPrice -> {
      System.out.println("unit pirce:" + etfUnitPrice.toString());
    });

//    etfService.etfSwap(ETFSwapRequest.builder()
//        .direction(EtfSwapDirectionEnum.SWAP_IN_ETF)
//        .etfName(etfName)
//        .amount(new BigDecimal(1000))
//        .build());

//    etfService.etfSwap(ETFSwapRequest.builder()
//        .direction(EtfSwapDirectionEnum.SWAP_OUT_ETF)
//        .etfName(etfName)
//        .amount(new BigDecimal(1000))
//        .build());

//    List<ETFSwapRecord> recordList = etfService.getEtfSwapList(ETFSwapListRequest.builder()
//        .etfName(etfName)
//        .offset(0)
//        .limit(100)
//        .build());
//    recordList.forEach(record->{
//      System.out.println(record.toString());
//    });

//    String jsonString = "{\n"
//        + "  \"code\": 200,\n"
//        + "  \"data\": [\n"
//        + "    {\n"
//        + "      \"id\": 112222,\n"
//        + "      \"gmt_created\": 1528855872323,\n"
//        + "      \"currency\": \"hb10\",\n"
//        + "      \"amount\": 11.5,\n"
//        + "      \"type\": 1,\n"
//        + "      \"status\": 2,\n"
//        + "      \"detail\": {\n"
//        + "        \"used_currency_list\": [\n"
//        + "          {\n"
//        + "            \"currency\": \"btc\",\n"
//        + "            \"amount\": 0.666\n"
//        + "          },\n"
//        + "          {\n"
//        + "            \"currency\": \"eth\",\n"
//        + "            \"amount\": 0.666\n"
//        + "          }\n"
//        + "        ],\n"
//        + "        \"rate\": 0.002,\n"
//        + "        \"fee\": 100.11,\n"
//        + "        \"point_card_amount\":1.0,\n"
//        + "        \"obtain_currency_list\": [\n"
//        + "          {\n"
//        + "            \"currency\": \"hb10\",\n"
//        + "            \"amount\": 1000\n"
//        + "          }\n"
//        + "        ]\n"
//        + "      }\n"
//        + "    },\n"
//        + "    {\n"
//        + "      \"id\": 112223,\n"
//        + "      \"gmt_created\": 1528855872323,\n"
//        + "      \"currency\": \"hb10\",\n"
//        + "      \"amount\": 11.5,\n"
//        + "      \"type\": 2,\n"
//        + "      \"status\": 1,\n"
//        + "      \"detail\": {\n"
//        + "        \"used_currency_list\": [\n"
//        + "          {\n"
//        + "            \"currency\": \"btc\",\n"
//        + "            \"amount\": 0.666\n"
//        + "          },\n"
//        + "          {\n"
//        + "            \"currency\": \"eth\",\n"
//        + "            \"amount\": 0.666\n"
//        + "          }\n"
//        + "        ],\n"
//        + "        \"rate\": 0.002,\n"
//        + "        \"fee\": 100.11,\n"
//        + "        \"point_card_amount\":1.0,\n"
//        + "        \"obtain_currency_list\": [\n"
//        + "          {\n"
//        + "            \"currency\": \"hb10\",\n"
//        + "            \"amount\": 1000\n"
//        + "          }\n"
//        + "        ]\n"
//        + "      }\n"
//        + "    }\n"
//        + "  ],\n"
//        + "  \"message\": null,\n"
//        + "  \"success\": true\n"
//        + "}";
//
//    JSONObject jsonObject = JSON.parseObject(jsonString);
//    List<ETFSwapRecord> list = new ETFSwapRecordParser().parseArray(jsonObject.getJSONArray("data"));
//    list.forEach(record->{
//      System.out.println(record.toString());
//    });

  }


}
