package de.anhquan.ordertracker.datasource;

import de.anhquan.ordertracker.ui.OrderListener;

public interface IDataSource {

	public void addListener(OrderListener listener);
	
	public void fetch();
	
	public boolean connect();
	
	public void disconnect();
}
