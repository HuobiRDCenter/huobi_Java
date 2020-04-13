package com.huobi.client.model;

import java.math.BigDecimal;

public class AccountLedger {

	/**
	 * Account ID
	 */
	private Long accountId;

	/**
	 * Currency
	 */
	private String currency;

	/**
	 * Amount change (positive value if income, negative value if outcome)
	 */
	private BigDecimal transactAmt;

	/**
	 * Amount change types
	 */
	private String transactType;

	/**
	 * transaction Id
	 */
	private Long transactId;

	/**
	 * Acccount transferred from
	 */
	private Long transferer;

	/**
	 * Acccount transferred to
	 */
	private Long transferee;

	/**
	 * Transaction time (database time)
	 */
	private Long transactTime;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getTransactAmt() {
		return transactAmt;
	}

	public void setTransactAmt(BigDecimal transactAmt) {
		this.transactAmt = transactAmt;
	}

	public String getTransactType() {
		return transactType;
	}

	public void setTransactType(String transactType) {
		this.transactType = transactType;
	}

	public Long getTransactTime() {
		return transactTime;
	}

	public void setTransactTime(Long transactTime) {
		this.transactTime = transactTime;
	}

	public Long getTransactId() {
		return transactId;
	}

	public void setTransactId(Long transactId) {
		this.transactId = transactId;
	}

	public Long getTransferer() {
		return transferer;
	}

	public void setTransferer(Long transferer) {
		this.transferer = transferer;
	}

	public Long getTransferee() {
		return transferee;
	}

	public void setTransferee(Long transferee) {
		this.transferee = transferee;
	}
}
