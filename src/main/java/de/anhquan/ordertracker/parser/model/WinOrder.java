package de.anhquan.ordertracker.parser.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("WinOrder")
public class WinOrder {
	
	@XStreamAlias("OrderList")
	OrderList _orderList;
	
	public WinOrder(){}
	public void setChild(OrderList orderList){
		_orderList = orderList;
	}
		
}