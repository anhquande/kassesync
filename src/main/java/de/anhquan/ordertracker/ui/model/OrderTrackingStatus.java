package de.anhquan.ordertracker.ui.model;

public enum OrderTrackingStatus {
	READY(0,"vorbereiten"),
	COOKING(1,"kochen"),
	SENDING(2,"unterwegs"),
	RECEIVED(3,"fertig");
	
	private final int value;
	private final String title;
	
	private OrderTrackingStatus(int v, String title){
		this.value = v;
		this.title = title;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public OrderTrackingStatus next(){
		if (this == RECEIVED)
			return this;
		
		return OrderTrackingStatus.values()[value + 1];
	}
	
	public OrderTrackingStatus prev(){
		if (this == READY)
			return this;
		
		return OrderTrackingStatus.values()[value - 1];
	}
	
}
