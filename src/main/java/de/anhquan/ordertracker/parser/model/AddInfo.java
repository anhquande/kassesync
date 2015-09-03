package de.anhquan.ordertracker.parser.model;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class AddInfo {
	
	@XStreamAlias("DateTimeOrder")
	private Date _dateTimeOrder;
	
	@XStreamAlias("DiscountPercent")
	private double _discountPercent;
	
	@XStreamOmitField
	private long _discountValue;
	@XStreamAlias("DiscountValue")	
	private String strDiscountValue;
	
	@XStreamAlias("CurrencyStr")
	private String _currencyStr;
	
	@XStreamOmitField
	private long _deliverLumpSum;
	
	@XStreamAlias("DeliverLumpSum")
	private String strdeliverLumpSum;
	
	@XStreamAlias("DeliverType")
	private DeliverType _deliverType;
	
	@XStreamAlias("DeliveredByEmployeeNo")
	private int _deliveredByEmployeeNo;
	
	@XStreamAlias("Comment")
	private String _comment;
	
	@XStreamAlias("PaymentType")
	private String _paymentType;
	
	public Date getDateTimeOrder() {
		return _dateTimeOrder;
	}

	public AddInfo dateTimeOrder(Date dateTimeOrder) {
		this._dateTimeOrder = dateTimeOrder;
		return this;
	}

	public double getDiscountPercent() {
		return _discountPercent;
	}

	public AddInfo discountPercent(double discountPercent) {
		this._discountPercent = discountPercent;
		return this;
	}

	public long getDiscountValue() {
		return _discountValue;
	}

	public AddInfo discountValue(long discountValue) {
		this._discountValue = discountValue;
		this.strDiscountValue = Money.format(discountValue);
		return this;
	}

	public String getCurrencyStr() {
		return _currencyStr;
	}

	public AddInfo currencyStr(String currencyStr) {
		this._currencyStr = currencyStr;
		return this;
	}

	public long getDeliverLumpSum() {
		return _deliverLumpSum;
	}

	public AddInfo deliverLumpSum(long deliverLumpSum) {
		this._deliverLumpSum = deliverLumpSum;
		this.strdeliverLumpSum = Money.format(deliverLumpSum);
		return this;
	}

	public DeliverType getDeliverType() {
		return _deliverType;
	}

	public AddInfo deliverType(DeliverType deliverType) {
		this._deliverType = deliverType;
		return this;
	}

	public int getDeliveredByEmployeeNo() {
		return _deliveredByEmployeeNo;
	}

	public AddInfo deliveredByEmployeeNo(int deliveredByEmployeeNo) {
		this._deliveredByEmployeeNo = deliveredByEmployeeNo;
		return this;
	}

	public String getComment() {
		return _comment;
	}

	public AddInfo comment(String comment) {
		this._comment = comment;
		return this;
	}

	public String getPaymentType() {
		return _paymentType;
	}

	public AddInfo paymentType(String paymentType) {
		this._paymentType = paymentType;
		return this;
	}

}
