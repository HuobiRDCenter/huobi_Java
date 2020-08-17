package com.huobi.client.model.event;

@SuppressWarnings("serial")
public class OrdersCreationEvent extends OrdersUpdateEvent {

	private String orderPrice;
	
	private String orderSize;
	
	private String type;
	
	private Long orderCreateTime;

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderSize() {
		return orderSize;
	}

	public void setOrderSize(String orderSize) {
		this.orderSize = orderSize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(Long orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	
}
