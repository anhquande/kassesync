package de.anhquan.ordertracker.ui;

import java.util.List;

import de.anhquan.ordertracker.datasource.IDataSource;
import de.anhquan.ordertracker.ui.model.OrderTrackingEntity;

public interface OrderListener {

	public void onNewOrdersReceived(IDataSource source, List<OrderTrackingEntity> newOrders);
}
