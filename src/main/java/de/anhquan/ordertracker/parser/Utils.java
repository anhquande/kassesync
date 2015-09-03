package de.anhquan.ordertracker.parser;

import jodd.util.StringUtil;

public class Utils {
	
	public static String AREA_CODE = "0241";
	public static String AREA_CODE_NO_PREFIX = "241";
	public static String extractPhoneNumber(String input) {
		String phone = StringUtil.trimLeft(input);
		phone = StringUtil.remove(phone, ' ');
		phone = StringUtil.remove(phone, '-');
		phone = StringUtil.remove(phone, '_');
		phone = StringUtil.remove(phone, '/');
		phone = StringUtil.remove(phone, '\\');

		if (phone == null || phone.length()==0)
			return "";
		
		if (phone.startsWith("+49")) phone = StringUtil.cutPrefix(phone, "+49");
		if (phone.startsWith("0049")) phone = StringUtil.cutPrefix(phone, "0049");
		if (phone.startsWith("49")) phone = StringUtil.cutPrefix(phone, "49");
		if (phone.startsWith("+31")) phone = StringUtil.cutPrefix(phone, "+31");
		if (phone.startsWith("0031")) phone = StringUtil.cutPrefix(phone, "0031");
		if (phone.startsWith("+32")) phone = StringUtil.cutPrefix(phone, "+31");
		if (phone.startsWith("0032")) phone = StringUtil.cutPrefix(phone, "0032");
		

		boolean isGerman = true;
		boolean isAreaLandLine = false;
		
		if (isGerman && phone.startsWith(AREA_CODE)){
			phone = StringUtil.cutPrefix(phone, AREA_CODE);
			isAreaLandLine = true;
		}
		else if (isGerman && phone.startsWith(AREA_CODE_NO_PREFIX)){
			phone = StringUtil.cutPrefix(phone, AREA_CODE_NO_PREFIX);
			isAreaLandLine = true;
		}
		
		if (isAreaLandLine)
			return AREA_CODE +  phone;
		
		if (isGerman){
			if (!phone.startsWith("0"))
				phone = "0"+phone;
		}
				
		return phone;
	}

}
