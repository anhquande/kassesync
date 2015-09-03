package de.anhquan.ordertracker.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CustomerMapper {

	private static final String CUSTOMER_DB = "customers.txt";
	// key == ESHOP CustomerID
	// val == POS CustomerID
	static Map<Integer, Integer> map = new HashMap<Integer, Integer>(); 
	
	public static void loadFromFile(){
		FileInputStream fis;
		try {
			fis = new FileInputStream(CUSTOMER_DB);
			//Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		 
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty())
					continue;
				
				String s[] = line.split(",",2);
				if (s.length==2){
					Integer eShop = Integer.parseInt(s[0]);

					try{
						Integer pos = Integer.parseInt(s[1]);
						map.put(eShop, pos);
					}
					catch(NumberFormatException e){
						map.put(eShop, -1);
					}
				}

			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void appendRecord(Integer eShopCustomerNo, String desc){
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(CUSTOMER_DB, true)));
		    out.println(eShopCustomerNo.toString()+","+desc);
		    out.close();
		} catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}

	}
	
	public static Integer eShop2POS(Integer eShopCustomerNo){
	    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
	    	Integer key = entry.getKey();
	    	if (key.equals(eShopCustomerNo))
	    		return entry.getValue();
	    }

		return null;
	}
	
	public static Integer pos2EShop(Integer posCustomerNo){
	    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
	    	Integer value = entry.getValue();
	    	if (value == posCustomerNo)
	    		return entry.getKey();
	    }

		return null;
	}
}
