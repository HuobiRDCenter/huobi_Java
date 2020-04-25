package com.huobi.client;

import com.huobi.client.exception.HuobiApiException;

import java.net.Proxy;
import java.net.URI;

/**
 * The configuration for the subscription APIs
 */
public class SubscriptionOptions {

  private String uri = "wss://api.huobi.pro/";
  private boolean isAutoReconnect = true;
  private int receiveLimitMs = 60_000;
  private int connectionDelayOnFailure = 15;
  private Proxy proxy;


  public SubscriptionOptions(
          SubscriptionOptions options) {
    this.uri = options.uri;
    this.isAutoReconnect = options.isAutoReconnect;
    this.receiveLimitMs = options.receiveLimitMs;
    this.connectionDelayOnFailure = options.connectionDelayOnFailure;
    this.proxy=options.proxy;
  }
  public void setProxy(Proxy proxy)
  {
    this.proxy=proxy;
  }
  public Proxy getProxy()
  {
    return this.proxy;
  }
  public SubscriptionOptions() {
  }

  /**
   * Set the URI for subscription.
   *
   * @param uri The URI name like "wss://api.huobi.pro".
   */
  public void setUri(String uri) {
    try {
      URI u = new URI(uri);
    } catch (Exception e)
    {
      throw new HuobiApiException(
              HuobiApiException.INPUT_ERROR, "The URI is incorrect: " + e.getMessage());
    }
    this.uri = uri;
  }

  /**
   * Set the receive limit in millisecond. If no message is received within this limit time, the
   * connection will be disconnected.
   *
   * @param receiveLimitMs The receive limit in millisecond.
   */
  public void setReceiveLimitMs(int receiveLimitMs) {
    this.receiveLimitMs = receiveLimitMs;
  }

  /**
   * If auto reconnect is enabled, specify the delay time before reconnect.
   *
   * @param connectionDelayOnFailure The delay time in second.
   */
  public void setConnectionDelayOnFailure(int connectionDelayOnFailure) {
    this.connectionDelayOnFailure = connectionDelayOnFailure;
  }

  /**
   * When the connection lost is happening on the subscription line, specify whether the client
   * reconnect to server automatically.
   * <p>The connection lost means:
   * <ul>
   * <li>Caused by network problem</li>
   * <li>The connection close triggered by server (happened every 24 hours)</li>
   * <li>No any message can be received from server within a specified time, see {@link
   * #setReceiveLimitMs(int)} (int)}</li>
   * </ul>
   *
   * @param isAutoReconnect The boolean flag, true for enable, false for disable
   * @return Return self for chaining
   */
  public SubscriptionOptions setAutoReconnect(boolean isAutoReconnect) {
    this.isAutoReconnect = isAutoReconnect;
    return this;
  }

  public boolean isAutoReconnect() {
    return isAutoReconnect;
  }

  public int getReceiveLimitMs() {
    return receiveLimitMs;
  }

  public int getConnectionDelayOnFailure() {
    return connectionDelayOnFailure;
  }

  public String getUri() {
    return uri;
  }
}
