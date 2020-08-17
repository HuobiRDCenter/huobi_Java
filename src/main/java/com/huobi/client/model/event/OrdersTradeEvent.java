package com.huobi.client.model.event;

@SuppressWarnings("serial")
public class OrdersTradeEvent extends OrdersUpdateEvent {
	
	private Long tradeId;
	
	private String tradePrice;
	
	private String tradeVolume;
	
	private Long tradeTime;
	
	private Boolean aggressor;
	
	private String execAmt;
	
	private String remainAmt;

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public String getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(String tradePrice) {
		this.tradePrice = tradePrice;
	}

	public String getTradeVolume() {
		return tradeVolume;
	}

	public void setTradeVolume(String tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	public Long getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Long tradeTime) {
		this.tradeTime = tradeTime;
	}

	public Boolean getAggressor() {
		return aggressor;
	}

	public void setAggressor(Boolean aggressor) {
		this.aggressor = aggressor;
	}

	public String getExecAmt() {
		return execAmt;
	}

	public void setExecAmt(String execAmt) {
		this.execAmt = execAmt;
	}

	public String getRemainAmt() {
		return remainAmt;
	}

	public void setRemainAmt(String remainAmt) {
		this.remainAmt = remainAmt;
	}
	
}
