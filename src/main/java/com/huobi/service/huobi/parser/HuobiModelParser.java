package com.huobi.service.huobi.parser;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface HuobiModelParser<T> {


  T parse(JSONObject json);

  List<T> parseArray(JSONArray jsonArray);

}
