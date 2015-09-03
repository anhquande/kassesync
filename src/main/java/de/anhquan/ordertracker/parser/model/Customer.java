package de.anhquan.ordertracker.parser.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Customer {

	@XStreamAlias("CustomerNo")
	private String _customerNo;
	
	@XStreamAlias("DeliveryAddress")
	private Address _deliveryAddress;
	
	public Customer(){
		
	}

	public String getCustomerNo() {
		return _customerNo;
	}

	public Customer customerNo(String customerNo) {
		this._customerNo = customerNo;
		return this;
	}
	
	public Customer deliveryAddress(Address addr){
		this._deliveryAddress = addr;
		return this;
	}
	
	public Address getDeliveryAddress(){
		return this._deliveryAddress;
	}

}
