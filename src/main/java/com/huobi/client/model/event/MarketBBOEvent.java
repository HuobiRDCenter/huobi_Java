package com.huobi.client.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.huobi.client.impl.RestApiJsonParser;
import com.huobi.client.impl.utils.JsonWrapper;
import com.huobi.client.impl.utils.TimeService;
import com.huobi.client.model.MarketBBO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MarketBBOEvent {

  private String ch;

  private Long timestamp;

  private MarketBBO data;

  public static RestApiJsonParser<MarketBBOEvent> getParser(){
    return (jsonWrapper) -> {
      return parse(jsonWrapper);
    };
  }

  public static MarketBBOEvent parse(JsonWrapper jsonWrapper) {
    return MarketBBOEvent.builder()
        .ch(jsonWrapper.getStringOrDefault("ch",null))
        .timestamp(TimeService.convertCSTInMillisecondToUTC(jsonWrapper.getLong("ts")))
        .data(MarketBBO.parse(jsonWrapper.getJsonObject("tick")))
        .build();
  }

}
