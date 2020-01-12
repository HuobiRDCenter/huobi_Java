package com.huobi.client.model.event;


import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.model.MarketBBO;

/**
 * The Market BBO data received by request of market bbo.
 */

public class MarketBBOEvent {

  private String ch;

  private Long timestamp;

  private MarketBBO data;

  public String getCh() {
    return ch;
  }

  public void setCh(String ch) {
    this.ch = ch;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public MarketBBO getData() {
    return data;
  }

  public void setData(MarketBBO data) {
    this.data = data;
  }

  public static RestApiJsonParser<MarketBBOEvent> getParser(){
    return (jsonWrapper) -> {
      return parse(jsonWrapper);
    };
  }

  public static MarketBBOEvent parse(JsonWrapper jsonWrapper) {
    MarketBBOEvent event = new MarketBBOEvent();
    event.setCh(jsonWrapper.getStringOrDefault("ch",null));
    event.setTimestamp(jsonWrapper.getLong("ts"));
    event.setData(MarketBBO.parse(jsonWrapper.getJsonObject("tick")));
    return event;
  }

}
