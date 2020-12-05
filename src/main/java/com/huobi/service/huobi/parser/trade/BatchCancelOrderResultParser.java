package com.huobi.service.huobi.parser.trade;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.huobi.model.trade.BatchCancelOrderResult;
import com.huobi.service.huobi.parser.HuobiModelParser;

public class BatchCancelOrderResultParser implements HuobiModelParser<BatchCancelOrderResult> {

  @Override
  public BatchCancelOrderResult parse(JSONObject json) {

    JSONArray successArray = json.getJSONArray("success");
    List<Long> successList = new ArrayList<>();
    if (successArray != null && successArray.size() > 0) {
      successList = successArray.toJavaList(Long.class);
    }

    return BatchCancelOrderResult.builder()
        .successList(successList)
        .failedList(new CancelFailedResultParser().parseArray(json.getJSONArray("failed")))
        .build();
  }

  @Override
  public BatchCancelOrderResult parse(JSONArray json) {
    return null;
  }

  @Override
  public List<BatchCancelOrderResult> parseArray(JSONArray jsonArray) {
    return null;
  }
}
