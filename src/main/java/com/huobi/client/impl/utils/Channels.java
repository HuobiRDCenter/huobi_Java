package com.huobi.client.impl.utils;

import com.alibaba.fastjson.JSONObject;

import com.huobi.client.model.enums.AccountChangeModeEnum;
import com.huobi.client.model.enums.BalanceMode;
import com.huobi.client.model.enums.CandlestickInterval;
import com.huobi.client.model.enums.DepthStep;
import com.huobi.client.model.enums.MBPLevelEnums;

public abstract class Channels {

  public static final String OP_SUB = "sub";
  public static final String OP_REQ = "req";

  public static String klineChannel(String symbol, CandlestickInterval interval) {
    JSONObject json = new JSONObject();
    json.put("sub", "market." + symbol + ".kline." + interval.toString());
    json.put("id", System.currentTimeMillis() + "");
    return json.toJSONString();
  }

  public static String klineReqChannel(String symbol, CandlestickInterval interval, Long from, Long to) {
    JSONObject json = new JSONObject();
    json.put(OP_REQ, "market." + symbol + ".kline." + interval.toString());
    json.put("id", System.currentTimeMillis() + "");
    if (from != null) {
      json.put("from", from);
    }
    if (to != null) {
      json.put("to", to);
    }
    return json.toJSONString();
  }

  public static String priceDepthChannel(String symbol, DepthStep step) {
    return priceDepthChannel(OP_SUB, symbol, step);
  }

  public static String marketBBOChannel(String symbol) {
    JSONObject json = new JSONObject();
    json.put("sub", "market." + symbol + ".bbo");
    json.put("id", System.currentTimeMillis() + "");
    return json.toJSONString();
  }

  public static String priceDepthChannel(String op, String symbol, DepthStep step) {
    JSONObject json = new JSONObject();
    json.put(op, "market." + symbol + ".depth." + step.getStep());
    json.put("id", System.currentTimeMillis() + "");
    return json.toJSONString();
  }

  public static String tradeChannel(String symbol) {
    return tradeChannel(OP_SUB, symbol);
  }

  public static String tradeChannel(String op, String symbol) {
    JSONObject json = new JSONObject();
    json.put(op, "market." + symbol + ".trade.detail");
    json.put("id", System.currentTimeMillis() + "");
    return json.toJSONString();
  }

  public static String accountChannel(BalanceMode mode) {
    JSONObject json = new JSONObject();
    json.put("op", "sub");
    json.put("cid", System.currentTimeMillis() + "");
    json.put("topic", "accounts");
    if (mode != null) {
      json.put("model", mode.getCode());
    }
    return json.toJSONString();
  }

  public static String accountV2Channel(AccountChangeModeEnum mode) {
    JSONObject json = new JSONObject();
    json.put("action", "sub");
    json.put("ch", "accounts.update#" + mode.getCode());
    return json.toJSONString();
  }

  public static String tradeClearingChannel(String symbol) {
    JSONObject json = new JSONObject();
    json.put("action", "sub");
    json.put("ch", "trade.clearing#" + symbol);
    return json.toJSONString();
  }

  public static String ordersChannel(String symbol) {
    JSONObject json = new JSONObject();
    json.put("op", "sub");
    json.put("cid", System.currentTimeMillis() + "");
    json.put("topic", "orders." + symbol);
    return json.toJSONString();
  }

  public static String ordersChannelNew(String symbol) {
    JSONObject json = new JSONObject();
    json.put("op", "sub");
    json.put("cid", System.currentTimeMillis() + "");
    json.put("topic", "orders." + symbol + ".update");
    return json.toJSONString();
  }

  public static String tradeStatisticsChannel(String symbol) {
    return tradeStatisticsChannel(OP_SUB, symbol);
  }

  public static String tradeStatisticsChannel(String op, String symbol) {
    JSONObject json = new JSONObject();
    json.put(op, "market." + symbol + ".detail");
    json.put("id", System.currentTimeMillis() + "");
    return json.toJSONString();
  }

  public static String requestAccountListChannel() {
    JSONObject json = new JSONObject();
    json.put("op", OP_REQ);
    json.put("cid", System.currentTimeMillis() + "");
    json.put("topic", "accounts.list");
    return json.toJSONString();
  }

  public static String marketDepthMBPChannel(String symbol, MBPLevelEnums level) {
    return marketDepthMBPChannel(OP_SUB, symbol, level);
  }

  public static String requestMarketDepthMBPChannel(String symbol, MBPLevelEnums level) {
    return marketDepthMBPChannel(OP_REQ, symbol, level);
  }

  public static String marketDepthMBPChannel(String op, String symbol, MBPLevelEnums level) {
    JSONObject json = new JSONObject();
    String topic = "market." + symbol + ".mbp." + level.getLevels();
    json.put(op, topic);
    json.put("cid", System.currentTimeMillis() + "");
    return json.toJSONString();
  }
}
