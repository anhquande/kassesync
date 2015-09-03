package de.anhquan.ordertracker.parser.model;

public class Money {

	public static String format(long price) {
		String str = (price<0 ? "-":"") + Math.abs(price)/100 + "." + Math.abs(price)%100;
		return str;
	}
	
}
