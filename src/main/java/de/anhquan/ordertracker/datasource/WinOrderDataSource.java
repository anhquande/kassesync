package de.anhquan.ordertracker.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import de.anhquan.ordertracker.ui.OrderListener;
import de.anhquan.ordertracker.ui.model.OrderNumber;
import de.anhquan.ordertracker.ui.model.OrderTrackingEntity;
import de.anhquan.ordertracker.ui.model.OrderTrackingStatus;

public class WinOrderDataSource implements IDataSource {

	private static Logger log = Logger.getLogger(WinOrderDataSource.class);

	private Connection conn;

	private Statement statement;
	
	List<OrderTrackingEntity> orders = new ArrayList<OrderTrackingEntity>();
	List<OrderTrackingEntity> newOrders = new ArrayList<OrderTrackingEntity>();

	List<OrderListener> listeners = new ArrayList<OrderListener>();

	public boolean connect(){
		Properties props = new Properties();
		props.setProperty("user", "sysdba");
		props.setProperty("password", "masterkey");
		props.setProperty("encoding", "UTF-8");

		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver");
		} catch (ClassNotFoundException e) {
			log.error("Firebird JCA-JDBC driver not found in class path", e);
			return false;
		}

		try {
			conn = DriverManager.getConnection("jdbc:firebirdsql:java://localhost:3050/C:/WINORDER/WINORDER.FDB", props);
			statement = conn.createStatement();
		} catch (SQLException e) {
			log.error("Unable to establish a connection to FBServer", e);
			return false;
		}
		
		System.out.println("Connect successful");
		return true;
	}
	
	public void disconnect() {
		try {
			if ((statement != null) && (!statement.isClosed()))
				statement.close();
		} catch (SQLException e) {
			log.error(e);
		}

		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error("Error when closing connection.", e);
		}
		
		System.out.println("Connection closed");
	}
	
	public List<OrderTrackingEntity> getAll(){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		//String sql = "SELECT ORDERS.ORDERNO, CUSTOMERS.LASTNAME, STREETS.NAME, CUSTOMERS.HOUSENUMBER, ORDERCOMMENTS.SALESPRICE FROM ORDERS LEFT JOIN ORDERDETAILS ON ORDERS.ORDERSID = ORDERDETAILS.ORDERSID LEFT JOIN ARTICLES ON ORDERDETAILS.ARTICLESID = ARTICLES.ARTICLESID LEFT JOIN ORDERCOMMENTS ON ORDERS.ORDERSID=ORDERCOMMENTS.ORDERSID LEFT JOIN CUSTOMERS ON ORDERS.CUSTOMERSID = CUSTOMERS.CUSTOMERSID LEFT JOIN STREETS ON CUSTOMERS.STREETSID = STREETS.STREETSID";
		String sql = "SELECT ORDERS.ORDERNO, ORDERS.ORDERDATETIME, LASTNAME, STREETS.NAME AS STREET, CUSTOMERS.HOUSENUMBER FROM ORDERS LEFT JOIN CUSTOMERS ON ORDERS.CUSTOMERSID = CUSTOMERS.CUSTOMERSID LEFT JOIN STREETS ON CUSTOMERS.STREETSID = STREETS.STREETSID WHERE ORDERS.ORDERSID>0";
		
		try {
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()){
				int i = rs.getInt("ORDERNO");
				OrderTrackingEntity order = new OrderTrackingEntity();
				order.setOrderNumber(i);
				order.setCustomer(rs.getString("LASTNAME"));
				String address = rs.getString("STREET")+" "+rs.getString("HOUSENUMBER");
				order.setAddress(address );
				try {
					order.setOrderDate(df.parse(rs.getString("ORDERDATETIME")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				order.setStatus(OrderTrackingStatus.READY);
				
				OrderTrackingEntity found = findOrderByNumber(i);
				if (found==null){
					newOrders.add(order);
					orders.add(order);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (newOrders.size()>0)
			fireEvent();
		return orders;
	}
	
	private OrderTrackingEntity findOrderByNumber(int index){
		for (OrderTrackingEntity order : orders) {
			OrderNumber orderNumber = order.getOrderNumber();
			if (orderNumber!=null)
				if (orderNumber.value() == index)
					return order;
		}
		return null;
	}
	
	public void fetch(){
		newOrders.clear();
		getAll();
	}
	
	private void fireEvent(){
		for (OrderListener listener : listeners) {
			listener.onNewOrdersReceived(this, newOrders);
		}
	}
	
	public void addListener(OrderListener listener) {
		listeners.add(listener);
	}
}
