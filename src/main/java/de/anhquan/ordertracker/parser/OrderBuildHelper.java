package de.anhquan.ordertracker.parser;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

import de.anhquan.ordertracker.parser.model.Order;
import de.anhquan.ordertracker.parser.model.WinOrder;

/**
 * 
 * @author anhquan
 *
 */
public class OrderBuildHelper {

	private static XStream xstream;
	
	static Logger log = Logger.getLogger(OrderBuildHelper.class);
	
	static {
		xstream = new XStream();
		xstream.processAnnotations(WinOrder.class);
		xstream.processAnnotations(Order.class);
	}
	
	public static Order newOrder() {
		Order order = new Order();
		return order;
	}
	
	public static String toXML(Order order){
		StringBuilder str = new StringBuilder();
		
		str.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		str.append(xstream.toXML(order.getParent().getParent()));
		
		return str.toString();
	}
	
	public static void toXML(Order order, String outputDirectory) throws IOException{
		String fullPath = outputDirectory+"/"+order.getShopifyId()+".xml";
		toXML(order,new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fullPath), "UTF-8")));
		log.info("Write to file "+fullPath);
	}
		
	
	public static void toXML(Order order, Writer writer) throws IOException{
		writer.write(toXML(order));
		writer.flush();
		writer.close();		
	}
	
	public static void toXML(Order order, OutputStream os) throws IOException{
		os.write(toXML(order).getBytes());
		os.flush();
	}

}
