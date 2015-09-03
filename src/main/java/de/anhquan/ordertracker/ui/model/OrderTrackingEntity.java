package de.anhquan.ordertracker.ui.model;

import java.util.Date;



public class OrderTrackingEntity {

	private String trackingId;
	private OrderNumber orderNumber;
	private Date orderDate;
	private String customer;
	private String address;
	private OrderTrackingStatus status;
	private String shopId;

	public OrderNumber getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = new OrderNumber(orderNumber);
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public OrderTrackingStatus getStatus() {
		return status;
	}

	public void setStatus(OrderTrackingStatus status) {
		this.status = status;
	}
	
	public OrderTrackingStatus gotoNextStatus(){
		this.status = status.next();
		return this.status;
	}
	
	public OrderTrackingStatus gotoPrevStatus(){
		this.status = status.prev();
		return this.status;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

}
