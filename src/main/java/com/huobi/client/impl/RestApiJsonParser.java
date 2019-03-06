package com.huobi.client.impl;

import com.huobi.client.impl.utils.JsonWrapper;

@FunctionalInterface
interface RestApiJsonParser<T> {

  T parseJson(JsonWrapper json);
}
