package com.huobi.client.model.event;

@SuppressWarnings("serial")
public class OrdersCancellationEvent extends OrdersUpdateEvent {

	private String remainAmt;
	
	private Long lastActTime;

	public String getRemainAmt() {
		return remainAmt;
	}

	public void setRemainAmt(String remainAmt) {
		this.remainAmt = remainAmt;
	}

	public Long getLastActTime() {
		return lastActTime;
	}

	public void setLastActTime(Long lastActTime) {
		this.lastActTime = lastActTime;
	}
	
}
