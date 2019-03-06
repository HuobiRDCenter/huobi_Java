package com.huobi.client.impl.utils;

@FunctionalInterface
public interface Handler<T> {

  void handle(T t);
}
