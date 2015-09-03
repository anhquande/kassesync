package de.anhquan.ordertracker.ui.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import jodd.util.StringUtil;

import de.anhquan.ordertracker.Constants;
import de.anhquan.ordertracker.datasource.IDataSource;
import de.anhquan.ordertracker.ui.OrderListener;
import de.anhquan.ordertracker.ui.Utils;

public class OrderTableModel extends AbstractTableModel implements
		OrderListener {

	public static final String COL_ORDER_NUMBER = "Nr";
	public static final String COL_ORDER_DATE = "Zeit";
	public static final String COL_CUSTOMER = "Kunden";
	public static final String COL_ADDRESS = "Adresse";
	public static final String COL_STATUS = "Status";

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { COL_ORDER_NUMBER, COL_ORDER_DATE,
			COL_CUSTOMER, COL_ADDRESS, COL_STATUS };

	private List<OrderTrackingEntity> rowData = new ArrayList<OrderTrackingEntity>();

	private boolean isFirstLoad = true;

	public void saveToFile() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (OrderTrackingEntity order : rowData) {
			map.put(new Integer(order.getOrderNumber().value()), new Integer(
					order.getStatus().getValue()));
		}

		File file = new File("temp");
		FileOutputStream f;
		try {
			f = new FileOutputStream(file);
			ObjectOutputStream s = new ObjectOutputStream(f);
			s.writeObject(map);
			s.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Map<Integer, Integer> loadFromFile() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		System.out.println("Load from file ...");
		try {
			FileInputStream f = new FileInputStream("temp");
			ObjectInputStream s = new ObjectInputStream(f);
			map = (HashMap<Integer, Integer>) s.readObject();
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return map;
	}

	public void addFirst(OrderTrackingEntity order) {
		rowData.add(0, order);
	}

	public void addLast(OrderTrackingEntity order) {
		rowData.add(order);
	}

	public void add(int afterRow, OrderTrackingEntity order) {
		rowData.add(afterRow, order);
	}

	public void clearAll() {
		rowData.clear();
	}

	public Class<?> getColumnClass(int col) {
		Object o = getValueAt(0, col);

		if (o == null)
			return Object.class;

		return o.getClass();
	}

	public int findColumn(String columnName) {
		if (COL_ORDER_NUMBER.compareToIgnoreCase(columnName) == 0)
			return 0;
		if (COL_ORDER_DATE.compareToIgnoreCase(columnName) == 0)
			return 1;
		if (COL_CUSTOMER.compareToIgnoreCase(columnName) == 0)
			return 2;
		if (COL_ADDRESS.compareToIgnoreCase(columnName) == 0)
			return 3;
		if (COL_STATUS.compareToIgnoreCase(columnName) == 0)
			return 4;
		return -1;
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public OrderTableModel() {
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return rowData.size();
	}

	public Object getValueAt(int row, int col) {
		OrderTrackingEntity order = rowData.get(row);
		if (order == null)
			return null;

		switch (col) {
		case 0:
			return order.getOrderNumber();
		case 1:
			return order.getOrderDate();
		case 2:
			return order.getCustomer();
		case 3:
			return order.getAddress();
		case 4:
			return order.getStatus();
		}

		return null;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 4;
	}

	public String viewStatusURL(int row) {
		OrderTrackingEntity entity = rowData.get(row);
		return Constants.host + "/status?id=" + entity.getTrackingId();
	}

	public String createStatusURL(int row) {
		OrderTrackingEntity entity = rowData.get(row);
		return Constants.host + "/create?id=" + entity.getTrackingId()
				+ "&order=" + entity.getOrderNumber().value();
	}

	public String clearStatusURL(int row) {
		OrderTrackingEntity entity = rowData.get(row);
		return Constants.host + "/clear?id=" + entity.getTrackingId();
	}

	public String updateStatusURL(OrderTrackingEntity entity, int newStatus) {
		return Constants.host + "/update?id=" + entity.getTrackingId()
				+ "&newstatus=" + newStatus;
	}

	public void nextStatus(int row) {
		OrderTrackingEntity entity = rowData.get(row);
		OrderTrackingStatus newStatus = entity.gotoNextStatus();
		fireTableCellUpdated(row, findColumn(COL_STATUS));
		Utils.doPostRequest(updateStatusURL(entity, newStatus.getValue()));
	}

	public void prevStatus(int row) {
		OrderTrackingEntity entity = rowData.get(row);
		OrderTrackingStatus newStatus = entity.gotoPrevStatus();
		fireTableCellUpdated(row, findColumn(COL_STATUS));
		Utils.doPostRequest(updateStatusURL(entity, newStatus.getValue()));
	}
	
	public String viewRouteURL(int curRow) {
		OrderTrackingEntity entity = rowData.get(curRow);
		String address = entity.getAddress();
		if ((address==null) || (address.isEmpty()))
			return "";
		
		address = address.replaceAll("\\s", "+");
		return Constants.ROUTE_FINDER_URL+""+address+"+Aachen";
	}
	

	public void onNewOrdersReceived(IDataSource source,
			List<OrderTrackingEntity> newOrders) {
		if (isFirstLoad) {
			Map<Integer, Integer> map = loadFromFile();
			for (OrderTrackingEntity order : newOrders) {
				Integer key = new Integer(order.getOrderNumber().value());
				if (map.containsKey(key)) {
					Integer value = map.get(key);
					order.setStatus(OrderTrackingStatus.values()[value]);
				}
			}
			isFirstLoad = false;
		}

		int rowUpdatedCount = 0;
		for (OrderTrackingEntity order : newOrders) {
			addFirst(order);
			rowUpdatedCount++;
		}

		if (rowUpdatedCount > 0) {
			Utils.playSound();
			fireTableRowsInserted(0, rowUpdatedCount - 1);
		}
	}

}
