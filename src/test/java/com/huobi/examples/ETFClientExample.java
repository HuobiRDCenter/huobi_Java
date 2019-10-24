package com.huobi.examples;

import com.huobi.constant.Constants;
import com.huobi.constant.HuobiOptions;
import com.huobi.model.etf.ETFConfig;
import com.huobi.service.huobi.HuobiETFService;

public class ETFClientExample {

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
