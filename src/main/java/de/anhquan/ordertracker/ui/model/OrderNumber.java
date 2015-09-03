package de.anhquan.ordertracker.ui.model;

public class OrderNumber {

	private int value;
	
	public OrderNumber(){
		value = 0;
	}
	
	public OrderNumber(int v){
		value = v;
	}
	
	public String toString (){
		return ""+value;
	}
	
	public int value(){
		return value;
	}
	
}
