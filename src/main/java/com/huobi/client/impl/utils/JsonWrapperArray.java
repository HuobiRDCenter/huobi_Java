package com.huobi.client.impl.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.client.exception.HuobiApiException;
import java.math.BigDecimal;

public class JsonWrapperArray {

  private JSONArray array = null;

  public JsonWrapperArray(JSONArray array) {
    this.array = array;
  }

  public JsonWrapper getJsonObjectAt(int index) {
    if (array != null && array.size() > index) {
      JSONObject object = (JSONObject) array.get(index);
      if (object == null) {
        throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
            "[Json] Cannot get object at index " + index + " in array");
      }
      return new JsonWrapper(object);
    } else {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
          "[Json] Index is out of bound or array is null");
    }
  }

  public JsonWrapperArray getArrayAt(int index) {
    if (array != null && array.size() > index) {
      JSONArray newArray = (JSONArray) array.get(index);
      if (newArray == null) {
        throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
            "[Json] Cannot get array at index " + index + " in array");
      }
      return new JsonWrapperArray(newArray);
    } else {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
          "[Json] Index is out of bound or array is null");
    }
  }

  private Object getObjectAt(int index) {
    if (array != null && array.size() > index) {
      return array.get(index);
    } else {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
          "[Json] Index is out of bound or array is null");
    }
  }

  public long getLongAt(int index) {
    try {
      return (Long) getObjectAt(index);
    } catch (Exception e) {
      throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
          "[Json] Cannot get long at index " + index + " in array: " + e.getMessage());
    }

  }

  public BigDecimal getBigDecimalAt(int index) {

    try {
      return new BigDecimal(((BigDecimal) getObjectAt(index)).stripTrailingZeros().toPlainString());
    } catch (RuntimeException e) {
      throw new HuobiApiException(null, e.getMessage());
    }

  }

  public String getStringAt(int index) {

    try {
      return (String) getObjectAt(index);
    } catch (RuntimeException e) {
      throw new HuobiApiException(null, e.getMessage());
    }

  }

  public void forEach(Handler<JsonWrapper> objectHandler) {
    array.forEach((object) -> {
      if (!(object instanceof JSONObject)) {
        throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
            "[Json] Parse array error in forEach");
      }
      objectHandler.handle(new JsonWrapper((JSONObject) object));
    });
  }

  public void forEachAsArray(Handler<JsonWrapperArray> objectHandler) {
    array.forEach((object) -> {
      if (!(object instanceof JSONArray)) {
        throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
            "[Json] Parse array error in forEachAsArray");
      }
      objectHandler.handle(new JsonWrapperArray((JSONArray) object));
    });
  }

  public void forEachAsString(Handler<String> objectHandler) {
    array.forEach((object) -> {
      if (!(object instanceof String)) {
        throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
            "[Json] Parse array error in forEachAsString");
      }
      objectHandler.handle((String) object);
    });
  }
}

