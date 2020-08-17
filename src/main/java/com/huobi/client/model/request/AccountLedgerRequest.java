package com.huobi.client.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.huobi.client.model.enums.QuerySort;
import com.huobi.client.model.enums.TransactType;

public class AccountLedgerRequest {
	
	/**
	 * default constructor
	 */
	public AccountLedgerRequest() {}

	/**
	 * constructor with accountId
	 * @param accountId
	 */
	public AccountLedgerRequest(Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * user business account id
	 */
	private Long accountId;

	/**
	 * currency
	 */
	private String currency;

	/**
	 * Amount change types (multiple selection allowed)
	 */
	private List<TransactType> typeList;

	/**
	 * Far point of time of the query window (unix time in millisecond). Searching
	 * based on transact-time. The maximum size of the query window is 1 hour. The
	 * query window can be shifted within 30 days.
	 */
	private Long startTime;

	/**
	 * Near point of time of the query window (unix time in millisecond). Searching
	 * based on transact-time. The maximum size of the query window is 1 hour. The
	 * query window can be shifted within 30 days.
	 */
	private Long endTime;

	/**
	 * Sorting order asc or desc
	 */
	private QuerySort sort;

	/**
	 * Maximum number of items in each response
	 */
	private Integer limit;

	private Long fromId;

	public String getTypeString() {
		return Optional.ofNullable(getTypeList()).orElse(new ArrayList<>()).stream().map(TransactType::getCode)
				.collect(Collectors.joining(","));
	}

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

	public List<TransactType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<TransactType> typeList) {
		this.typeList = typeList;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public QuerySort getSort() {
		return sort;
	}

	public void setSort(QuerySort sort) {
		this.sort = sort;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

}
