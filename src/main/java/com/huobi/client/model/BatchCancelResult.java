package com.huobi.client.model;

/**
 * The result of batch cancel operation.
 */
public class BatchCancelResult {

  private int successCount;
  private int failedCount;

  /**
   * Get the number of cancel request sent successfully.
   *
   * @return The number.
   */
  public int getSuccessCount() {
    return successCount;
  }

  /**
   * Get the number of cancel request failed.
   *
   * @return The number.
   */
  public int getFailedCount() {
    return failedCount;
  }

  public void setFailedCount(int failedCount) {
    this.failedCount = failedCount;
  }

  public void setSuccessCount(int successCount) {
    this.successCount = successCount;
  }
}
