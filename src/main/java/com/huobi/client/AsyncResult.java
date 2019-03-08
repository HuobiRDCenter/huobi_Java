package com.huobi.client;


import com.huobi.client.exception.HuobiApiException;


/**
 * Asynchronous calling result, including whether succeed or not, get T data and exception.
 *
 * @param <T> Any type you incoming
 */
public interface AsyncResult<T> {

  /**
   * Get exception, if the asynchronous invoking failure, you should use this method to get the
   * exception.
   *
   * @return The exception of the invoking.
   */
  HuobiApiException getException();

  /**
   * Calling success or not.
   *
   * @return true for successful, false for failed.
   */
  boolean succeeded();

  /**
   * Get the data response from the server.
   *
   * @return Any type you incoming.
   */
  T getData();
}
